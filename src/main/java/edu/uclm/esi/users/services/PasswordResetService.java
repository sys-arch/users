package edu.uclm.esi.users.services;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uclm.esi.users.dao.UserDao;
import edu.uclm.esi.users.model.PasswordResetToken;
import edu.uclm.esi.users.model.User;
import edu.uclm.esi.users.dao.PasswordResetTokenRepository;

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
    @Transactional
    public void resetPassword(String token, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
    
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido o expirado"));
    
        String email = resetToken.getEmail();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        String hashedPassword = DigestUtils.sha256Hex(newPassword);
    
        user.setPwd(hashedPassword);
        userRepository.save(user);
        tokenRepository.delete(resetToken);
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
