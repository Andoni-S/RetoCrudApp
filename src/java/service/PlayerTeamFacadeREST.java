/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.PlayerTeam;
import entity.PlayerTeamId;
import exceptions.UpdateException;
import java.util.List;
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
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.playerteam")
public class PlayerTeamFacadeREST extends AbstractFacade<PlayerTeam> {

    private static final Logger LOGGER = Logger.getLogger("java");

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private PlayerTeamId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;playerId=playerIdValue;teamId=teamIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.PlayerTeamId key = new entity.PlayerTeamId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> playerId = map.get("playerId");
        if (playerId != null && !playerId.isEmpty()) {
            key.setPlayerId(new java.lang.Long(playerId.get(0)));
        }
        java.util.List<String> teamId = map.get("teamId");
        if (teamId != null && !teamId.isEmpty()) {
            key.setTeamId(new java.lang.Long(teamId.get(0)));
        }
        return key;
    }

    public PlayerTeamFacadeREST() {
        super(PlayerTeam.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(PlayerTeam entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, PlayerTeam entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.PlayerTeamId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PlayerTeam find(@PathParam("id") PathSegment id) {
        entity.PlayerTeamId key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PlayerTeam> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PlayerTeam> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @PUT
    @Path("JoinTeam/{player}/{team}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void joinTeam(@PathParam("player")Integer playerId, @PathParam("team") Integer teamId) {
        try {
            LOGGER.info("Fetching all teams of player");
            super.joinTeam(playerId, teamId);
        } catch (UpdateException ex) {
            LOGGER.info("Error fetching all teams of player");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}
