package edu.uclm.esi.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.users.model.Users;


public interface UsersDao extends JpaRepository<Users, String>{
	Users findByEmailAndPwd(String email, String pwd);
	Users findByEmail(String email);
	
}
