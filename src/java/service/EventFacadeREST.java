/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Event;
import exceptions.CreateException;
import exceptions.ReadException;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

/**
 * RESTful web service for managing Event entities. This service provides CRUD
 * operations (Create, Read, Update, Delete) for Event entities, as well as
 * additional operations for retrieving events based on different criteria. All
 * operations are accessible through HTTP methods and can produce and consume
 * XML or JSON representations.
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.event")
public class EventFacadeREST extends AbstractFacade<Event> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("java");

    /**
     * Default constructor. Initializes the superclass with the Entity class
     * type.
     */
    public EventFacadeREST() {
        super(Event.class);
    }

    /**
     * Creates a new Event entity.
     *
     * @param entity The Event entity to be created.
     * @throws CreateException If an error occurs during the creation process.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Event entity) throws CreateException {
        try {
            LOGGER.info("Creating event...");
            super.create(entity);
            LOGGER.info("Event created");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error creating the event", ex);
            throw new CreateException(ex.getMessage());
        }
    }

    /**
     * Updates an existing Event entity.
     *
     * @param id The ID of the Event entity to be updated.
     * @param entity The updated Event entity.
     * @throws InternalServerErrorException If an error occurs during the update
     * process.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Event entity) {
        try {
            LOGGER.info("Updating event...");
            super.edit(entity);
            LOGGER.info("Event updated.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error updating the event", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Deletes an existing Event entity.
     *
     * @param id The ID of the Event entity to be updated.
     * @throws InternalServerErrorException If an error occurs during the update
     * process.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            LOGGER.info("Removing event...");
            deletePlayerEventsByEventId(id);
            deleteTeamEventsByEventId(id);
            super.remove(super.find(id));
            LOGGER.info("Event removed.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error removing the event", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }

    }

    /**
     * Retrieves an Event entity by its ID.
     *
     * @param id The ID of the Event entity to retrieve.
     * @return The Event entity with the specified ID.
     * @throws NotFoundException If the Event entity with the specified ID is
     * not found.
     * @throws InternalServerErrorException If an error occurs during the
     * retrieval process.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Event find(@PathParam("id") Long id) {
        try {
            LOGGER.info("Searching for event with ID: " + id);
            Event event = super.find(id);
            if (event == null) {
                throw new NotFoundException("Event not found with ID: " + id);
            }
            return event;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error finding event", ex);
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    /**
     * Retrieves a list of all Event entities.
     *
     * @return A List of Event entities containing all events in the database.
     * @throws InternalServerErrorException If an error occurs during the
     * retrieval process.
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findAll() {
        try {
            LOGGER.info("Searching all events");
            return super.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error finding events", ex);
            throw new InternalServerErrorException("Internal Server Error");
        }
    }

    /**
     * Retrieves a range of Event entities based on the specified indices.
     *
     * @param from The starting index of the range.
     * @param to The ending index of the range.
     * @return A List of Event entities within the specified range.
     * @throws InternalServerErrorException If an error occurs during the
     * retrieval process.
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Retrieves the total count of Event entities.
     *
     * @return A String representing the total count of Event entities.
     * @throws InternalServerErrorException If an error occurs during the count
     * retrieval process.
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Retrieves the entity manager associated with this web service.
     *
     * @return The entity manager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Retrieves a list of events associated with a specific organizer.
     *
     * @param organizerName The name of the organizer.
     * @return A List of Event entities associated with the specified organizer.
     */
    @GET
    @Path("findEventsByOrganizer/{organizerName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsByOrganizer(@PathParam("organizerName") String organizerName) {
        try {
            LOGGER.log(Level.INFO, "Searching for events for : {0}", organizerName);
            return super.findEventsByOrganizer(organizerName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding events", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of events associated with a specific game.
     *
     * @param gameName The name of the game.
     * @return A List of Event entities associated with the specified game.
     */
    @GET
    @Path("findEventsByGame/{gameName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsByGame(@PathParam("gameName") String gameName) {
        try {
            LOGGER.log(Level.INFO, "Searching for events for : {0}", gameName);
            return super.findEventsByGame(gameName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding events", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of events won by a specific player.
     *
     * This method queries the database to find events that have been won by the
     * specified player. The search is based on the player's ID.
     *
     * @param playerId The ID of the player for whom to retrieve events won.
     * @return A List of {@link Event} entities won by the specified player.
     * @throws ReadException If an error occurs during the retrieval process.
     * The exception message provides details about the error.
     * @see Event
     */
    @GET
    @Path("findEventsWonByPlayer/{playerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsWonByPlayer(@PathParam("playerId") Long playerId) throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Searching for events Won by player with ID: {0}", playerId);
            return super.findEventsWonByPlayer(playerId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding events", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of events won by a specific team.
     *
     * @return A List of Event entities won by the specified team.
     * @throws ReadException If an error occurs during the retrieval process.
     */
    @GET
    @Path("findEventsWonByTeam/{teamName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsWonByTeam(@PathParam("teamId") Long teamId) throws ReadException {
        try {
            LOGGER.log(Level.INFO, "Searching for events Won by team with ID: {0}", teamId);
            return super.findEventsWonByPlayer(teamId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding events", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of events associated with a specific NGO.
     *
     * @param ongName The name of the NGO.
     * @return A List of Event entities associated with the specified NGO.
     */
    @GET
    @Path("findEventsByONG/{ongName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsByONG(@PathParam("ongName") String ongName) {
        try {
            LOGGER.log(Level.INFO, "Searching for events for : {0}", ongName);
            return super.findEventsByONG(ongName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding events", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private void deletePlayerEventsByEventId(Long eventId) {
        try {
            Query query = em.createNamedQuery("deletePlayerEventByEventId");
            query.setParameter("eventId", eventId);
            query.executeUpdate();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error deleting player events", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    private void deleteTeamEventsByEventId(Long eventId) {
        try {
            Query query = em.createNamedQuery("deleteTeamEventByEventId");
            query.setParameter("eventId", eventId);
            query.executeUpdate();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error deleting player events", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}
