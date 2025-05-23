package edu.uclm.esi.users.http;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.users.model.User;
import edu.uclm.esi.users.model.UserRegister;
import edu.uclm.esi.users.security.JwtTokenProvider;
import edu.uclm.esi.users.services.EmailService;
import edu.uclm.esi.users.services.PasswordService;
import edu.uclm.esi.users.services.TokenService;
import edu.uclm.esi.users.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")

public class UsersController {

    @Autowired
    UserService userservice;
    
    @Autowired
    PasswordService pwdservice;

    @Autowired
    EmailService emailservice;

    @Autowired
    TokenService tokenService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    /////////////////////////////
    //REGISTRO
    /////////////////////////////
    @PostMapping("/register")
    public void register(@RequestBody UserRegister ur) {
        // Comprobamos que ambas contraseñas son iguales
    	if(!this.pwdservice.isSamePwd(ur.getPwd1(), ur.getPwd2())) {
    		throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Las contraseñas no son iguales");
    	}
    	
    	// Comprobamos que la contraseña cumple requisitos de seguridad
    	if(!this.pwdservice.isValid(ur.getPwd1())) {
    		throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, 
    				"Las contraseña no cumple con los requisitos de seguridad: "
    				+ "Entre 8 y 24 caracteres, Debe contener una maysucula, una minuscula, un digito, "
    				+ "un caracter especial y no debe contener espacios");
    	}
        

        // Comprobamos que el email tiene un formato válido
        if (!emailservice.validarEmail(ur.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email insertado no tiene un formato válido: "
            		+ "usuario@dominio.com");
        }
        //sanitizacion elementos si existen
        ur.setEmail(userservice.sanitize(ur.getEmail()));
        ur.setNombre(userservice.sanitize(ur.getNombre())); 
        ur.setApellido1(userservice.sanitize(ur.getApellido1()));
        ur.setApellido2(userservice.sanitize(ur.getApellido2()));
        ur.setPwd1(userservice.sanitize(ur.getPwd1()));

        // Si pasa los controles, se registra en BD
        User usuario = new User();
        usuario.setEmail(ur.getEmail().toLowerCase());
        usuario.setPwd(ur.getPwd1()); // Asignar la contraseña validada
        usuario.setNombre(ur.getNombre());
        usuario.setApellido1(ur.getApellido1());
        usuario.setApellido2(ur.getApellido2());

        this.userservice.registrar(usuario);
    }
    
    /////////////////////////////
    //LOGIN
    ////////////////////////////
    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> info) {
        String email = userservice.sanitize(info.get("email").toString().toLowerCase());

        String pwd = DigestUtils.sha256Hex(info.get("pwd").toString());

        try {
            boolean loginResult = userservice.login(email, pwd);

            if (loginResult) {
                String token = jwtTokenProvider.generateToken(email);
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas.");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason()));
        }
    }
    @GetMapping("/getuserid")
    public UUID getUserId(@RequestHeader("Authorization") String token) {
        
        if (token == null || token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no proporcionado.");
        }
        
        token = tokenService.validarToken(token);
        // Add logic to extract and return the user ID from the token
        return jwtTokenProvider.getUserIdFromToken(token);
    }

}
