package ejb;

import exception.ReadException;
import exception.CreateException;
import exception.UpdateException;
import exception.DeleteException;
import entity.User;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class UserEJB implements UserEJBLocal {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("java");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;

    @Override
    public User findUserByLogin(String login) throws ReadException {
        User user = null;
        try {
            LOGGER.info("UserManager: Finding user by login.");
            user = em.find(User.class, login);
            if (user != null)
                LOGGER.log(Level.INFO, "UserManager: User found {0}", user.getEmail());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception finding user by login:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading all users.");
            users = em.createNamedQuery("findAllUsers").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all users:", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUserByUsername(String username) throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users by username.");
            users = em.createNamedQuery("findUserByUsername", User.class)
                    .setParameter("username", username)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users by username", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUsersByEmail(String email) throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users by email.");
            users = em.createNamedQuery("findUsersByEmail", User.class)
                    .setParameter("email", email)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users by email", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUsersByName(String name) throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users by name.");
            users = em.createNamedQuery("findUsersByName", User.class)
                    .setParameter("name", name)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users by name", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUsersByBirthDate(Date birthDate) throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users by birth date.");
            users = em.createNamedQuery("findUsersByBirthDate", User.class)
                    .setParameter("birthDate", birthDate)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users by birth date", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUsersByBirthDateBefore(Date birthDate) throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users born before a certain date.");
            users = em.createNamedQuery("findUsersByBirthDateBefore", User.class)
                    .setParameter("birthDate", birthDate)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users born before a certain date", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUsersByBirthDateAfter(Date birthDate) throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users born after a certain date.");
            users = em.createNamedQuery("findUsersByBirthDateAfter", User.class)
                    .setParameter("birthDate", birthDate)
                    .getResultList();
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users born after a certain date", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUsersByNameAndSurname(String name, String surnames) throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users by name and surname.");
            users = em.createNamedQuery("findUsersByNameAndSurname", User.class)
                    .setParameter("name", name)
                    .setParameter("surnames", surnames)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users by name and surname", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public List<User> findUsersBornAfterAvgBirthDate() throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("UserManager: Reading users born after the average birth date.");
            users = em.createNamedQuery("findUsersBornAfterAvgBirthDate", User.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading users born after the average birth date", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Transactional
    @Override
    public User createUser(User newUser) throws CreateException {
        try {
            LOGGER.info("UserManager: Creating a new user.");
            em.persist(newUser);
            em.flush(); // Ensures the entity is immediately persisted

            return newUser;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception creating a new user", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public User updateUser(User userToUpdate) throws UpdateException {
        try {
            LOGGER.info("UserManager: Updating a user.");
            User updatedUser = em.merge(userToUpdate);
            em.flush(); // Ensures the changes are immediately updated

            return updatedUser;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating a user", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteUser(User userToDelete) throws DeleteException {
        try {
            LOGGER.info("UserManager: Deleting a user.");
            userToDelete = em.merge(userToDelete);
            em.remove(userToDelete);
            em.flush(); // Ensures the entity is immediately removed

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserManager: Exception deleting a user", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
