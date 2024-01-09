/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import entity.Result;
import entity.Team;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
     * Retrieves all teams.
     *
     * <p>
     * This method queries the database to retrieve all {@link Team} entities
     * available. The result is returned as a {@link Set}. If no teams are
     * found, the Set will be empty.
     *
     * @return A {@link Set} of {@link Team} entities representing all teams.
     * @throws ReadException If an error occurs during the retrieval process.
     * Check the log for details.
     */
    public Set<Team> findAllTeams() throws ReadException {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding all teams.");
            teams = (Set) getEntityManager().createNamedQuery("findAllTeams").getResultList();
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding all teams.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
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
    public Set<Team> findTeamsByName(String name) throws ReadException {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            teams = (Set) em.createNamedQuery("findTeamByPlayerName").setParameter("name", name).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding teams by name.", e.getMessage());
            throw new ReadException(e.getMessage());
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
    public Set<Team> findTeamsByDate(Date date) throws ReadException {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by date.");
            teams = (Set) em.createNamedQuery("findTeamByDate").setParameter("date", date).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team by date.", e.getMessage());
            throw new ReadException(e.getMessage());
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
    public Set<Team> findTeamsByCoach(String coach) throws ReadException {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by coach.");
            teams = (Set) em.createNamedQuery("findTeamByCoach").setParameter("coach", coach).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team by coach.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }

    /**
     * Finds a {@link Team} by its ID.
     *
     * @param id The ID of the team to be found.
     * @return The {@link Team} object containing team data.
     */
    public Set<Team> findTeamsByPlayerId(Long id) throws ReadException {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team by id.");
            teams = (Set) em.createNamedQuery("findTeamByPlayerId").setParameter("playerId", id).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team by player id.", e.getMessage());
            throw new ReadException(e.getMessage());
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
    public Set<Team> findTeamsWithWins(Result result) throws ReadException {
        Set<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding team wins by id.");
            teams = (Set) em.createNamedQuery("findTeamWithWins").setParameter("result", result).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception finding team with wins by id.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return teams;
    }   
    
    /**
     * Creates a new Team.
     *
     * @param newTeam The Team entity to be created.
     * @return The newly created Team entity.
     * @throws CreateException If an error occurs during the creation process.
     */
    @Transactional
    public Team createTeam(Team newTeam) throws CreateException {
        try {
            em.persist(newTeam);
            em.flush();
            return newTeam;
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    
    /**
     * Updates an existing Team.
     *
     * @param teamToUpdate The Team entity to be updated.
     * @return The updated Team entity.
     * @throws UpdateException If an error occurs during the update process.
     */
    @Transactional
    public Team updateTeam(Team teamToUpdate) throws UpdateException {
        try {
            Team updatedTeam = em.merge(teamToUpdate);
            em.flush();
            return updatedTeam;
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }
    
    /**
     * Deletes an existing Team.
     *
     * @param teamToDelete The Team entity to be deleted.
     * @throws DeleteException If an error occurs during the delete process.
     */
    @Transactional
    public void deleteTeam(Team teamToDelete) throws DeleteException {
        try {
            teamToDelete = em.merge(teamToDelete);
            em.remove(teamToDelete);
            em.flush();
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }
}
