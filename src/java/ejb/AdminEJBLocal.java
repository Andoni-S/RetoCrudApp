/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Game;
import entity.User;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author 2dam
 */
@Local
public interface AdminEJBLocal {
    
    /**
     * Finds a List of {@link User} objects containing data for all games 
     * created by a expecific admin
     * @return A List of {@link User} objects.
     */
    public List<Game> findAllGamesCreatedByAdmin();
    public List<Game> findAllGames();
    public List<Game> findGamesByName(String name);
    public List<Game> findGamesByGenre(String genre);
    public List<Game> findGamesByPlatform(String platform);
    public List<Game> findGamesByPVPType(Enum pvpType);
    public List<Game> findGamesByReleaseDate(Date releaseDate);
    public List<Game> findGamesByGenreAndReleaseDate(String genre, Date releaseDate);
    public Game createGame(Game newGame);
    public Game updateGame(Game gameToUpdate);
    public void deleteGame(Game gameToDelete);
}
