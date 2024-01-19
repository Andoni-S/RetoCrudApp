/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import entity.Player;
import entity.Result;
import entity.Team;
import entity.Game;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public abstract class AbstractFacade<T> {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("java");
    
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Retrieves all teams.
     *
     * <p>
     * This method queries the database to retrieve all {@link Team} entities
     * available. The result is returned as a {@link List}. If no teams are
     * found, the List will be empty.
     *
     * @return A {@link List} of {@link Team} entities representing all teams.
     * @throws ReadException If an error occurs during the retrieval process.
     * Check the log for details.
     */
    public List<Team> findAllTeams() throws ReadException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding all teams.");
            teams = (List) getEntityManager().createNamedQuery("findAllTeams", Team.class).getResultList();
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding all teams.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }
    
    /**
     * Finds a List of {@link Team} entities based on the provided team name.
     *
     * @param name The name of the team to be found.
     * @return A {@link List} of {@link Team} entities matching the specified
     * team name. If no teams are found, the List will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByName(String name) throws ReadException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by name.");
            teams = (List) getEntityManager().createNamedQuery("findTeamsByName", Team.class).setParameter("name", name).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding teams by name.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a List of {@link Team} entities based on the provided foundation
     * date.
     *
     * @param date The foundation date of the teams to be found.
     * @return A {@link List} of {@link Team} entities whose foundation date
     * matches the specified value. If no teams are found, the List will be
     * empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByDate(Date date) throws ReadException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by date.");
            teams = (List) getEntityManager().createNamedQuery("findTeamsByDate", Team.class).setParameter("date", date).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team by date.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a List of {@link Team} entities based on the provided coach name.
     *
     * @param coach The name of the coach associated with the teams to be found.
     * @return A {@link List} of {@link Team} entities whose coach name matches
     * the specified value. If no teams are found, the List will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsByCoach(String coach) throws ReadException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by coach.");
            teams = (List) getEntityManager().createNamedQuery("findTeamsByCoach", Team.class).setParameter("coach", coach).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team by coach.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a {@link Team} by its ID.
     *
     * @param id The ID of the team to be found.
     * @return The {@link Team} object containing team data.
     */
    /**public List<Team> findTeamsByPlayerName(String name) throws ReadException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by name.");
            teams = (List) getEntityManager().createNamedQuery("findTeamsByPlayerName", Team.class).setParameter("name", name).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team by player name.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }
**/
    /**
     * Finds a List of {@link Team} entities based on the provided result
     * indicating wins.
     *
     * @param result The result associated with the teams to be found.
     * @return A {@link List} of {@link Team} entities with the specified result
     * indicating wins. If no teams are found, the List will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsWithWins() throws ReadException {
        Result result = null;
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team wins by id.");
            teams = (List) getEntityManager().createNamedQuery("findTeamsWithWins", Team.class).setParameter("result", result.Won).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team with wins by id.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }

    public void joinTeam(Integer playerId, Integer teamId) throws UpdateException {
        try {
            LOGGER.info("TeamManager: Finding player and team.");
            Player player = getEntityManager().find(Player.class, playerId);
            Team team = getEntityManager().find(Team.class, teamId);
            player.getTeams().add(team);
            // Persist the PlayerTeam entity to the database
            getEntityManager().persist(player);
            LOGGER.info("TeamManager: List of player's teams updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team player's teams.", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    
    /**
     * Retrieves a list of teams associated with a player by their name.
     *
     * This method queries the database to find teams associated with a player
     * based on the provided player name.
     *
     * @param name The name of the player for whom teams are to be retrieved.
     * @return A list of teams associated with the specified player.
     * @throws ReadException If an error occurs while retrieving the teams from
     * the database.
     */
    /*public List<Team> findMyTeams(Player player) throws ReadException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding the player's teams.");
            teams = (List) getEntityManager().createNamedQuery("findMyTeams", Team.class).setParameter("player", player).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding the player's teams.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }*/

    /**
    /**
     * Creates a new Team.
     *
     * @param newTeam The Team entity to be created.
     * @return The newly created Team entity.
     * @throws CreateException If an error occurs during the creation process.
     */
    @Transactional
    public Team createTeam(Team newTeam) throws CreateException {
        try {
            getEntityManager().persist(newTeam);
            getEntityManager().flush();
            return newTeam;
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    
    /**
     * Updates an existing Team.
     *
     * @param teamToUpdate The Team entity to be updated.
     * @return The updated Team entity.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Transactional
    public Team updateTeam(Team teamToUpdate) throws UpdateException {
        try {
            Team updatedTeam = (Team) getEntityManager().merge(teamToUpdate);
            getEntityManager().flush();
            return updatedTeam;
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    
    /**
     * Deletes an existing Team.
     *
     * @param teamToDelete The Team entity to be deleted.
     * @throws DeleteException If an error occurs during the delete process.
     */
    @Transactional
    public void deleteTeam(Team teamToDelete) throws DeleteException {
        try {
            teamToDelete = getEntityManager().merge(teamToDelete);
            getEntityManager().remove(teamToDelete);
            getEntityManager().flush();
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }
    
    public List<Game> findAllGamesCreatedByAdmin(String adminUsername)throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findAllGamesCreatedByAdmin")
                    .setParameter("adminUsername", adminUsername)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findAllGames() throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findAllGames").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findGamesByName(String name) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByName", Game.class)
                    .setParameter("name", name)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findGamesByGenre(String genre) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByGenre", Game.class)
                    .setParameter("genre", genre)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findGamesByPlatform(String platform) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByPlatform", Game.class)
                    .setParameter("platform", platform)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    /*public Set<Game> findGamesByPVPType(Enum pvpType) throws ReadException {
        Set<Game> games = null;
        try {
            games = (Set) em.createNamedQuery("findGamesByPVPType", Game.class)
                    .setParameter("pvpType", pvpType.ordinal())
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }*/

    public List<Game> findGamesByReleaseDate(Date releaseDate) throws ReadException {
    List<Game> games = null;
    try {
        // Format the Date to a String with the desired pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String formattedReleaseDate = dateFormat.format(releaseDate);

        // Use the formatted date in the query
        games = (List) getEntityManager().createNamedQuery("findGamesByReleaseDate", Game.class)
                .setParameter("releaseDate", formattedReleaseDate)
                .getResultList();
    } catch (Exception e) {
        throw new ReadException(e.getMessage());
    }
        return games;
    }

    public List<Game> findGamesByGenreAndReleaseDate(String genre, Date releaseDate) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByGenreAndReleaseDate", Game.class)
                    .setParameter("genre", genre)
                    .setParameter("releaseDate", releaseDate)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Transactional
    public Game createGame(Game newGame) throws CreateException {
        try {
            getEntityManager().persist(newGame);
            getEntityManager().flush();
            return newGame;
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Transactional
    public Game updateGame(Game gameToUpdate) throws UpdateException {
        try {
            Game updatedGame = (Game) getEntityManager().merge(gameToUpdate);
            getEntityManager().flush();
            return updatedGame;
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Transactional
    public void deleteGame(Game gameToDelete) throws DeleteException {
        try {
            gameToDelete = getEntityManager().merge(gameToDelete);
            getEntityManager().remove(gameToDelete);
            getEntityManager().flush();
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }
}
