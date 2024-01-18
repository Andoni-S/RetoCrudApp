/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.PlayerEvent;
import entity.PlayerEventId;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Stateless
@Path("entity.playerevent")
public class PlayerEventFacadeREST extends AbstractFacade<PlayerEvent> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("javafxserverside");

    private PlayerEventId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;playerId=playerIdValue;eventId=eventIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.PlayerEventId key = new entity.PlayerEventId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> playerId = map.get("playerId");
        if (playerId != null && !playerId.isEmpty()) {
            key.setPlayerId(new java.lang.Long(playerId.get(0)));
        }
        java.util.List<String> eventId = map.get("eventId");
        if (eventId != null && !eventId.isEmpty()) {
            key.setEventId(new java.lang.Long(eventId.get(0)));
        }
        return key;
    }

    public PlayerEventFacadeREST() {
        super(PlayerEvent.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(PlayerEvent entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, PlayerEvent entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.PlayerEventId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PlayerEvent find(@PathParam("id") PathSegment id) {
        entity.PlayerEventId key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PlayerEvent> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PlayerEvent> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Registers a player for a specific event using JQL.
     *
     * @param playerId The ID of the player to register.
     * @param eventId The ID of the event for which the player is registering.
     */
    @POST
    @Path("registerPlayerForEvent/{playerId}/{eventId}")
    public void registerPlayerForEvent(@PathParam("playerId") Long playerId, @PathParam("eventId") Long eventId) {
        try {
            LOGGER.log(Level.INFO, "Registering player with ID {0} for event with ID {1}", new Object[]{playerId, eventId});

            // Call the named query to register the player for the event
            em.createNamedQuery("registerPlayerForEvent")
                    .setParameter("playerId", playerId)
                    .setParameter("eventId", eventId)
                    .executeUpdate();

            LOGGER.info("Player successfully registered for the event.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error registering player for event", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
