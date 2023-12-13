/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Player;
import entity.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class PlayerEJB implements PlayerEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("java");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Finds a {@link User} by its login. 
     * @param login The login for the user to be found.
     * @return The {@link User} object containing user data. 
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public Player findPlayerByLogin(String login) {
        User user = null;
        try {
            LOGGER.info("PlayerManager: Finding player by login.");
            user = em.find(User.class, login);
            if(user!=null)
                LOGGER.log(Level.INFO,"PlayerManager: Player found {0}", 
                        user.getEmail());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding player by login:",
                    e.getMessage());
        }
        return (Player) user;
    }
}
