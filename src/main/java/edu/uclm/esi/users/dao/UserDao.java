package edu.uclm.esi.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.users.model.User;

public interface UserDao extends JpaRepository<User, String>{
	User findByEmailAndPwd(String email, String pwd);
	User findByEmail(String email);
	
}
