package com.patryk.shop.service.impl

import com.patryk.shop.domain.dao.User
import com.patryk.shop.domain.dto.LoginDto
import com.patryk.shop.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import spock.lang.Specification

class LoginServiceImplSpec extends Specification{

    def loginService;
    def authenticationManager = Mock(AuthenticationManager)
    def userService = Mock(UserService)

    def setup() {
        loginService = new LoginServiceImpl(authenticationManager, userService)
    }

    def 'testing login method'() {
        given:
        LoginDto loginDto = new LoginDto("myEmail", "myPassword")
        User user = new User(email: "myEmail", password: "myPassword")
        def token = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        def authenticationToken = Mock(Authentication)


        when:
        loginService.login(loginDto)

        then:
        1 * authenticationManager.authenticate(token) >> authenticationToken
        1 * authenticationToken.getName() >> "myEmail"
        1 * authenticationToken.getAuthorities() >> [new SimpleGrantedAuthority("ROLE_USER")]
        1 * userService.getByEmail("myEmail") >> user
        1 * userService.save(user)
        0 * _
    }

    def 'testing login method for an Exception'() {
        given:
        LoginDto loginDto = new LoginDto("myEmail", "myPassword")
        User user = new User(email: "myEmail", password: "myPassword")
        def token = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        authenticationManager.authenticate(token) >> null


        when:
        loginService.login(loginDto)

        then:
        thrown AuthenticationServiceException
    }
}
