/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * "INSERT INTO PlayerTeam (SELECT FROM Player p WHERE p.id VALUES (:playerId, :teamId)"
 * @author Jagoba Bartolomé Barroso
 */

@Table(name = "player", schema = "esportsdb")
@NamedQueries({
    @NamedQuery(name = "findPlayer", query = "SELECT p FROM Player p WHERE p.id = :playerId")
})
//@DiscriminatorValue("1")
@Entity
@DiscriminatorValue("Player")
@XmlRootElement
public class Player extends User {

    @OneToMany(cascade = ALL, mappedBy = "player", fetch = EAGER)
    private Set<PlayerTeam> teamsOfPlayer;

    @OneToMany(cascade = ALL, mappedBy = "player", fetch = EAGER)
    private Set<PlayerEvent> playerevent;

    private Integer level;

    public void setTeamsOfPlayer(Set<PlayerTeam> teamsOfPlayer) {
        this.teamsOfPlayer = teamsOfPlayer;
    }

    public void setPlayerevent(Set<PlayerEvent> playerevent) {
        this.playerevent = playerevent;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


    @XmlTransient
    public Set<PlayerEvent> getPlayerevent() {
        return playerevent;
    }
    
    @XmlTransient
    public Set<PlayerTeam> getTeamsOfPlayer() {
        return teamsOfPlayer;
    }

    public Integer getLevel() {
        return level;
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
