package entity;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents a Player entity, extending the User class.
 *
 * The Player class is an entity class representing a player in the system.
 * It includes additional attributes such as teams, playerevents, and level.
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
//@Table(name = "player", schema = "esportsdb")
@NamedQueries({
    @NamedQuery(name = "findPlayerById", query = "SELECT p FROM User p WHERE p.id = :id")
        ,
    @NamedQuery(name = "findMyTeams", query = "SELECT pt.team FROM PlayerTeam pt WHERE pt.player.id = :player_id")
})
@DiscriminatorValue("player")
@XmlRootElement
public class Player extends User {

    @OneToMany(mappedBy="player", fetch = EAGER, cascade = CascadeType.PERSIST)
    private Set<PlayerTeam> teams;

    @OneToMany(cascade = ALL, mappedBy = "player", fetch = EAGER)
    private Set<PlayerEvent> playerevent;

    private Integer level;

    public void setTeams(Set<PlayerTeam> teams) {
        this.teams = teams;
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
    public Set<PlayerTeam> getTeams() {
        return teams;
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