package edu.uclm.esi.users.model;

public class Credits {
    private String userId;
    private int credits;

    public Credits(String userId, int credits) {
        this.userId = userId;
        this.credits = credits;
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
