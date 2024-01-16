/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name="player_team", schema="esportsdb")
@NamedQueries({
    @NamedQuery(name = "joinTeam", query = "INSERT into PlayerTeamId (playerId, teamId) VALUES (:playerId, :teamId)")
})
@XmlRootElement
public class PlayerTeam implements Serializable {
    @EmbeddedId
    private PlayerTeamId id;
    @MapsId("playerId")
    @ManyToOne
    private Player player;
    @MapsId("teamId")
    @ManyToOne
    private Team team;

    public PlayerTeamId getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public void setId(PlayerTeamId id) {
        this.id = id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
    
}
