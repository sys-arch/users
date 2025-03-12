package edu.uclm.esi.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Credits {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, length = 36)
    private String userId;

    @Column
    private int credits;

    public Credits(String userId, int credits) {
        this.userId = userId;
        this.credits = credits;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getCredits() {
        return credits;
    }

    public void addCredits(int amount) {
        credits += amount;
    }

    public boolean deductCredits(int amount) {
        if (credits < amount) {
            return false;
        }
        credits -= amount;
        return true;
    }
    
}
