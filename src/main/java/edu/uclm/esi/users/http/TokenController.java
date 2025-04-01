package edu.uclm.esi.users.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.users.services.TokenService;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    // Endpoint para validar el JWT
    @GetMapping("/validarToken")
    public ResponseEntity<?> validarToken(@RequestHeader(name = "Authorization") String authHeader) {
        try {
            tokenService.validarToken(authHeader);
            return ResponseEntity.ok("Token válido");
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }


    // Endpoint para obtener el email desde el JWT
    @GetMapping("/obtenerEmail")
    public String obtenerEmail(@RequestHeader(name = "Authorization") String authHeader) {
        String token = tokenService.validarToken(authHeader);
        return tokenService.obtenerEmail(token);
    }

}