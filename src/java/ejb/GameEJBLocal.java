/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Game;
import java.util.List;

/**
 *
 * @author 2dam
 */
public interface GameEJBLocal {
    
        /**
     * Finds a List of {@link User} objects containing data for all games in the
     * application data storage.
     * @return A List of {@link User} objects.
     */
    public List<Game> findAllGames();
}
