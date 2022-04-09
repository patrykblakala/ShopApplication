package com.patryk.shop.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hazelcast.spi.impl.merge.SplitBrainDataSerializerHook.EXPIRATION_TIME;
import static com.patryk.shop.security.SecurityConstants.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static Optional<UsernamePasswordAuthenticationToken> parseToken(String token) {
        var claims = Jwts.parser()
                .setSigningKey("sekretnyKlucz")
                .parseClaimsJws(token.replace(BEARER, EMPTY))
                .getBody();
        var email = claims.getSubject();

        if (email == null) {
            return Optional.empty();
        }

        var authorities = claims.get(AUTHORITIES, String.class);
        var grantedAuthorities = new ArrayList<GrantedAuthority>();
        if (authorities != null && !authorities.isEmpty()) {
            grantedAuthorities = Arrays.stream(authorities.split(COMMA))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return Optional.of(new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities));
    }

    public static String getCurrentUserEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    public static String generateToken(String userName, String roles) {
        if (userName == null || roles == null) {
            throw new IllegalArgumentException();
        }
        var claims = new DefaultClaims()
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setSubject(userName);
        claims.put(AUTHORITIES, roles);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "sekretnyKlucz")
                .compact();
    }

    public static boolean hasRole(String role) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }
}
