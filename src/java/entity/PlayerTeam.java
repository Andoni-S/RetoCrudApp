package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents an association entity between Player and Team entities.
 *
 * The PlayerTeam class is an entity class representing the association
 * between a Player and a Team in the esports system.
 * It includes attributes such as id, player, and team.
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name="player_team", schema="esportsdb")
@NamedQueries({
   // @NamedQuery(name = "joinTeam", query = "INSERT into PlayerTeam (id) VALUES (:id)")
})
@XmlRootElement
public class PlayerTeam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Player player;
    
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
    
}
