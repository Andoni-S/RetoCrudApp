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
    public void create(Organizer entity) throws CreateException {
        LOGGER.log(Level.INFO, "Creating organizer: {0}", entity.toString());
        // Hash the password before persisting the entity
        entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
        super.create(entity);
        LOGGER.log(Level.INFO, "Organizer created successfully: {0}", entity.toString());
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Organizer entity) throws UpdateException {
        LOGGER.log(Level.INFO, "Editing organizer with ID {0}", id);
        // Check if the password is present before hashing and updating
        if (entity.getPassword() != null) {
            entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
        }
        super.edit(entity);
        LOGGER.log(Level.INFO, "Organizer edited successfully: {0}", entity.toString());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws DeleteException {
        try {
            LOGGER.log(Level.INFO, "Removing organizer with ID {0}", id);
            super.remove(super.find(id));
            LOGGER.log(Level.INFO, "Organizer removed successfully");
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error finding organizer:{0}", e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Organizer find(@PathParam("id") Long id) throws ReadException {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Organizer> findAll() throws ReadException {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Organizer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) throws ReadException {
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
}
