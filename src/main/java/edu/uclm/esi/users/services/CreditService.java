package edu.uclm.esi.users.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uclm.esi.users.dao.CreditsDAO;
import edu.uclm.esi.users.model.Credits;

@Service
public class CreditService {

    @Autowired
    private CreditsDAO cdao;

    public Optional<Credits> getUserCredits(String userId) {
        return cdao.findByUserId(userId);
    }

    @Transactional
    public Credits addCredits(String userId, int amount) {
        Credits c = cdao.findByUserId(userId)
                .orElse(new Credits(userId, 0));
        c.addCredits(amount);
        return cdao.save(c);
    }

    @Transactional
    public boolean deductCredits(String userId, int amount) {
        Optional<Credits> userOpt = cdao.findByUserId(userId);
        if (userOpt.isPresent() && userOpt.get().getCredits() >= amount) {
            Credits userCredits = userOpt.get();
            userCredits.deductCredits(amount);
            cdao.save(userCredits);
            return true;
        }
        return false;
    }
}
