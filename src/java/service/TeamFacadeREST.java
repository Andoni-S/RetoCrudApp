package service;

import entity.Player;
import entity.PlayerTeam;
import entity.Team;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.time.OffsetDateTime;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
 * This class represents a RESTful web service for managing Team entities. It
 * extends the AbstractFacade class and provides CRUD operations for Team
 * entities. Additionally, it includes methods for handling specific operations
 * related to teams.
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
        try {
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.log(Level.SEVERE, "Exception creating a team: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Team entity) {
        try {
            super.edit(entity);
        } catch (UpdateException e) {
            LOGGER.log(Level.SEVERE, "Exception editing team with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        try {
            deletePlayerTeamByTeamId(id);
            super.remove(super.find(id));
        } catch (ReadException | DeleteException e) {
            LOGGER.log(Level.SEVERE, "Exception removing team with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Team find(@PathParam("id") Long id) {
        try {
            return super.find(id);
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding team with ID {0}: {1}", new Object[]{id, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findAll() {
        try {
            return super.findAll();
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding all teams: {0}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException e) {
            LOGGER.log(Level.SEVERE, "Exception finding teams in range [{0}, {1}]: {2}", new Object[]{from, to, e.getMessage()});
            throw new InternalServerErrorException(e.getMessage());
        }
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

    @GET
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
    }

    @GET
    @Path("byDate/{date}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findTeamsByDate(@PathParam("date") String date) {
        try {
            LOGGER.info("Fetching teams by date");
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
            Date newDate = (Date) Date.from(offsetDateTime.toInstant());
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

    /*public List<Team> findTeamsByPlayerName(@PathParam("name") String name) {
        try {
            LOGGER.info("Fetching teams by player name");
            return super.findTeamsByPlayerName(name);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching by player name");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }*/
    @GET
    @Path("Won/{team_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findTeamsWithWins(@PathParam("team_id") Long teamId) {
        try {
            LOGGER.info("Fetching all teams that won");
            return super.findTeamsWithWins(teamId);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all teams that won");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("findPlayerLevelById/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Integer findPlayerLevelById(@PathParam("id") Long id) {
        try {
            LOGGER.log(Level.INFO, "Fetching player by id: {0}", id);
            return super.findPlayerById(id).getLevel();

        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching player by id", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("MyTeams/{player_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Team> findMyTeams(@PathParam("player_id") Long player_id) {
        try {
            LOGGER.info("Fetching all teams of player");
            return super.findMyTeams(player_id);
        } catch (ReadException ex) {
            LOGGER.info("Error fetching all teams of player");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @POST
    @Path("joinTeam/{teamId}/{playerId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void joinTeam(@PathParam("teamId") Long teamId, @PathParam("playerId") Long playerId) {
        try {
            LOGGER.info("Joining player to team");
            Player player = getEntityManager().find(Player.class, playerId);
            Team team = getEntityManager().find(Team.class, teamId);

            PlayerTeam pt = new PlayerTeam();
            pt.setPlayer(player);
            pt.setTeam(team);

            // Add the new PlayerTeam to the player's set of teams
            Set<PlayerTeam> playerTeams = player.getTeams();
            playerTeams.add(pt);
            player.setTeams(playerTeams);

            // Add the new PlayerTeam to the team's set of players
            Set<PlayerTeam> teamPlayers = team.getPlayers();
            teamPlayers.add(pt);
            team.setPlayers(teamPlayers);

            super.createPlayerTeam(pt);
        } catch (CreateException ex) {
            Logger.getLogger(TeamFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deletePlayerTeamByTeamId(Long teamId) {
        try {
            Query query = em.createNamedQuery("deletePlayerTeamByTeamId");
            query.setParameter("teamId", teamId);
            query.executeUpdate();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error deleting player teams", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @POST
    @Override
    @Path("createTeam")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Team createTeam(Team newTeam) {
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

    /*@DELETE
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
    }*/
}
