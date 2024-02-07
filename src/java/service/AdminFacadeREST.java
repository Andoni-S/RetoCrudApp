package service;

import entity.Admin;
import entity.Game;
import entity.PVPType;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.time.OffsetDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import java.sql.Date;
import security.Hash;

@Stateless
@Path("entity.admin")
public class AdminFacadeREST extends AbstractFacade<Admin> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private Hash hashUtil = new Hash();
    /**
     * Logger for this class.
     */
    private Logger LOGGER = Logger.getLogger(AdminFacadeREST.class.getName());

    public AdminFacadeREST() {
        // Hash the password before persisting the entity
        super(Admin.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Admin entity) {
        try {
            entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Exception creating an admin: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Admin entity) {
        try {
            super.edit(entity);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Exception editing admin with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            super.remove(super.find(id));
        } catch (DeleteException | ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception removing admin with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Admin find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding admin with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findAll() {
        try {
            return super.findAll();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding all admins: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding admins in range [{0}, {1}]: {2}", new Object[]{from, to, e.getMessage()});
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
    @Override
    @Path("allGamesCreatedByAdmin/{username}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findAllGamesCreatedByAdmin(@PathParam("username") String name) {
        try {
            LOGGER.log(Level.INFO, "Fetching all games created by admin");
            return super.findAllGamesCreatedByAdmin(name);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all games created by admin", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Override
    @Path("allGames")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findAllGames() {
        try {
            LOGGER.log(Level.INFO, "Fetching all games");
            return super.findAllGames();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all games", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Override
    @Path("gamesByName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findGamesByName(@PathParam("name") String name) {
        try {
            LOGGER.log(Level.INFO, "Fetching games by name: {0}", name);
            return super.findGamesByName(name);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by name", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Override
    @Path("gamesByGenre/{genre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findGamesByGenre(@PathParam("genre") String genre) {
        try {
            LOGGER.log(Level.INFO, "Fetching games by genre: {0}", genre);
            return super.findGamesByGenre(genre);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by genre", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Override
    @Path("gamesByPlatform/{platform}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findGamesByPlatform(@PathParam("platform") String platform) {
        try {
            LOGGER.log(Level.INFO, "Fetching games by platform: {0}", platform);
            return super.findGamesByPlatform(platform);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by platform", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("gamesByPVPType/{pvpType}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findGamesByPVPType(@PathParam("pvpType") String pvpType) {
        try {
            LOGGER.log(Level.INFO, "Fetching games by PVPType: {0}", pvpType);
            return super.findGamesByPVPType(PVPType.valueOf(pvpType));
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by PVPType", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("gamesByReleaseDate/{releaseDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findGamesByReleaseDate(@PathParam("releaseDate") String releaseDate) {
        try {
            LOGGER.info("Fetching all games");
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(releaseDate);
            Date newDate = (Date) Date.from(offsetDateTime.toInstant());
            return super.findGamesByReleaseDate(newDate);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by release date", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("gamesByGenreAndReleaseDate/{genre}/{releaseDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findGamesByGenreAndReleaseDate(
            @PathParam("genre") String genre,
            @PathParam("releaseDate") Date releaseDate) {
        try {
            LOGGER.log(Level.INFO, "Fetching games by genre {0} and release date {1}", new Object[]{genre, releaseDate});
            return super.findGamesByGenreAndReleaseDate(genre, releaseDate);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by genre and release date", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @POST
    @Override
    @Path("createGame")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Game createGame(Game newGame) {
        try {
            LOGGER.log(Level.INFO, "Creating a new game");
            return super.createGame(newGame);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "Error creating a new game", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @PUT
    @Override
    @Path("updateGame")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Game updateGame(Game gameToUpdate) {
        try {
            LOGGER.log(Level.INFO, "Updating a game");
            return super.updateGame(gameToUpdate);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "Error updating a game", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @DELETE
    @Override
    @Path("deleteGame")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void deleteGame(Game gameToDelete) {
        try {
            LOGGER.log(Level.INFO, "Deleting a game");
            super.deleteGame(gameToDelete);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "Error deleting a game", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}
