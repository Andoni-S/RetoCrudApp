package rest; // Update with your actual package structure

import ejb.UserEJBLocal;
import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("users")
public class UserREST {

    private static final Logger LOGGER = Logger.getLogger("rest"); // Update with your actual package structure

    @EJB
    private UserEJBLocal ejb;

    @POST
    @Consumes({"application/xml"})
    public void create(User user) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: create {0}.", user);
            ejb.createUser(user);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception creating user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @PUT
    @Consumes({"application/xml"})
    public void update(User user) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: update {0}.", user);
            ejb.updateUser(user);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception updating user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: delete User by id={0}.", id);
            ejb.deleteUser(ejb.findUserByLogin(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception deleting user by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml"})
    public User find(@PathParam("id") String id) {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find User by id={0}.", id);
            user = ejb.findUserByLogin(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading user by id, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return user;
    }

    @GET
    @Produces({"application/xml"})
    public List<User> findAll() {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find all users.");
            users = ejb.findAllUsers();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading all users, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }

    @GET
    @Path("username/{username}")
    @Produces({"application/xml"})
    public List<User> findUserByUsername(@PathParam("username") String username) {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find users by username {0}.", username);
            users = ejb.findUserByUsername(username);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading users by username, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }

    // Add similar methods for other operations (find by email, find by name, etc.)
    
    @GET
    @Path("birthDate/{birthDate}")
    @Produces({"application/xml"})
    public List<User> findUsersByBirthDate(@PathParam("birthDate") Date birthDate) {
        List<User> users = null;
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: find users by birth date {0}.", birthDate);
            users = ejb.findUsersByBirthDate(birthDate);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception reading users by birth date, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }

    // Add similar methods for other operations
    
    @POST
    @Path("create")
    @Consumes({"application/xml"})
    public User createUser(User newUser) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: create {0}.", newUser);
            return ejb.createUser(newUser);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception creating user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @PUT
    @Path("update")
    @Consumes({"application/xml"})
    public User updateUser(User userToUpdate) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: update {0}.", userToUpdate);
            return ejb.updateUser(userToUpdate);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception updating user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @DELETE
    @Path("delete")
    public void deleteUser(User userToDelete) {
        try {
            LOGGER.log(Level.INFO, "UserRESTful service: delete User by id={0}.", userToDelete.getId());
            ejb.deleteUser(userToDelete);
        } catch (DeleteException ex) {
            LOGGER.log(Level.SEVERE, "UserRESTful service: Exception deleting user, {0}", ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
}
