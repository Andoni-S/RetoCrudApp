/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Player;
import entity.Result;
import entity.Team;
import entity.Game;
import entity.User;
import entity.PlayerTeam;
import entity.Event;
import entity.PlayerEvent;
import entity.TeamEvent;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.PathParam;

/**
 * An abstract class serving as a base for implementing facade classes for
 * various entities.
 *
 * The AbstractFacade class provides generic CRUD (Create, Read, Update, Delete)
 * operations for entities. It also includes additional methods for specific
 * business logic related to different entities.
 *
 * @param <T> The type of entity for which the facade is implemented.
 * @author Jaboba Bartolomé Barroso
 * @author Ander Goirigolzarri Iturburu
 * @author Andoni Sanz Alcalde
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    private static final Logger LOGGER = Logger.getLogger(AbstractFacade.class.getName());

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws CreateException {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) throws UpdateException {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) throws DeleteException {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) throws ReadException {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() throws ReadException {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) throws ReadException {
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
        } catch (Exception e) {
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
    /**
     * public List<Team> findTeamsByPlayerName(String name) throws ReadException
     * { List<Team> teams = null; try { LOGGER.info("TeamManager: Finding team
     * by name."); teams = (List)
     * getEntityManager().createNamedQuery("findTeamsByPlayerName",
     * Team.class).setParameter("name", name).getResultList(); } catch
     * (Exception e) { LOGGER.log(Level.SEVERE, "TeamManager: Exception finding
     * team by player name.", e.getMessage()); throw new
     * ReadException(e.getMessage()); } return teams; }
     *
     */
    /**
     * Finds a List of {@link Team} entities based on the provided result
     * indicating wins.
     *
     * @return A {@link List} of {@link Team} entities with the specified result
     * indicating wins. If no teams are found, the List will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public List<Team> findTeamsWithWins(Long teamId) throws ReadException {
        Result result = null;
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team wins by id.");
            teams = (List) getEntityManager().createNamedQuery("findTeamsWithWins", Team.class).setParameter("team_id", teamId).setParameter("result", result.Won).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team with wins by id.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }

    @Transactional
    public PlayerTeam createPlayerTeam(PlayerTeam pt) throws CreateException {
        try {
            getEntityManager().persist(pt);
            getEntityManager().flush();
            return pt;
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of teams associated with a player by their name.
     *
     * This method queries the database to find teams associated with a player
     * based on the provided player name.
     *
     * @return A list of teams associated with the specified player.
     * @throws ReadException If an error occurs while retrieving the teams from
     * the database.
     */
    public List<Team> findMyTeams(Long player_id) throws ReadException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding the player's teams.");
            teams = (List) getEntityManager().createNamedQuery("findMyTeams", Team.class).setParameter("player_id", player_id).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding the player's teams.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }

    /**
     * /**
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

    /**
     * Retrieves a list of events associated with a specific organizer.
     *
     * This method queries the database to find events that are organized by the
     * specified organizer. The search is based on the organizer's name.
     *
     * @param organizerName The name of the organizer for whom to retrieve
     * events.
     * @return A list of {@link Event} objects associated with the specified
     * organizer.
     * @throws ReadException If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsByOrganizer(String organizerName) throws ReadException {
        List<Event> events;
        LOGGER.info("EventFacade: Finding events by organizer.");
        events = getEntityManager()
                .createNamedQuery("findEventsByOrganizer", Event.class)
                .setParameter("organizerName", organizerName)
                .getResultList();
        LOGGER.log(Level.INFO, "EventFacade: Found {0} events by organizer {1}", new Object[]{events.size(), organizerName});
        return events;
    }

    /**
     * Retrieves a list of events associated with a specific game.
     *
     * This method queries the database to find events that are related to the
     * specified game. The search is based on the name of the game.
     *
     * @param gameName The name of the game for which to retrieve events.
     * @return A list of {@link Event} objects associated with the specified
     * game.
     * @throws ReadException If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsByGame(String gameName) throws ReadException {
        List<Event> events = null;
        LOGGER.info("EventFacade: Finding events by game.");
        events = getEntityManager()
                .createNamedQuery("findEventsByGame", Event.class)
                .setParameter("gameName", gameName)
                .getResultList();
        LOGGER.log(Level.INFO, "EventFacade: Found {0} events by game {1}", new Object[]{events.size(), gameName});
        return events;
    }

    /**
     * Retrieves a list of events won by a specific player.
     *
     * This method queries the database to find events that have been won by the
     * specified player. The search is based on the player's ID.
     *
     * @param playerId The ID of the player for whom to retrieve events won.
     * @return A list of {@link Event} objects won by the specified player.
     * @throws ReadException If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsWonByPlayer(Long playerId) throws ReadException {
        List<Event> events;
        LOGGER.info("EventFacade: Finding events won by player.");
        events = getEntityManager()
                .createNamedQuery("findEventsWonByPlayer", Event.class)
                .setParameter("playerId", playerId)
                .getResultList();
        LOGGER.log(Level.INFO, "EventFacade: Found {0} events won by player with ID {1}", new Object[]{events.size(), playerId});
        return events;
    }

    /**
     * Retrieves a list of events won by a specific team.
     *
     * This method queries the database to find events that were won by the
     * specified team. The search is based on the team ID.
     *
     * @param teamId The ID of the team for which to retrieve events.
     * @return A list of {@link Event} objects won by the specified team.
     * @throws ReadException If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsWonByTeam(Long teamId) throws ReadException {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events won by team.");
            events = getEntityManager()
                    .createNamedQuery("findEventsWonByTeam", Event.class)
                    .setParameter("teamId", teamId)
                    .getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events won by team with ID {1}", new Object[]{events.size(), teamId});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events won by team:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return events;
    }

    /**
     * Retrieves a list of events associated with a specific non-governmental
     * organization (ONG).
     *
     * This method queries the database to find events that are associated with
     * the specified ONG. The search is based on the name of the ONG.
     *
     * @param ongName The name of the non-governmental organization (ONG) for
     * which to retrieve events.
     * @return A list of {@link Event} objects associated with the specified
     * ONG.
     * @throws ReadException If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsByONG(String ongName) throws ReadException {
        List<Event> events = null;
        LOGGER.info("EventFacade: Finding events by ONG.");
        events = getEntityManager()
                .createNamedQuery("findEventsByONG", Event.class)
                .setParameter("ongName", ongName)
                .getResultList();
        LOGGER.log(Level.INFO, "EventFacade: Found {0} events associated with ONG {1}", new Object[]{events.size(), ongName});
        return events;
    }

    public List<Game> findAllGamesCreatedByAdmin(String adminUsername) throws ReadException {
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

    public List<Game> findGamesByPVPType(Enum pvpType) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByPVPType", Game.class)
                    .setParameter("pvpType", pvpType)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findGamesByReleaseDate(Date releaseDate) throws ReadException {
        List<Game> games = null;
        try {
            // Format the Date to a String with the desired pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    public User findUserByEmail(String email) throws ReadException {
        List<User> user = null;

        try {
            user = (List) getEntityManager().createNamedQuery("findUsersByEmail", User.class)
                    .setParameter("email", email)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return user.get(0);
    }

    public Player findPlayerById(@PathParam("id") Long id) throws ReadException {
        List<Player> user = null;

        try {
            user = (List) getEntityManager().createNamedQuery("findPlayerById", Player.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return user.get(0);
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

    /**
     * Signs the provided data with the private key and sends it to the client.
     *
     * @param entity The entity containing the data to be signed.
     */
    public void signAndSendDataToClient(T entity) {
        try {
            // Assuming your entity has a method to get the relevant data (adjust as needed)
            String dataToSign = getDataToSign(entity);

            // Sign the data using the private key
            byte[] signature = SecurityService.signData(dataToSign.getBytes());

            // Send data and signature to the client (this is a placeholder, replace with your logic)
            sendToClient(dataToSign, signature);

            LOGGER.log(Level.INFO, "Data signed and sent to the client successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error signing and sending data to client:", e);
            // Handle the exception appropriately
        }
    }

    // Placeholder method, replace with your actual communication logic
    private void sendToClient(String data, byte[] signature) {
        LOGGER.log(Level.INFO, "Sending data to the client: {0}", data);
        LOGGER.log(Level.INFO, "Sending signature to the client: {0}", Base64.getEncoder().encodeToString(signature));
    }

    // Placeholder method, replace with your actual logic to extract data from the entity
    private String getDataToSign(T entity) {
        // Example: assuming your entity has a getName method (adjust as needed)
        if (entity instanceof Event) {
            return ((Event) entity).getName();
        } else {
            // Handle other entity types if needed
            return "Unknown Data";
        }
    }

    public void addPlayerToEvent(PlayerEvent playerEvent) {
        getEntityManager().merge(playerEvent);
    }

    public void addTeamToEvent(TeamEvent teamEvent) {
        getEntityManager().merge(teamEvent);
    }

    public User findUsersByEmail(String email) throws ReadException {
        User u = new User();
        try {
            u = (User) getEntityManager().createNamedQuery("findUsersByEmail", User.class)
                    .setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return u;
    }
}
