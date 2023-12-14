/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name="users",schema="esportsdb")
@NamedQueries({
    @NamedQuery(name="findAllPlayers", query="SELECT u FROM User WHERE u.user_type = 1 ORDER BY u.username DESC"), 
    @NamedQuery(name="findTeamByPlayerId", query="SELECT pt.teamId FROM PlayerTeam pt WHERE pt.playerId = :playerId"),
    @NamedQuery(name="findTeamByPlayerName", query="SELECT t FROM Team t JOIN TeamPlayer tp ON t.id = tp.teamId +"
            + " JOIN Player p ON tp.playerId = p.id  WHERE p.name = :playerName"),
    @NamedQuery(name="findPlayerByLevel", query="SELECT u FROM User WHERE u.level = :level"),
    @NamedQuery(name="findTeamsByDate", query="SELECT t FROM Team WHERE t.foundation = :date"),
    @NamedQuery(name="findTeamsByCoach", query="SELECT t FROM Team WHERE t.coach = :coach"),
    @NamedQuery(name="findTeamsByCaptainId", query="SELECT t FROM Team WHERE t.captainID = :captainID"),
    @NamedQuery(name="findTeamsWithWins", query="SELECT t from Team t JOIN TeamEvent te ON t.id = te.teamId WHERE tp.result = :result")
})


@DiscriminatorValue("1")
public class Player extends User implements Serializable {
    @XmlTransient
    @ManyToMany
    @JoinTable(
            name = "PlayerTeam",
            joinColumns = @JoinColumn(name = "playerId"),
            inverseJoinColumns = @JoinColumn(name = "teamId"))
    Set<Team> teamsOfPlayer;

    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
         // Check if the object is the same reference as this (the same object)
        if (this == obj) {
            return true;
        }
        // Check if the passed object is null or not an instance of Player
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Call the equals method of the base class (Player) to check equality in the class hierarchy
        if (!super.equals(obj)) {
            return false;
        }
        // Convert the passed object to Player to compare the IDs
        Player player = (Player) obj;
        // Compare the IDs using Objects.equals to handle null values
        return Objects.equals(super.getId(), player.getId());
    }
    
}
