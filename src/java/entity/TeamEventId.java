package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 * Represents the composite identifier for TeamEvent.
 *
 * The TeamEventId class is an embeddable class representing the composite
 * identifier for the TeamEvent entity. It includes attributes such as teamId
 * and eventId.
 *
 * @author Jagoba Bartolomé Barroso
 * @author Ander Goirigolzarri Iturburu
 */
@Embeddable
public class TeamEventId implements Serializable {
    
    private Long teamId;
    private Long eventId;
    
    public TeamEventId(){
    }

    public TeamEventId(Long teamId, Long eventId) {
        this.teamId = teamId;
        this.eventId = eventId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final TeamEventId other = (TeamEventId) obj;
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        if (!Objects.equals(this.eventId, other.eventId)) {
            return false;
        }
        return true;
    }
    
    
}
