package edu.uclm.esi.users.model;



import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Usuarios", indexes = {
		@Index(columnList = "email", unique = true),
		@Index(columnList = "pwd")
	})

public class Users {
	
	@Id @Column(length = 36)
	private String id;
	
	@Column(length = 254, nullable = false)
	private String email;
	
	@Column(length = 128, nullable = false)
	private String pwd;
	
	@Column(length = 100, nullable = false)
	private String nombre;
	
	@Column(length = 100, nullable = false)
	private String apellido1;
	
	@Column(length = 100, nullable = false)
	private String apellido2;
	
    public Users() {
        this.id = UUID.randomUUID().toString();
    }
    
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

}