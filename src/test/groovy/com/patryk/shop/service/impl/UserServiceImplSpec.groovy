package com.patryk.shop.service.impl

import com.patryk.shop.domain.dao.Role
import com.patryk.shop.domain.dao.User
import com.patryk.shop.repository.RoleRepository
import com.patryk.shop.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import javax.persistence.EntityNotFoundException
import java.time.LocalDateTime

class UserServiceImplSpec extends Specification{
    def userService
    def userRepository = Mock(UserRepository)
    def roleRepository = Mock(RoleRepository)
    def passwordEncoder = Mock(PasswordEncoder)

    def setup() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder)
    }

    def 'testing save method'() {
        given:
        def role = new Role(name: "ROLE_USER")
        def user = Mock(User)
        user.setPassword("password")
        def roles = new HashSet<>()
        roles.add(role)

        when:
        userService.save(user)

        then:
        1 * roleRepository.findByName("ROLE_USER") >> Optional.of(role)
        1 * user.setRoles(roles)
        1 * user.getPassword() >> "password"
        1 * user.setPassword(_)
        1 * passwordEncoder.encode(_)
        1 * userRepository.save(user)
        0 * _
    }

    def 'testing getById method, should find user by the id'() {
        given:
        def userId = 1
        def user = Mock(User)

        when:
        userService.getById(1L)

        then:
        1 * userRepository.getById(userId) >> user
        0 * _
    }


    def 'testing getCurrentUser method, should get the user'() {
        given:
        def user = Mock(User)
        user.setEmail("email@com")
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)

        def authentication = Mock(Authentication)

        when:
        userService.getCurrentUser()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> "email@com"
        1 * userRepository.findByEmail("email@com") >> Optional.of(user)
        0 * _
    }

    def 'testing getByEmail method, should get user'() {
        given:
        def user = Mock(User)
        user.setEmail("email@com")

        when:
        userService.getByEmail("email@com")

        then:
        1 * userRepository.findByEmail("email@com") >> Optional.of(user)
        0 * _
    }

    def 'testing getByEmail method for throwing an Exception'() {
        given:
        def user = Mock(User)
        user.setEmail("email@com")
        userRepository.findByEmail("email108@com") >> {throw new EntityNotFoundException() }

        when:
        userService.getByEmail("email108@com")

        then:
        thrown EntityNotFoundException
    }

    def 'testing update method'() {
        given:
        Role role = new Role(1L, "ADMIN")
        Set<Role> roles = new HashSet<>()
        roles.add(role)
        def user = new User(1L, "email1@gmail.com", "password1", LocalDateTime.of(2022,12,10,12,0), roles)
        def userDb = new User(2L, "email2@gmail.com", "password2", LocalDateTime.of(2022,10,1,12,0), roles)

        when:
        userService.update(user, 2L)

        then:
        1 * userRepository.getById(2L) >> userDb
        1 * userRepository.save(userDb)
        0 * _
    }

    def 'testing deleteById method'() {
        when:
        userService.deleteById(1L)

        then:
        1 * userRepository.deleteById(1L)
        0 * _
    }
}
