/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Player;
import entity.User;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public interface PlayerEJBLocal {
     /**
     * Finds a {@link Player} by its login. 
     * @param login The login for the player to be found.
     * @return The {@link Player} object containing player data. 
     */
    public User findPlayerByLogin(String login);
}
