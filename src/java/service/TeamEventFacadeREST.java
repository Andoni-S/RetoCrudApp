package service;

import entity.PlayerEvent;
import entity.PlayerEventId;
import entity.Result;
import entity.TeamEvent;
import entity.TeamEventId;
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
import javax.ws.rs.core.PathSegment;

/**
 * This class represents a RESTful web service for managing TeamEvent entities.
 * It extends the AbstractFacade class and provides CRUD operations for TeamEvent entities.
 * Additionally, it includes methods for handling specific operations related to team events.
 *
 * The URI path for this resource is expected to be in the form 'entity.teamevent'.
 * It supports operations such as creating, editing, removing, finding, and retrieving lists of TeamEvent entities.
 * The primary key for TeamEvent entities is a composite key of teamId and eventId.
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.teamevent")
public class TeamEventFacadeREST extends AbstractFacade<TeamEvent> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private TeamEventId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;teamId=teamIdValue;eventId=eventIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.TeamEventId key = new entity.TeamEventId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> teamId = map.get("teamId");
        if (teamId != null && !teamId.isEmpty()) {
            key.setTeamId(new java.lang.Long(teamId.get(0)));
        }
        java.util.List<String> eventId = map.get("eventId");
        if (eventId != null && !eventId.isEmpty()) {
            key.setEventId(new java.lang.Long(eventId.get(0)));
        }
        return key;
    }

    public TeamEventFacadeREST() {
        super(TeamEvent.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TeamEvent entity) {
        super.addTeamToEvent(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, TeamEvent entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.TeamEventId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TeamEvent find(@PathParam("id") PathSegment id) {
        entity.TeamEventId key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TeamEvent> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TeamEvent> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @POST
    @Path("addTeamToEvent/{teamId}/{eventId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addPlayerToEvent(@PathParam("teamId") Long teamId, @PathParam("eventId") Long eventId) {
        TeamEventId teamEventId = new TeamEventId(teamId, eventId);
        TeamEvent teamEvent = new TeamEvent();
        teamEvent.setId(teamEventId);
        teamEvent.setResult(Result.Draw);
        em.merge(teamEvent);
    }
}
