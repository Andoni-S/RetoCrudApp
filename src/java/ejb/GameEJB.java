/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Game;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
public class GameEJB implements GameEJBLocal{
    
       /**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("java");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
        /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     * @return A List of {@link User} objects.
     */
    @Override
    public List<Game> findAllGames() {
        List<Game> games=null;
        try{
            LOGGER.info("UserManager: Reading all games.");
            games=em.createNamedQuery("findAllGames").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all games",
                    e.getMessage());
        }
        return games;
    }
    @Override
    public List<Game> updateGame() {
        List<Game> games=null;
        try{
            LOGGER.info("UserManager: Reading all games.");
            games=em.createNamedQuery("updateGame").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all games",
                    e.getMessage());
        }
        return games;
    }
    @Override
    public List<Game> deleteGame() {
        List<Game> games=null;
        try{
            LOGGER.info("UserManager: Reading all games.");
            games=em.createNamedQuery("deleteGame").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all games",
                    e.getMessage());
        }
        return games;
    }
}
