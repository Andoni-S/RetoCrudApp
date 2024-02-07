/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Game;
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
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.game")
public class GameFacadeREST extends AbstractFacade<Game> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(GameFacadeREST.class.getName());

    public GameFacadeREST() {
        super(Game.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Game entity) {
        try {
            if (getEntityManager().contains(entity)) {
                entity = getEntityManager().merge(entity);
            }
            getEntityManager().merge(entity);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception creating a game: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Game entity) throws UpdateException {
        try {
            super.edit(entity);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Exception editing game with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws DeleteException {
        try {
            super.remove(super.find(id));
        } catch (DeleteException | ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception removing game with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Game find(@PathParam("id") Long id) throws ReadException {
        try {
            return super.find(id);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding game with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findAll() throws ReadException {
        try {
            return super.findAll();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding all games: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Game> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding games in range [{0}, {1}]: {2}", new Object[]{from, to, e.getMessage()});
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
