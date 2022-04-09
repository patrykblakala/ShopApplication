package com.patryk.shop.fixtures

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class Fixtures extends Specification {

    def authentication = Mock(Authentication)
    def securityContext = Mock(SecurityContext)

    def mockUser(String email) {
        securityContext.getAuthentication() >> authentication
        SecurityContextHolder.setContext(securityContext)
        SecurityContextHolder.getContext().getAuthentication().getName() >> email
    }

    def mockUserRole(String role) {
        securityContext.getAuthentication() >> authentication
        SecurityContextHolder.setContext(securityContext)
        SecurityContextHolder.getContext().getAuthentication().getAuthorities() >>  Collections.singleton(new SimpleGrantedAuthority(role))
    }
}
