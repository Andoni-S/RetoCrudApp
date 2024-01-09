/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Player;
import entity.Result;
import entity.Team;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Stateless
@Path("entity.player")
public class PlayerFacadeREST extends AbstractFacade<Player> {

    private static final Logger LOGGER = Logger.getLogger("java");
    
    @PersistenceContext(unitName = "RetoAppCrudPU")
    private EntityManager em;

    public PlayerFacadeREST() {
        super(Player.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Player entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Player entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Player find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Player> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Player> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("allTeams")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Team> findAllTeams() throws ReadException {
        try {
            LOGGER.info("Fetching all games");
            return super.findAllTeams();
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all games");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("byName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Team> findTeamsByName(@PathParam("name") String name) {
        try {
            LOGGER.info("Fetching all games");
            return super.findTeamsByName(name);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all games");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("byDate/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Team> findTeamsByDate(@PathParam("date") Date date) {
        try {
            LOGGER.info("Fetching all games");
            return super.findTeamsByDate(date);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all games");
            throw new InternalServerErrorException(ex.getMessage());
        }  
    }

    @GET
    @Path("byCoach/{coach}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Team> findTeamsByCoach(@PathParam("coach") String coach) {
        try {
            LOGGER.info("Fetching all games");
            return super.findTeamsByCoach(coach);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all games");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("byPlayerId/{playerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Team> findTeamsByPlayerId(@PathParam("playerId") Long playerId) {
        try {
            LOGGER.info("Fetching all games");
            return super.findTeamsByPlayerId(playerId);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all games");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
/**
    @GET
    @Path("byWins/{result}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Set<Team> findTeamsWithWins(@PathParam("result") Result result) {
         try {
            LOGGER.info("Fetching all games");
            return super.findTeamsWithWins(result);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all games");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
   **/ 
    @POST
    @Path("createTeam")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Team createTeam (Team newTeam) {
        try {
            LOGGER.info("Creating a new team");
            return super.createTeam(newTeam);
        } catch (CreateException ex) {
            LOGGER.info("Error creating a new team");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @PUT
    @Override
    @Path("updateTeam")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Team updateTeam(Team teamToUpdate) {
        try {
            LOGGER.info("Updating a team");
            return super.updateTeam(teamToUpdate);
        } catch (UpdateException ex) {
            LOGGER.info("Error updating a team");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @DELETE
    @Override
    @Path("deleteTeam")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void deleteTeam(Team teamToDelete) {
        try {
            LOGGER.info("Deleting a team");
            super.deleteTeam(teamToDelete);
        } catch (DeleteException ex) {
            LOGGER.info("Error deleting a team");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

}
