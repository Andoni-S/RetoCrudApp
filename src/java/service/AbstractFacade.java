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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public List<Game> findAllGamesCreatedByAdmin(String adminUsername)throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findAllGamesCreatedByAdmin")
                    .setParameter("adminUsername", adminUsername)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findAllGames() throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findAllGames").getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findGamesByName(String name) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByName", Game.class)
                    .setParameter("name", name)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findGamesByGenre(String genre) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByGenre", Game.class)
                    .setParameter("genre", genre)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    public List<Game> findGamesByPlatform(String platform) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByPlatform", Game.class)
                    .setParameter("platform", platform)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    /*public Set<Game> findGamesByPVPType(Enum pvpType) throws ReadException {
        Set<Game> games = null;
        try {
            games = (Set) em.createNamedQuery("findGamesByPVPType", Game.class)
                    .setParameter("pvpType", pvpType.ordinal())
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }*/

    public List<Game> findGamesByReleaseDate(Date releaseDate) throws ReadException {
    List<Game> games = null;
    try {
        // Format the Date to a String with the desired pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String formattedReleaseDate = dateFormat.format(releaseDate);

        // Use the formatted date in the query
        games = (List) getEntityManager().createNamedQuery("findGamesByReleaseDate", Game.class)
                .setParameter("releaseDate", formattedReleaseDate)
                .getResultList();
    } catch (Exception e) {
        throw new ReadException(e.getMessage());
    }
        return games;
    }

    public List<Game> findGamesByGenreAndReleaseDate(String genre, Date releaseDate) throws ReadException {
        List<Game> games = null;
        try {
            games = (List) getEntityManager().createNamedQuery("findGamesByGenreAndReleaseDate", Game.class)
                    .setParameter("genre", genre)
                    .setParameter("releaseDate", releaseDate)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return games;
    }

    @Transactional
    public Game createGame(Game newGame) throws CreateException {
        try {
            getEntityManager().persist(newGame);
            getEntityManager().flush();
            return newGame;
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Transactional
    public Game updateGame(Game gameToUpdate) throws UpdateException {
        try {
            Game updatedGame = (Game) getEntityManager().merge(gameToUpdate);
            getEntityManager().flush();
            return updatedGame;
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Transactional
    public void deleteGame(Game gameToDelete) throws DeleteException {
        try {
            gameToDelete = getEntityManager().merge(gameToDelete);
            getEntityManager().remove(gameToDelete);
            getEntityManager().flush();
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }
}
