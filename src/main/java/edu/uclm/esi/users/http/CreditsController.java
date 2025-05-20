package edu.uclm.esi.users.http;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.users.model.Credits;
import edu.uclm.esi.users.model.User;
import edu.uclm.esi.users.services.CreditService;
import edu.uclm.esi.users.services.TokenService;

@RestController
@RequestMapping("/credits")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class CreditsController {

    @Autowired
    private CreditService service;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/getcredits/{email}")
    public ResponseEntity<Credits> getUserCredits(
            @PathVariable String email,
            @RequestHeader("Authorization") String authHeader) {
    	System.out.println("Obteniendo creditos de " + email);
        try {
            tokenService.validarToken(authHeader);

            User user = service.getUserId(email);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            Optional<Credits> credits = service.getUserCredits(user.getId());
            if (credits.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(credits.get());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/addcreditsbyemail/{email}")
    public ResponseEntity<Credits> addCreditsByEmail(@RequestHeader(name = "Authorization") String authHeader,
                                                    @PathVariable String email,
                                                    @RequestParam int amount) {
        tokenService.validarToken(authHeader);
        User user = service.getUserId(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.addCredits(user.getId(), amount));

    }


    @PostMapping("/deductcredits/{email}")
    public ResponseEntity<String> deductCredits(@RequestHeader(name = "Authorization") String authHeader,
    		@PathVariable String email, @RequestParam int amount) {
        
        tokenService.validarToken(authHeader);
        User user = service.getUserId(email);
        boolean success = service.deductCredits(user.getId(), amount);
        return success ? ResponseEntity.ok("Credits deducted") : ResponseEntity.badRequest().body("Insufficient credits");
    }
}
