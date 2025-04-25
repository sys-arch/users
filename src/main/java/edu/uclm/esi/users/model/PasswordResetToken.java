package edu.uclm.esi.users.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class PasswordResetToken {

    private static final int DURACION_MINUTOS = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, String email) {
        this.token = token;
        this.email = email;
        this.expirationTime = LocalDateTime.now().plusMinutes(DURACION_MINUTOS);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationTime);
    }
    public String getEmail() {
        return email;
    }
    

    // Getters y setters
}
