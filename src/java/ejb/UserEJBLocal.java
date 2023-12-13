/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author 2dam
 */
@Local
public interface UserEJBLocal {
     /**
     * Finds a {@link User} by its login. 
     * @param login The login for the user to be found.
     * @return The {@link User} object containing user data. 
     */
    public User findUserByLogin(String login);
    /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     * @return A List of {@link User} objects.
     */
    public List<User> findAllUsers();
}
