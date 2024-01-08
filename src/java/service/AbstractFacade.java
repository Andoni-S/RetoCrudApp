/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import entity.Result;
import entity.Team;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public abstract class AbstractFacade<T> {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("java");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
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
    
    /**
     * Finds a Set of {@link Team} entities based on the provided team name.
     *
     * @param name The name of the team to be found.
     * @return A {@link Set} of {@link Team} entities matching the specified
     * team name. If no teams are found, the Set will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public Set<Team> findTeamsByName(String name) {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            teams = (Set) em.createNamedQuery("findTeamByPlayerName").setParameter("name", name).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by name:", e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a Set of {@link Team} entities based on the provided foundation
     * date.
     *
     * @param date The foundation date of the teams to be found.
     * @return A {@link Set} of {@link Team} entities whose foundation date
     * matches the specified value. If no teams are found, the Set will be
     * empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public Set<Team> findTeamsByDate(Date date) {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by date.");
            teams = (Set) em.createNamedQuery("findTeamByDate").setParameter("date", date).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by date:", e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a Set of {@link Team} entities based on the provided coach name.
     *
     * @param coach The name of the coach associated with the teams to be found.
     * @return A {@link Set} of {@link Team} entities whose coach name matches
     * the specified value. If no teams are found, the Set will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public Set<Team> findTeamsByCoach(String coach) {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by coach.");
            teams = (Set) em.createNamedQuery("findTeamByCoach").setParameter("coach", coach).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by coach:", e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a {@link Team} by its ID.
     *
     * @param id The ID of the team to be found.
     * @return The {@link Team} object containing team data.
     */
    public Set<Team> findTeamsByPlayerId(Long id) {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            teams = (Set) em.createNamedQuery("findTeamByPlayerId").setParameter("playerId", id).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team by player id:",
                    e.getMessage());
        }
        return teams;
    }
   
    /**
     * Finds a Set of {@link Team} entities based on the provided result
     * indicating wins.
     *
     * @param result The result associated with the teams to be found.
     * @return A {@link Set} of {@link Team} entities with the specified result
     * indicating wins. If no teams are found, the Set will be empty.
     * @throws ReadException If there is any exception during the retrieval
     * process. Check the log for details.
     */
    public Set<Team> findTeamsWithWins(Result result) {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team wins by id.");
            teams = (Set) em.createNamedQuery("findTeamWithWins").setParameter("result", result).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception Finding team with wins by id:",
                    e.getMessage());
        }
        return teams;
    }
    
    
}
