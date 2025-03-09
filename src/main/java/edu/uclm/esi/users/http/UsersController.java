package edu.uclm.esi.users.http;

import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;



import edu.uclm.esi.users.model.*;
import edu.uclm.esi.users.services.*;

import edu.uclm.esi.users.security.JwtTokenProvider;
import java.util.HashMap;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userservice;
    
    @Autowired
    PasswordService pwdservice;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    /*
     * COMPROBAR LO DE ABAJO CAMBIARLO  AL TRABAJO ACTUAL
     */
    
    /////////////////////////////
    //REGISTRO EMPLEADOS
    ////////////////////////////
    @PostMapping("/register")
    public void register(@RequestBody RegistroEmp re) {
        // Comprobamos que ambas contraseñas son iguales
    	if(!this.pwdservice.isSamePwd(re.getPwd1(), re.getPwd2())) {
    		throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Las contraseñas no son iguales");
    	}
    	
    	// Comprobamos que la contraseña cumple requisitos de seguridad
    	if(!this.pwdservice.isValid(re.getPwd1())) {
    		throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, 
    				"Las contraseña no cumple con los requisitos de seguridad: "
    				+ "Entre 8 y 24 caracteres, Debe contener una maysucula, una minuscula, un digito, "
    				+ "un caracter especial y no debe contener espacios");
    	}
        

        // Comprobamos que el email tiene un formato válido
        if (!emailservice.validarEmail(re.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email insertado no tiene un formato válido: "
            		+ "usuario@dominio.com");
        }

        // Si pasa los controles, se registra en BD
        Empleado emp = new Empleado();
        emp.setEmail(re.getEmail().toLowerCase());
        emp.setPwd(re.getPwd1()); // Asignar la contraseña validada
        emp.setNombre(re.getNombre());
        emp.setApellido1(re.getApellido1());
        emp.setApellido2(re.getApellido2());
        emp.setCentro(re.getCentro());
        emp.setDepartamento(re.getDepartamento());
        emp.setPerfil(re.getPerfil());
        emp.setFechaalta(re.getFechaalta());
        emp.setBloqueado(re.isBloqueado());
        emp.setVerificado(re.isVerificado());

        this.userservice.registrar(emp);
    }
    
    /////////////////////////////
    //LOGIN UNICO
    ////////////////////////////
    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> info) {
        String email = info.get("email").toString().toLowerCase();
        String pwd = org.apache.commons.codec.digest.DigestUtils.sha512Hex(info.get("pwd").toString());

        if (loginAttemptService.isBlocked(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Algo ha pasado, inténtelo más tarde.")); // Mensaje para el front
        }

        try {
            boolean loginResult = userservice.login(email, pwd);

            if (loginResult) {
            	String role = userservice.getRoleByEmail(email);
                String token = jwtTokenProvider.generateToken(email, role);
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas.");
            }
        } catch (ResponseStatusException e) {
            loginAttemptService.loginFailed(email); // Incrementa los intentos
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason()));
        }
    }


}
