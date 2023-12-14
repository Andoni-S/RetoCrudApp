/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Player;
import entity.Result;
import entity.Team;
import entity.User;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
public interface PlayerEJBLocal {

    /**
     * Retrieves a list of all {@link Player} entities from the database.
     *
     * @return A {@link List} containing all {@link Player} entities. If no
     * players are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Player> findAllPlayers();

    /**
     * Finds a {@link Player} by its login.
     *
     * @param login The login for the player to be found.
     * @return The {@link Player} object containing player data.
     */
    public User findPlayerByLogin(Long id);

    /**
     * Finds a list of {@link User} entities based on the provided player level.
     *
     * @param level The level of the players to be found.
     * @return A {@link List} of {@link User} entities whose level matches the
     * specified value. If no players are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<User> findPlayersByLevel(Integer level);

    /**
     * Finds a {@link Team} by its ID.
     *
     * @param id The ID of the team to be found.
     * @return The {@link Team} object containing team data.
     */
    public Team findTeamById(Long id);
    
    /**
     * Finds a list of {@link Team} entities based on the provided team name.
     *
     * @param name The name of the team to be found.
     * @return A {@link List} of {@link Team} entities matching the specified
     * team name. If no teams are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByName(String name);

    /**
     * Finds a list of {@link Team} entities based on the provided foundation
     * date.
     *
     * @param date The foundation date of the teams to be found.
     * @return A {@link List} of {@link Team} entities whose foundation date
     * matches the specified value. If no teams are found, the list will be
     * empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByDate(Date date);

    /**
     * Finds a list of {@link Team} entities based on the provided coach name.
     *
     * @param coach The name of the coach associated with the teams to be found.
     * @return A {@link List} of {@link Team} entities whose coach name matches
     * the specified value. If no teams are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByCoach(String coach);

    /**
     * Finds a list of {@link Team} entities based on the provided captain ID.
     *
     * @param captainId The ID of the captain associated with the teams to be
     * found.
     * @return A {@link List} of {@link Team} entities whose captain ID matches
     * the specified value. If no teams are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByCaptainId(Long captainId);

    /**
     * Finds a {@link Team} by its ID.
     *
     * @param id The ID of the team to be found.
     * @return The {@link Team} object containing team data.
     */
    public List<Team> findTeamsByPlayerId(Long id);

    /**
     * Finds a list of {@link Team} entities based on the provided result
     * indicating wins.
     *
     * @param result The result associated with the teams to be found.
     * @return A {@link List} of {@link Team} entities with the specified result
     * indicating wins. If no teams are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsWithWins(Result result);

    /**
     * Updates team information.
     *
     * @param team The {@link Team} object with updated information.
     */
    public void updateTeam(Team team);

    /**
     * Deletes a team.
     * 
     * @param team The {@link Team} object to be deleted.
     */
    public void deleteTeam(Team team);
}
