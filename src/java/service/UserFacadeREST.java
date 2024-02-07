/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
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
import mail.SendMail;
import security.Decrypt;
import security.Hash;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    private Hash hashUtil = new Hash();

    /**
     * Logger for this class.
     */
    private Logger LOGGER = Logger.getLogger(AdminFacadeREST.class.getName());

    public UserFacadeREST() {

        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) throws CreateException {
        entity.setPassword(hashUtil.hashPassword(entity.getPassword()));
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, User entity) throws UpdateException {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws DeleteException {
        try {
            super.remove(super.find(id));
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Error finding user:{0}", e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Long id) throws ReadException {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() throws ReadException {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findUsersByEmail/{mail}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User findUsersByEmail(@PathParam("mail") String mail) {
        try {
            LOGGER.log(Level.INFO, "Fetching user by mail");
            return super.findUsersByEmail(mail);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all games created by admin", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User login(User loginUser) {
        User newUser = new User();
        String password = new Decrypt().decrypt(loginUser.getPassword());
        String passwordDB = null;
        try {
            LOGGER.log(Level.INFO, "Log In User");
            loginUser = super.findUserByEmail(loginUser.getEmail());

            passwordDB = loginUser.getPassword();
            if (passwordDB.equals(password)) {
                newUser.setId(loginUser.getId());
                newUser.setEmail(loginUser.getEmail());
                newUser.setName(loginUser.getName());
                newUser.setPassword(loginUser.getPassword());
                newUser.setSurnames(loginUser.getSurnames());
                newUser.setUsername(loginUser.getUsername());
                newUser.setBirthDate(loginUser.getBirthDate());
                newUser.setUser_type(loginUser.getUser_type());
            } else {
                throw new InternalServerErrorException();
            }

        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error creating a new game", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
        return newUser;
    }

    @PUT
    @Path("passwordRecovery")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void passwordRecovery(User user) throws UpdateException {
        User newUser = new User();
        try {
            LOGGER.log(Level.INFO, "Creating a new password");
            //send email and get the generated password
            String newPassword = SendMail.sendPasswordRecovery(user.getEmail());
            //get the user of the generated password
            newUser = super.findUserByEmail(user.getEmail());
            //set the new password and change it on the database
            newUser.setPassword(newPassword);
            edit(newUser.getId(), newUser);

            //return newUser;
        } catch (ReadException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return null;
    }

}
