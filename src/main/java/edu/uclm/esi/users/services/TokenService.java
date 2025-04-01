package edu.uclm.esi.users.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.users.security.JwtTokenProvider;

@Service
public class TokenService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Genera un JWT usando JwtTokenProvider
    public String generarToken(String email) {
        return jwtTokenProvider.generateToken(email);
    }

    // Valida el JWT usando JwtTokenProvider
    public String validarToken(String authHeader) {
        String token = extractToken(authHeader);
        
        if (token == null || token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no proporcionado o vacío.");
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token no válido o ha expirado.");
        }

        return token;
    }

    // Obtiene el email del usuario desde el JWT
    public String obtenerEmail(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token no válido o ha expirado.");
        }
        return jwtTokenProvider.getUsernameFromToken(token);
    }

    // Extrae el token del encabezado Authorization
    public String extractToken(String authHeader) {

        if (authHeader == null || authHeader.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El encabezado Authorization no puede ser nulo o vacío");
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Remover el prefijo "Bearer "
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El encabezado Authorization no contiene un token válido");
    }
    
}
