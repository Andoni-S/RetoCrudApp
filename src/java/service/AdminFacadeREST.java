package service;

import entity.Admin;
import entity.Game;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

@Stateless
@Path("entity.admin")
public class AdminFacadeREST extends AbstractFacade<Admin> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    /**
     * Logger for this class.
     */
    private Logger LOGGER = Logger.getLogger(AdminFacadeREST.class.getName());

    public AdminFacadeREST() {
        super(Admin.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Admin entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Admin entity) {
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
    public Admin find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Admin> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    /*@GET
    @Path("gamesByPVPType/{pvpType}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Set<Game> findGamesByPVPType(@PathParam("pvpType") String pvpType) {
        try {
            LOGGER.log(Level.INFO, "Fetching games by PVPType: {0}", pvpType);
            return super.findGamesByPVPType(pvpType);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by PVPType", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }*/

    @GET
    @Override
    @Path("gamesByReleaseDate/{releaseDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})  
    public List<Game> findGamesByReleaseDate(@PathParam("releaseDate") Date releaseDate) {
        try {
            LOGGER.log(Level.INFO, "Fetching games by release date: {0}", releaseDate);
            return super.findGamesByReleaseDate(releaseDate);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching games by release date", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Override
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
