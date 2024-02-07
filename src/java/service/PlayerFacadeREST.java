package service;

import entity.Player;
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
import security.Hash;

/**
 * This class represents a RESTful web service for managing Player entities. It
 * extends the AbstractFacade class and provides CRUD operations for Player
 * entities.
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.player")
public class PlayerFacadeREST extends AbstractFacade<Player> {

    private static final Logger LOGGER = Logger.getLogger("java");

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private Hash hashUtil = new Hash();

    public PlayerFacadeREST() {
        super(Player.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Player entity) {
        try {
            // Hash the password before persisting the entity
            entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Exception creating a player: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Player entity) {
        try {
            // Check if the password is present before hashing and updating
            if (entity.getPassword() != null) {
                entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
            }
            super.edit(entity);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Exception editing player with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            super.remove(super.find(id));
        } catch (ReadException | DeleteException e) {
            LOGGER.log(Level.SEVERE, "Exception removing player with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Player find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding player with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Player> findAll() {
        try {
            return super.findAll();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding all players: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Player> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding players in range [{0}, {1}]: {2}", new Object[]{from, to, e.getMessage()});
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

    @GET
    @Path("findPlayerLevelById/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String findPlayerLevelById(@PathParam("id") Long id) {
        try {
            LOGGER.log(Level.INFO, "Fetching player level by id: {0}", id);
            Integer lvl = super.findPlayerById(id).getLevel();
            String lvlstr = String.valueOf(lvl);
            return lvlstr;

        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching player level by id", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /*@GET
    @Path("MyTeams/{teamsOfPlayer}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findMyTeams(@PathParam("player") Player player) {
        try {
            LOGGER.info("Fetching all teams of player");
            return super.findMyTeams(player);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all teams of player");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }*/
}
