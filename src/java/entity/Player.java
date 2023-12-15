/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name="findPlayerByLevel", query="SELECT u FROM User WHERE u.level = :level")
})


@DiscriminatorValue("1")
public class Player extends User implements Serializable {
    @ManyToMany
    @JoinTable(
            name = "PlayerTeam",
            joinColumns = @JoinColumn(name = "playerId"),
            inverseJoinColumns = @JoinColumn(name = "teamId"))
    Set<Team> teamsOfPlayer;
    
    @OneToMany(cascade=ALL, mappedBy="player", fetch=EAGER)
    Set<PlayerEvent> playerevent;

    private Integer level;
      
    @Enumerated(EnumType.ORDINAL)
    private Enum PVPType;
    
    public void setTeamsOfPlayer(Set<Team> teamsOfPlayer) {
        this.teamsOfPlayer = teamsOfPlayer;
    }

    public void setPlayerevent(Set<PlayerEvent> playerevent) {
        this.playerevent = playerevent;
    }

    public void setPVPType(Enum PVPType) {
        this.PVPType = PVPType;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }

    @XmlTransient
    public Set<PlayerEvent> getPlayerevent() {
        return playerevent;
    }

    public Set<Team> getTeamsOfPlayer() {
        return teamsOfPlayer;
    }

    public Integer getLevel() {
        return level;
    }

    public Enum getPVPType() {
        return PVPType;
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
