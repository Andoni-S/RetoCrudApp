/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Organizer;
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
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Stateless
@Path("entity.organizer")
public class OrganizerFacadeREST extends AbstractFacade<Organizer> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(OrganizerFacadeREST.class.getName());

    private Hash hashUtil = new Hash();

    public OrganizerFacadeREST() {
        super(Organizer.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Organizer entity) {
        try {
            LOGGER.log(Level.INFO, "Creating organizer: {0}", entity.toString());
            // Hash the password before persisting the entity
            entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
            super.create(entity);
            LOGGER.log(Level.INFO, "Organizer created successfully: {0}", entity.toString());
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Exception creating an organizer:{0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Organizer entity) {
        try {
            LOGGER.log(Level.INFO, "Editing organizer with ID {0}", id);
            // Check if the password is present before hashing and updating
            if (entity.getPassword() != null) {
                entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
            }
            super.edit(entity);
            LOGGER.log(Level.INFO, "Organizer edited successfully: {0}", entity.toString());
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Exception editing organizer with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            LOGGER.log(Level.INFO, "Removing organizer with ID {0}", id);
            super.remove(super.find(id));
            LOGGER.log(Level.INFO, "Organizer removed successfully");
        } catch (DeleteException | ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception removing organizer with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Organizer find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding organizer with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Organizer> findAll() {
        try {
            return super.findAll();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding all organizers: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Organizer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding organizers in range [{0}, {1}]: {2}", new Object[]{from, to, e.getMessage()});
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
