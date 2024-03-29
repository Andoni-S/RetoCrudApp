package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents a Team entity in the esports system.
 *
 * The Team class is an entity class representing a team in the system.
 * It includes attributes such as id, players, name, foundation, coach, and teamevents.
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name = "team", schema = "esportsdb")
@NamedQueries({
    @NamedQuery(name = "findTeamsByName", query = "SELECT t FROM Team t WHERE t.name = :name")
    ,
    @NamedQuery(name = "findTeamsByDate", query = "SELECT t FROM Team t WHERE t.foundation = :date")
    ,
    @NamedQuery(name = "findTeamsByCoach", query = "SELECT t FROM Team t WHERE t.coach = :coach")
    ,
    @NamedQuery(name = "findTeamsWithWins", query = "SELECT te.team FROM TeamEvent te WHERE te.team.id = :team_id AND te.result = :result")
    ,
    @NamedQuery(name = "findAllTeams", query = "SELECT t FROM Team t")
    ,
    @NamedQuery(name = "deletePlayerTeamByTeamId", query = "DELETE FROM PlayerTeam pt WHERE pt.team.id = :teamId")
})
@XmlRootElement
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @OneToMany(mappedBy = "team", fetch = EAGER, cascade = CascadeType.REMOVE)
    Set<PlayerTeam> players;

    private String name;
  
    @Temporal(TemporalType.TIMESTAMP)
    private Date foundation;
  
    private String coach;
  
    @OneToMany(cascade = ALL, mappedBy = "team", fetch = EAGER)
    private Set<TeamEvent> teamevents;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getFoundation() {
        return foundation;
    }

    public String getCoach() {
        return coach;
    }

    @XmlTransient
    public Set<PlayerTeam> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerTeam> players) {
        this.players = players;
    }

    public void setTeamevents(Set<TeamEvent> teamevents) {
        this.teamevents = teamevents;
    }

    @XmlTransient
    public Set<TeamEvent> getTeamevents() {
        return teamevents;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoundation(Date foundation) {
        this.foundation = foundation;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same reference as this (the same object)
        if (this == obj) {
            return true;
        }
        // Check if the passed object is null or not an instance of Team
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Convert the passed object to Team to compare the IDs
        Team team = (Team) obj;
        // Compare the IDs using Objects.equals to handle null values
        return Objects.equals(this.getId(), this.getId());
    }

}