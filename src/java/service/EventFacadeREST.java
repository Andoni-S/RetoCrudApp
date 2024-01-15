/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Event;
import java.util.List;
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

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Stateless
@Path("entity.event")
public class EventFacadeREST extends AbstractFacade<Event> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger("java");

    public EventFacadeREST() {
        super(Event.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Event entity){
        LOGGER.info("Creating event...");
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Event entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Event find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @GET
    @Path("findEventsByOrganizer/{organizerName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsByOrganizer(@PathParam("organizerName") String organizerName) throws Exception {
        List<Event> events = null;
        try {
            events = (List) getEntityManager().createNamedQuery("findEventsByOrganizer", Event.class)
                    .setParameter("name", organizerName).getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return events;
    }

    @GET
    @Path("findEventsByGame/{gameName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsByGame(@PathParam("gameName") String gameName) throws Exception {
        List<Event> events = null;
        try {
            events = (List) getEntityManager().createNamedQuery("findEventsByOrganizer", Event.class)
                    .setParameter("name", gameName).getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return events;
    }

    @GET
    @Path("findEventsWonByPlayer/{playerName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsWonByPlayer(@PathParam("playerName") String playerName) throws Exception {
        List<Event> events = null;
        try {
            events = (List) getEntityManager().createNamedQuery("findEventsByOrganizer", Event.class)
                    .setParameter("name", playerName).getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return events;
    }

    @GET
    @Path("findEventsWonByTeam/{teamName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsWonByTeam(@PathParam("teamName") String teamName) throws Exception {
        List<Event> events = null;
        try {
            events = (List) getEntityManager().createNamedQuery("findEventsByOrganizer", Event.class)
                    .setParameter("name", teamName).getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return events;
    }

    @GET
    @Path("findEventsByONG/{ongName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Event> findEventsByONG(@PathParam("ongName") String ongName) throws Exception {
        List<Event> events = null;
        try {
            events = (List) getEntityManager().createNamedQuery("findEventsByOrganizer", Event.class)
                    .setParameter("name", ongName).getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return events;
    }
}
