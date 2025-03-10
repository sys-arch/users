package edu.uclm.esi.users.http;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.users.model.Credits;
import edu.uclm.esi.users.services.CreditService;

@RestController
@RequestMapping("/credits")
public class CreditsController {

    @Autowired
    private CreditService service;

    @GetMapping("/getcredits/{userId}")
    public ResponseEntity<Credits> getUserCredits(@PathVariable String userId) {
        Optional<Credits> credits = service.getUserCredits(userId);
        return credits.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Credits> addCredits(@PathVariable String userId, @RequestParam int amount) {
        return ResponseEntity.ok(service.addCredits(userId, amount));
    }

    @PostMapping("/{userId}/deduct")
    public ResponseEntity<String> deductCredits(@PathVariable String userId, @RequestParam int amount) {
        boolean success = service.deductCredits(userId, amount);
        return success ? ResponseEntity.ok("Credits deducted") : ResponseEntity.badRequest().body("Insufficient credits");
    }
}