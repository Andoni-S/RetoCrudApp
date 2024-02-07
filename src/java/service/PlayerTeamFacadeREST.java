package service;

import entity.PlayerTeam;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
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

/**
 * This class represents a RESTful web service for managing PlayerTeam entities.
 * It extends the AbstractFacade class and provides CRUD operations for
 * PlayerTeam entities.
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.playerteam")
public class PlayerTeamFacadeREST extends AbstractFacade<PlayerTeam> {

    private static final Logger LOGGER = Logger.getLogger("java");

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    public PlayerTeamFacadeREST() {
        super(PlayerTeam.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(PlayerTeam entity) {
        try {
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Exception creating a player team: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, PlayerTeam entity) {
        try {
            super.edit(entity);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Exception editing player team with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            super.remove(super.find(id));
        } catch (ReadException | DeleteException e) {
            LOGGER.log(Level.SEVERE, "Exception removing player team with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public PlayerTeam find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding player team with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PlayerTeam> findAll() {
        try {
            return super.findAll();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding all player teams: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<PlayerTeam> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding player teams in range [{0}, {1}]: {2}", new Object[]{from, to, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
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
}
