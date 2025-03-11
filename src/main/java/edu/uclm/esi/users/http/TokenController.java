package edu.uclm.esi.users.http;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class TokenController {
    
    @GetMapping("/checkToken")
    public void checkToken(@RequestParam String param) {
        
        //devuelve random codigo http 200 o 402
        if(new java.util.Random().nextBoolean()){
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.OK, "Token correcto");
        }else{
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.PAYMENT_REQUIRED, "Token incorrecto");
        }
    }
    
}
