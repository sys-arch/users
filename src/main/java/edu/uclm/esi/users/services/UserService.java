package edu.uclm.esi.users.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.users.dao.CreditsDAO;
import edu.uclm.esi.users.dao.UserDao;
import edu.uclm.esi.users.model.Credits;
import edu.uclm.esi.users.model.User;


@Service
public class UserService {
	@Autowired
	protected UserDao userdao;
	@Autowired
	protected CreditsDAO creditsdao;
	@Autowired
	protected TokenService tokenService;
	
	/////////////////////////////////////
	//LOGIN 
	/////////////////////////////////////
	public boolean login(String email, String pwd) {
		User u = this.userdao.findByEmail(email);
		String errorMessage = "Credenciales incorrectas o desactivadas.";
		if (Objects.isNull(u)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage);
		}
	
		if (!u.getPwd().equals(pwd)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage);
		}
	
		return true;
	}
	
	
	/////////////////////////////////////
	//Registro GENERAL - EMPLEADOS Y ADMINS
	/////////////////////////////////////
	public void registrar(User user) {
	    User usercheck = this.userdao.findByEmail(user.getEmail());
	    if (usercheck != null) {
	        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "El usuario ya existe en la base de datos.");
	    }

	    String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(user.getPwd());
	    user.setPwd(hashedPassword);
	    this.userdao.save(user);

	    if (!creditsdao.existsByUserId(user.getId())) {
	        Credits credits = new Credits(user.getId(), 0);
	        this.creditsdao.save(credits);
	    }
	}

	
	/////////////////////////////////////
	//BUSQUEDA USUARIOS (SOLO PARA PWD RESET)
	/////////////////////////////////////
	public User findByEmail(String email) {
        User usuario = userdao.findByEmail(email);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }
        return usuario;
    }
	

	/////////////////////////////////////
	//RESET CONTRASEÑA
	/////////////////////////////////////
	public void updatePassword(String email, String newPassword) {
	    // Buscar el usuario por email
	    User usuario = userdao.findByEmail(email);
	    if (usuario == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
	    }

	    // Cifrar la nueva contraseña usando SHA-512
	    String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha512Hex(newPassword);

	    // Actualizar la contraseña y guardar el usuario
	    usuario.setPwd(hashedPassword); // Asumiendo que `setPwd` actualiza la contraseña
	    userdao.save(usuario);
	}

	//Sanitizacion de strings
	public String sanitize(String input) {
	    if (input == null) {
	        return null;
	    }
	    return input.replace("&", "&amp;")
	                .replace("<", "&lt;")
	                .replace(">", "&gt;")
	                .replace("\"", "&quot;")
	                .replace("'", "&#x27;");
	}
	
}