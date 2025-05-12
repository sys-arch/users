package edu.uclm.esi.users.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.users.model.Credits;

public interface CreditsDAO extends JpaRepository<Credits, Long> {
    public Optional<Credits> findByUserId(String userId);  
    
}
