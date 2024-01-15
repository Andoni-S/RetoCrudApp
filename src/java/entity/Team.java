/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name = "team", schema = "esportsdb")
@NamedQueries({
    @NamedQuery(name = "findAllTeams", query = "SELECT t FROM Team t")
    ,
    @NamedQuery(name= "findTeamsByName", query = "SELECT t FROM Team t WHERE t.name = :name")
    ,
    @NamedQuery(name = "findTeamsByPlayerName", query = "SELECT t FROM Team t JOIN t.playersInTeam tp JOIN tp.teamsOfPlayer p WHERE p.name = :playerName")
    ,
    @NamedQuery(name = "findTeamsByDate", query = "SELECT t FROM Team t WHERE t.foundation = :date")
    ,
    @NamedQuery(name = "findTeamsByCoach", query = "SELECT t FROM Team t WHERE t.coach = :coach")
    ,
    @NamedQuery(name = "findTeamsWithWins", query = "SELECT t from Team t JOIN t.teamevents te WHERE te.result = :result")
    ,
    @NamedQuery(name = "findMyTeams", query = "SELECT t from Team t JOIN t.playersInTeam tp WHERE tp = :player")
})
@XmlRootElement
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "teamsOfPlayer")
    Set<Player> playersInTeam;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(as=Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
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
    public Set<Player> getPlayersInTeam() {
        return playersInTeam;
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
