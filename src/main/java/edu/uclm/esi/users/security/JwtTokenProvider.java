package edu.uclm.esi.users.security;


import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    @Value("${jwt.secret}")
    private String secretKey; // Llave secreta (definida en application.properties)

    private final long expirationTime = 86400000; // Tiempo de expiración (1 día)

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Generar JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar JWT
    public boolean validateToken(String token) {
    	logger.info("Validando token: [" + token + "]");
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            logger.info("Token validated successfully.");

            return true;
        } catch (Exception e) {
            logger.error("Token validation failed: " + e.getMessage());

            return false;
        }
    }

    // Obtener nombre de usuario (subject) del JWT
    public String getUsernameFromToken(String token) {
        String username = getClaimFromToken(token, Claims::getSubject);
        logger.info("Username extracted from token: " + username);
        return username;
    }

    // Método genérico para obtener cualquier dato del token
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public UUID getUserIdFromToken(String token) {
        String userIdString = getClaimFromToken(token, claims -> claims.get("userId", String.class));
        UUID userId = UUID.fromString(userIdString);
        logger.info("User ID extracted from token: " + userId);
        return userId;
    }
}
