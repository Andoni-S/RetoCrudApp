/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
@Stateless
public class UserEJB implements UserEJBLocal{  
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
    
    @Override
    public User findUserByLogin(String login){
        User user=null;
        try{
            LOGGER.info("UserManager: Finding user by login.");
            user=em.find(User.class, login);
            if(user!=null)
                LOGGER.log(Level.INFO,"UserManager: User found {0}", 
                        user.getEmail());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
        }
        return user;
    }
    /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     * @return A List of {@link User} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    @Override
    public List<User> findAllUsers() {
        List<User> users=null;
        try{
            LOGGER.info("UserManager: Reading all users.");
            users=em.createNamedQuery("findAllUsers").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all users:",
                    e.getMessage());
        }
        return users;
    }
}

