/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Event;
import entity.Player;
import entity.Team;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    
    private static final Logger LOGGER = Logger.getLogger("javafxserverside");

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
    
        public List<Event> findEventsByOrganizer(String organizerName) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events by organizer.");
            TypedQuery<Event> query = getEntityManager().createNamedQuery("findEventsByOrganizer", Event.class);
            query.setParameter("organizerName", organizerName);
            events = query.getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events by organizer {1}", new Object[]{events.size(), organizerName});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events by organizer:", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return events;
    }

    public List<Event> findEventsByGame(String gameName) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events by game.");
            TypedQuery<Event> query = getEntityManager().createNamedQuery("findEventsByGame", Event.class);
            query.setParameter("gameName", gameName);
            events = query.getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events by game {1}", new Object[]{events.size(), gameName});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events by game:", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return events;
    }

    public List<Event> findEventsWonByPlayer(Player player, String result) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events won by player.");
            TypedQuery<Event> query = getEntityManager().createNamedQuery("findEventsWonByPlayer", Event.class);
            query.setParameter("player", player);
            query.setParameter("result", result);
            events = query.getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events won by player {1}", new Object[]{events.size(), player.getName()});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events won by player:", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return events;
    }

    public List<Event> findEventsWonByTeam(Team team, String result) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events won by team.");
            TypedQuery<Event> query = getEntityManager().createNamedQuery("findEventsWonByTeam", Event.class);
            query.setParameter("team", team);
            query.setParameter("result", result);
            events = query.getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events won by team {1}", new Object[]{events.size(), team.getName()});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events won by team:", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return events;
    }

    public List<Event> findEventsByONG(String ongName) throws Exception {
        List<Event> events = null;
        try {
            LOGGER.info("EventFacade: Finding events by ONG.");
            TypedQuery<Event> query = getEntityManager().createNamedQuery("findEventsByONG", Event.class);
            query.setParameter("ongName", ongName);
            events = query.getResultList();
            LOGGER.log(Level.INFO, "EventFacade: Found {0} events associated with ONG {1}", new Object[]{events.size(), ongName});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventFacade: Exception finding events by ONG:", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return events;
    }
    
}
