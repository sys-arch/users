package edu.uclm.esi.users.service;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.users.dao.UserDao;
import edu.uclm.esi.users.model.PasswordResetToken;
import edu.uclm.esi.users.model.User;
import main.java.edu.uclm.esi.users.dao.PasswordResetTokenRepository;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserDao userRepository;

    public String createResetToken(String email) {
        tokenRepository.deleteByEmail(email);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, email);
        tokenRepository.save(resetToken);
        return token;
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);

        if (resetToken.isEmpty() || resetToken.get().isExpired()) {
            throw new IllegalArgumentException("Token inválido o caducado.");
        }

        String email = resetToken.get().getEmail();
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        user.setPwd(hashPassword(newPassword));
        userRepository.save(user);
        tokenRepository.deleteByToken(token);
        return true;
    }

    private String hashPassword(String password) {
        return DigestUtils.sha512Hex(password);
    }

    public Optional<PasswordResetToken> validateResetToken(String token) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByToken(token);
        if (resetTokenOpt.isPresent() && !resetTokenOpt.get().isExpired()) {
            return resetTokenOpt;
        } else {
            return Optional.empty();
        }
    }
}
