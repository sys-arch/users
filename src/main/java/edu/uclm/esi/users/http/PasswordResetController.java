package edu.uclm.esi.users.http;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.users.services.EmailService;
import edu.uclm.esi.users.services.PasswordResetService;
import edu.uclm.esi.users.services.PasswordService;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RestController
@RequestMapping("/pwd")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordService passwordService;

    @Value("${APP_URL}")
    private String appUrl;

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        String token = passwordResetService.createResetToken(email);
        String resetLink = appUrl + "/reset-contrasena?token=" + token;

        String asunto = "Recuperación de contraseña";
        String mensaje = "<p>Hemos recibido una solicitud para restablecer tu contraseña. En 15 minutos el link caducará.</p>"
                + "<a href=\"" + resetLink + "\">Restablecer contraseña</a>";

        emailService.enviarEmail(email, asunto, mensaje);
        return ResponseEntity.ok("Se ha enviado un enlace para recuperar la contraseña a tu email.");
    }

    @GetMapping("/reset")
    public ResponseEntity<Map<String, String>> validateToken(@RequestParam String token) {
        if (passwordResetService.validateResetToken(token).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Token válido");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Token inválido o expirado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");

        if (!passwordService.isSamePwd(newPassword, confirmPassword)) {
            return ResponseEntity.badRequest().body("Las contraseñas no coinciden.");
        }
        if (!passwordService.isValid(newPassword)) {
            return ResponseEntity.badRequest().body("La nueva contraseña no cumple los requisitos.");
        }

        passwordResetService.resetPassword(token, newPassword, confirmPassword);
        return ResponseEntity.ok("¡Contraseña restablecida con éxito!");
    }
}
