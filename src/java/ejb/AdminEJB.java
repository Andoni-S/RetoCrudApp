/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entity.Game;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
public class AdminEJB implements AdminEJBLocal{
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
    public List<Game> findAllGamesCreatedByAdmin() {
        List<Game> games=null;
        try{
            LOGGER.info("UserManager: Reading all games.");
            games=em.createNamedQuery("findAllGamesCreatedByAdmin").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all games created by admin",
                    e.getMessage());
        }
        return games;
    }
}
