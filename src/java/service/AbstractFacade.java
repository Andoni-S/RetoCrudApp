/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Event;
import entity.Player;
import entity.Team;
import entity.Game;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.text.SimpleDateFormat;
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

    public List<Event> findEventsWonByPlayer(Player player, String result) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events won by player.");
            TypedQuery<Event> query = getEntityManager().createNamedQuery("findEventsWonByPlayer", Event.class);
            query.setParameter("player", player);
            query.setParameter("result", result);
            events = query.getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events won by player {1}", new Object[]{events.size(), player.getName()});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events won by player:", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return events;
    }

    public List<Event> findEventsWonByTeam(Team team, String result) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events won by team.");
            TypedQuery<Event> query = getEntityManager().createNamedQuery("findEventsWonByTeam", Event.class);
            query.setParameter("team", team);
            query.setParameter("result", result);
            events = query.getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events won by team {1}", new Object[]{events.size(), team.getName()});
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
}
