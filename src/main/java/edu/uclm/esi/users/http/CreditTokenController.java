package edu.uclm.esi.users.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class CreditTokenController {
    
    @GetMapping("/checkToken")
    public void checkToken(@RequestParam String param) {
        // Devuelve aleatoriamente código HTTP 200 o 402
        if (new java.util.Random().nextBoolean()) {
            // Enviar una respuesta correcta sin lanzar excepción
            return;
        } else {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "Token incorrecto");
        }
    }
}
