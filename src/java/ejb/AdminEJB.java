/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entity.Game;
import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author 2dam
 */
@Stateless
public class AdminEJB implements AdminEJBLocal{
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("java");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     * @return A List of {@link User} objects.
     */
    @Override
    public List<Game> findAllGamesCreatedByAdmin() {
        List<Game> games=null;
        try{
            LOGGER.info("UserManager: Reading all games.");
            games=em.createNamedQuery("findAllGamesCreatedByAdmin").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all games created by admin",
                    e.getMessage());
        }
        return games;
    }
    
    public List<User> findAllAdmins() {
        List<User> admins = null;
        try {
            LOGGER.info("AdminManager: Reading all admins.");
            admins = em.createNamedQuery("findAllAdmins", User.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception reading all admins", e.getMessage());
        }
        return admins;
    }

    public List<User> findAdminsByPermissions(Enum permissions) {
        List<User> admins = null;
        try {
            LOGGER.info("AdminManager: Reading admins by permissions.");
            admins = em.createNamedQuery("findAdminByPermissions", User.class)
                    .setParameter("permissions", permissions)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception reading admins by permissions", e.getMessage());
        }
        return admins;
    }
    
    @Transactional
    public User createAdmin(User newAdmin) {
        try {
            LOGGER.info("AdminManager: Creating a new admin.");
            em.persist(newAdmin);
            em.flush(); // Ensures the entity is immediately persisted

            return newAdmin;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception creating a new admin", e.getMessage());
            return null;
        }
    }

    @Transactional
    public User updateAdmin(User adminToUpdate) {
        try {
            LOGGER.info("AdminManager: Updating an admin.");
            User updatedAdmin = em.merge(adminToUpdate);
            em.flush(); // Ensures the changes are immediately updated

            return updatedAdmin;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception updating an admin", e.getMessage());
            return null;
        }
    }

    @Transactional
    public void deleteAdmin(User adminToDelete) {
        try {
            LOGGER.info("AdminManager: Deleting an admin.");
            adminToDelete = em.merge(adminToDelete); // Merge if the entity is not in the managed state
            em.remove(adminToDelete);
            em.flush(); // Ensures the entity is immediately removed
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "AdminManager: Exception deleting an admin", e.getMessage());
        }
    }

    @Override
    public List<Game> findAllGames() throws ReadException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Reading all games.");
            games = em.createNamedQuery("findAllGames").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception reading all games", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Override
    public List<Game> findGamesByName(String name) throws ReadException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Reading games by name.");
            games = em.createNamedQuery("findGamesByName", Game.class)
                    .setParameter("name", name)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception reading games by name", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Override
    public List<Game> findGamesByGenre(String genre) throws ReadException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Reading games by genre.");
            games = em.createNamedQuery("findGamesByGenre", Game.class)
                    .setParameter("genre", genre)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception reading games by genre", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Override
    public List<Game> findGamesByPlatform(String platform) throws ReadException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Reading games by platform.");
            games = em.createNamedQuery("findGamesByPlatform", Game.class)
                    .setParameter("platform", platform)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception reading games by platform", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Override
    public List<Game> findGamesByPVPType(Enum pvpType) throws ReadException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Reading games by PVPType.");
            games = em.createNamedQuery("findGamesByPVPType", Game.class)
                    .setParameter("pvpType", pvpType)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception reading games by PVPType", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Override
    public List<Game> findGamesByReleaseDate(Date releaseDate) throws ReadException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Reading games by release date.");
            games = em.createNamedQuery("findGamesByReleaseDate", Game.class)
                    .setParameter("releaseDate", releaseDate)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception reading games by release date", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Override
    public List<Game> findGamesByGenreAndReleaseDate(String genre, Date releaseDate) throws ReadException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Reading games by genre and release date.");
            games = em.createNamedQuery("findGamesByGenreAndReleaseDate", Game.class)
                    .setParameter("genre", genre)
                    .setParameter("releaseDate", releaseDate)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception reading games by genre and release date", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Transactional
    @Override
    public Game createGame(Game newGame) throws CreateException {
        try {
            LOGGER.info("GameManager: Creating a new game.");
            em.persist(newGame);
            em.flush(); // Ensures the entity is immediately persisted

            return newGame;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception creating a new game", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Game updateGame(Game gameToUpdate) throws UpdateException {
        try {
            LOGGER.info("GameManager: Updating a game.");
            Game updatedGame = em.merge(gameToUpdate);
            em.flush(); // Ensures the changes are immediately updated

            return updatedGame;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception updating a game", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteGame(Game gameToDelete) throws DeleteException {
        try {
            LOGGER.info("GameManager: Deleting a game.");
            gameToDelete = em.merge(gameToDelete);
            em.remove(gameToDelete);
            em.flush(); // Ensures the entity is immediately removed

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GameManager: Exception deleting a game", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
}
