package edu.uclm.esi.users.http;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.users.model.Credits;
import edu.uclm.esi.users.services.CreditService;
import edu.uclm.esi.users.services.TokenService;

@RestController
@RequestMapping("/credits")
public class CreditsController {

    @Autowired
    private CreditService service;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/getcredits/{userid}")
    public ResponseEntity<Credits> getUserCredits(@RequestHeader(name = "Authorization") String authHeader, @PathVariable String userid) {
        
        tokenService.validarToken(authHeader);
        Optional<Credits> credits = service.getUserCredits(userid);
        return credits.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addcredits/{userid}")
    public ResponseEntity<Credits> addCredits(@RequestHeader(name = "Authorization") String authHeader, @PathVariable String userid, @RequestParam int amount) {
        
        tokenService.validarToken(authHeader);
        return ResponseEntity.ok(service.addCredits(userid, amount));
    }

    @PostMapping("/deductcredits/{userid}")
    public ResponseEntity<String> deductCredits(@RequestHeader(name = "Authorization") String authHeader, @PathVariable String userid, @RequestParam int amount) {
        
        tokenService.validarToken(authHeader);
        boolean success = service.deductCredits(userid, amount);
        return success ? ResponseEntity.ok("Credits deducted") : ResponseEntity.badRequest().body("Insufficient credits");
    }
}