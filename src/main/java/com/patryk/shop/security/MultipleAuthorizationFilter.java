package com.patryk.shop.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleAuthorizationFilter extends BasicAuthenticationFilter {
    public MultipleAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        var token = request.getHeader("Authorization");

        if (token == null || (!token.startsWith("Bearer ") && !token.startsWith("Basic "))) {
            chain.doFilter(request, response);
            return;
        }
        if (token.startsWith("Basic ")) {
            super.doFilterInternal(request, response, chain);
            return;
        }
        try {
            var claims = Jwts.parser().setSigningKey("patryk")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            var email = claims.getSubject();
            if (email != null) {
                String authorities = claims.get("authorities", String.class);

                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                if (authorities != null) {
                    grantedAuthorities = Arrays.stream(authorities.split(",")).map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                }

                var authenticationToken = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);

                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);

                chain.doFilter(request, response);


            } else {
                response.setStatus(401);
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(401);
        }
    }
}
