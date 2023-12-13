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
public class PlayerEJB {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER
            = Logger.getLogger("java");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;

    @Override
    public Player findPlayerByLogin(String login) {
        Player players = null;
        try {
            LOGGER.info("PlayerManager: Finding player by login.");
            players= em.createNamedQuery("findPlayerByLogin").getResultList();
            if(player!=null)
                LOGGER.log(Level.INFO,"PlayerManager: Player found {0}", 
                        player.getEmail());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding player by login:",
                    e.getMessage());
        }
        return player;
    }
}
