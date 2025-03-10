package edu.uclm.esi.users.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.users.dao.UserDao;
import edu.uclm.esi.users.model.User;


@Service
public class UserService {
	@Autowired
	protected UserDao userdao;
	@Autowired
	protected TokenService tokenService;
	
	/////////////////////////////////////
	//LOGIN 
	/////////////////////////////////////
	public boolean login(String email, String pwd) {
	    // Verificar si el usuario existe
	    User u = this.userdao.findByEmail(email);
	    String errorMessage = "Credenciales incorrectas o desactivadas.";
	    if (Objects.isNull(u)) {
	        throw new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage);
	    }

	    // Verificar si la contraseña es correcta
	    if (!u.getPwd().equals(pwd)) {
	        throw new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage);
	    }

	    // Si todo está correcto, devolver true para permitir acceso a la pantalla de doble autenticación
	    return true;
	}
	
	/////////////////////////////////////
	//Registro GENERAL - EMPLEADOS Y ADMINS
	/////////////////////////////////////
	public void registrar(User user) {
	    // Comprobamos que el usuario no existe en la base de datos por email
	    User usercheck = this.userdao.findByEmail(user.getEmail());
	    if (usercheck != null) {
	        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "El usuario ya existe en la base de datos.");
	    }
	    
//	    // Hashear la contraseña y guardar el nuevo usuario en la base de datos
	    user.setPwd(org.apache.commons.codec.digest.DigestUtils.sha512Hex(user.getPwd()));
	    this.userdao.save(user);
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
	
}