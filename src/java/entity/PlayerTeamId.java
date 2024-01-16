/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class PlayerTeamId implements Serializable {
    
    private Long playerId;
    private Long teamId;

    public PlayerTeamId() {
    }

    public PlayerTeamId(Long playerId, Long teamId) {
        this.playerId = playerId;
        this.teamId = teamId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.playerId);
        hash = 97 * hash + Objects.hashCode(this.teamId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlayerTeamId other = (PlayerTeamId) obj;
        if (!Objects.equals(this.playerId, other.playerId)) {
            return false;
        }
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        return true;
    }
    
    
}
