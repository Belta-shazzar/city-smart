package com.shazzar.citysmart.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.signing.key}")
    private String AUTHORITY_KEY;
    @Value("${jwt.token.validity}")
    private Long TOKEN_VALIDITY;
    private final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String getUserNameFromToken(String jwt) {
        return getClaimsFromToken(jwt, Claims::getSubject);
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = getUserNameFromToken(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        final Date expiryDate = getExpirationFromToken(jwt);
        return expiryDate.before(new Date());
    }

    public Date getExpirationFromToken(String jwt) {
        return getClaimsFromToken(jwt, Claims::getExpiration);
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String jwt) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jwt).getBody();
    }

    public String generateToken(UserDetails userDetails, HttpServletRequest request) {
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return Jwts.builder()
                .claim(AUTHORITY_KEY, authorities)
                .setIssuer(request.getRequestURL().toString())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(KEY, SignatureAlgorithm.HS512)
                .compact();
    }



}
