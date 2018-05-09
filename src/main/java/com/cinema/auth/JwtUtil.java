package com.cinema.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * JwtUtil.
 */
public class JwtUtil {

    private static final String SECRET_KEY = "change_me";

    public static String generateJWT(String email, String role) {
        LocalDateTime now = LocalDateTime.now();

        return Jwts.builder()
                .setIssuer(email)
                .claim("role", role)
                .claim("created_at", now)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(Date.from(now.plusSeconds(3600).atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }

    public static User parseJWT(String jwt) {
        if (jwt == null) {
            return null;
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwt).getBody();

            String email = claims.getIssuer();
            String userRole = claims.get("role", String.class);
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userRole));

            return new User(email, "", authorities);
        } catch (InvalidClaimException e) {
            return null;
        }
    }
}
