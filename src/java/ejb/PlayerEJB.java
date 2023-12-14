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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class PlayerEJB implements PlayerEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("java");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of all {@link Player} entities from the database.
     *
     * @return A {@link List} containing all {@link Player} entities. If no
     * players are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Player> findAllPlayers() {
        List<Player> players = null;
        try {
            LOGGER.info("PlayerManager: Reading all players.");
            players = em.createNamedQuery("findAllPlayers").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "PlayerManager: Exception reading all players:",
                    e.getMessage());
        }
        return players;
    }

    /**
     * Finds a {@link Player} by its login.
     *
     * @param login The login for the player to be found.
     * @return The {@link Player} object containing player data.
     */
    public Player findPlayerByLogin(Long id) {
        User user = null;
        try {
            LOGGER.info("PlayerManager: Finding player by login.");
            user = em.find(User.class, id);
            if (user != null) {
                LOGGER.log(Level.INFO, "PlayerManager: Player found {0}",
                        user.getEmail());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding player by login:",
                    e.getMessage());
        }
        return (Player) user;
    }

    /**
     * Finds a list of {@link User} entities based on the provided player level.
     *
     * @param level The level of the players to be found.
     * @return A {@link List} of {@link User} entities whose level matches the
     * specified value. If no players are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<User> findPlayersByLevel(Integer level) {
        List<User> users = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            users = em.createNamedQuery("findTeamByLevel").setParameter("level", level).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by name:", e.getMessage());
        }
        return users;
    }

    /**
     * Finds a {@link Team} by its ID.
     *
     * @param id The ID of the team to be found.
     * @return The {@link Team} object containing team data.
     */
    public Team findTeamById(Long id) {
        Team team = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            team = em.find(Team.class, id);
            if (team != null) {
                LOGGER.log(Level.INFO, "TeamManager: Team found {0}",
                        team.getId());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by id:", e.getMessage());
        }
        return team;
    }

    /**
     * Finds a list of {@link Team} entities based on the provided team name.
     *
     * @param name The name of the team to be found.
     * @return A {@link List} of {@link Team} entities matching the specified
     * team name. If no teams are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByName(String name) {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            teams = em.createNamedQuery("findTeamByPlayerName").setParameter("name", name).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by name:", e.getMessage());
        }
        return teams;
    }

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
    public List<Team> findTeamsByDate(Date date) {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by date.");
            teams = em.createNamedQuery("findTeamByDate").setParameter("date", date).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by date:", e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a list of {@link Team} entities based on the provided coach name.
     *
     * @param coach The name of the coach associated with the teams to be found.
     * @return A {@link List} of {@link Team} entities whose coach name matches
     * the specified value. If no teams are found, the list will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByCoach(String coach) {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by coach.");
            teams = em.createNamedQuery("findTeamByCoach").setParameter("coach", coach).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by coach:", e.getMessage());
        }
        return teams;
    }

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
    public List<Team> findTeamsByCaptainId(Long captainId) {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by captain id.");
            teams = em.createNamedQuery("findTeamByCaptainId").setParameter("captainID", captainId).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by captain id:", e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a {@link Team} by its ID.
     *
     * @param id The ID of the team to be found.
     * @return The {@link Team} object containing team data.
     */
    public List<Team> findTeamsByPlayerId(Long id) {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            teams = em.createNamedQuery("findTeamByPlayerId").setParameter("playerId", id).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by player id:",
                    e.getMessage());
        }
        return teams;
    }
   
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
    public List<Team> findTeamsWithWins(Result result) {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team wins by id.");
            teams = em.createNamedQuery("findTeamWithWins").setParameter("result", result).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team with wins by id:",
                    e.getMessage());
        }
        return teams;
    }

    /**
     * Updates team information.
     *
     * @param team The {@link Team} object with updated information.
     */
    public void updateTeam(Team team){
        LOGGER.info("TeamManager: Updating team.");
        try {
            em.merge(team);
            em.flush();
            LOGGER.info("TeamManager: Team updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception updating team.",
                    e.getMessage());
        }
    }
    /**
     * Deletes a team.
     * 
     * @param team The {@link Team} object to be deleted.
     */
    public void deleteTeam(Team team){
        LOGGER.info("TeamManager: Deleting team.");
        try {
            team = em.merge(team);
            em.remove(team);
            LOGGER.info("TeamManager: Team deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception deleting team.",
                    e.getMessage());
        }
    }
}
