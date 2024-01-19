/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Result;
import entity.Team;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
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
@Path("entity.team")
public class TeamFacadeREST extends AbstractFacade<Team> {

     private static final Logger LOGGER = Logger.getLogger("java");
    
    @PersistenceContext(unitName = "RetoCrudAppPU")
    private EntityManager em;

    public TeamFacadeREST() {
        super(Team.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Team entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Team entity) {
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
    public Team find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("allTeams")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findAllTeams() throws ReadException {
        try {
            LOGGER.info("Fetching all teams");
            return super.findAllTeams();
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all teams");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
   /* @GET
    @Override
    @Path("byName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findTeamsByName(@PathParam("name") String name) {
        try {
            LOGGER.info("Fetching teams by name");
            return super.findTeamsByName(name);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching by name");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }*/
    
    @GET
    @Path("byDate/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findTeamsByDate(@PathParam("date") String date) {
        try {
            LOGGER.info("Fetching teams by date");
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
            Date newDate = Date.from(offsetDateTime.toInstant());
            return super.findTeamsByDate(newDate);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching teams by date");
            throw new InternalServerErrorException(ex.getMessage());
        }  
    }

    @GET
    @Override
    @Path("byCoach/{coach}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findTeamsByCoach(@PathParam("coach") String coach) {
        try {
            LOGGER.info("Fetching teams by coach");
            return super.findTeamsByCoach(coach);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching teams by coach");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
   /** @GET
    @Override
    @Path("byPlayerName/playersInTeam/{playerId}/player/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findTeamsByPlayerName(@PathParam("name") String name) {
        try {
            LOGGER.info("Fetching teams by player name");
            return super.findTeamsByPlayerName(name);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching by player name");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
**/
    @GET
    @Override
    @Path("Won/teamevents")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findTeamsWithWins() {
         try {
            LOGGER.info("Fetching all teams that won");
            return super.findTeamsWithWins();
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all teams that won");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @POST
    @Override
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

