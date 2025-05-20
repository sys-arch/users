package edu.uclm.esi.users.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uclm.esi.users.dao.CreditsDAO;
import edu.uclm.esi.users.dao.UserDao;
import edu.uclm.esi.users.model.Credits;
import edu.uclm.esi.users.model.User; 

@Service
public class CreditService {

    @Autowired
    private CreditsDAO cdao;
    
    @Autowired
    private UserDao userDAO;

    public Optional<Credits> getUserCredits(String userId) {
        
        return cdao.findByUserId(userId);
    }
    
    public User getUserId(String email) {
        System.out.println(email);
    	return userDAO.findByEmail(email);
        
    }

    @Transactional
    public Credits addCredits(String userId, int amount) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID no puede ser null");
        }

        Credits c = cdao.findByUserId(userId).orElse(new Credits(userId, 0));
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
