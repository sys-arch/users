package edu.uclm.esi.users.model;

public class UserRegister {
	
	private String email;
	private String pwd1;
    private String pwd2;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
    public UserRegister() {}   

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd1() {
		return pwd1;
	}

	public void setPwd1(String pwd) {
		this.pwd1 = pwd;
	}

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd) {
        this.pwd2 = pwd;
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