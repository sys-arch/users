package edu.uclm.esi.users.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.users.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByEmail(String email);
    void deleteByToken(String token);
}
