/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Game;
import entity.User;
import java.util.List;

/**
 *
 * @author 2dam
 */
public interface AdminEJBLocal {
    
    /**
     * Finds a List of {@link User} objects containing data for all games 
     * created by a expecific admin
     * @return A List of {@link User} objects.
     */
    public List<Game> findAllGamesCreatedByAdmin();
}
