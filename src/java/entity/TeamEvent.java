/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class TeamEvent {
    private Long teamId;
    private Long eventId;
    private Result result;

    public Long getTeam_id() {
        return teamId;
    }

    public Long getEvent_id() {
        return eventId;
    }

    public Result getResult() {
        return result;
    }

    public void setTeam_id(Long team_id) {
        this.teamId = team_id;
    }

    public void setEvent_id(Long event_id) {
        this.eventId = event_id;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.teamId);
        hash = 59 * hash + Objects.hashCode(this.eventId);
        hash = 59 * hash + Objects.hashCode(this.result);
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
        final TeamEvent other = (TeamEvent) obj;
        return true;
    }
    
    
}
