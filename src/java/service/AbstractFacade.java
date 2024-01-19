/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Event;
import entity.Team;
import entity.Game;
import entity.PlayerEvent;
import entity.TeamEvent;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author Jaboba Bartolomé Barroso
 * @author Ander Goirigolzarri Iturburu
 * @author Andoni Sanz Alcalde
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    private static final Logger LOGGER = Logger.getLogger("javafxserverside");

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
     * Retrieves a list of events associated with a specific organizer.
     *
     * This method queries the database to find events that are organized by the
     * specified organizer. The search is based on the organizer's name.
     *
     * @param organizerName The name of the organizer for whom to retrieve
     * events.
     * @return A list of {@link Event} objects associated with the specified
     * organizer.
     * @throws Exception If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsByOrganizer(String organizerName) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events by organizer.");
            events = getEntityManager()
                    .createNamedQuery("findEventsByOrganizer", Event.class)
                    .setParameter("organizerName", organizerName)
                    .getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events by organizer {1}", new Object[]{events.size(), organizerName});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events by organizer:", e.getMessage());
            throw new Exception(e.getMessage());
        }
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
     * @throws Exception If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsByGame(String gameName) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events by game.");
            events = getEntityManager()
                    .createNamedQuery("findEventsByGame", Event.class)
                    .setParameter("gameName", gameName)
                    .getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events by game {1}", new Object[]{events.size(), gameName});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events by game:", e.getMessage());
            throw new Exception(e.getMessage());
        }
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
     * @throws Exception If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsWonByPlayer(Long playerId) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events won by player.");
            events = getEntityManager()
                    .createNamedQuery("findEventsWonByPlayer", Event.class)
                    .setParameter("playerId", playerId)
                    .getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events won by player with ID {1}", new Object[]{events.size(), playerId});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events won by player:", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return events;
    }

    public List<Event> findEventsWonByTeam(Long teamId) throws Exception {
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
            throw new Exception(e.getMessage());
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
     * @throws Exception If an error occurs while retrieving events from the
     * database. The exception message provides details about the error.
     * @see Event
     */
    public List<Event> findEventsByONG(String ongName) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events by ONG.");
            events = getEntityManager()
                    .createNamedQuery("findEventsByONG", Event.class)
                    .setParameter("ongName", ongName)
                    .getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events associated with ONG {1}", new Object[]{events.size(), ongName});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events by ONG:", e.getMessage());
            throw new Exception(e.getMessage());
        }
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
    
    public void addPlayerToEvent(PlayerEvent playerEvent){
        getEntityManager().merge(playerEvent);
    }
    
    public void addTeamToEvent(TeamEvent teamEvent){
        getEntityManager().merge(teamEvent);
    }
}
