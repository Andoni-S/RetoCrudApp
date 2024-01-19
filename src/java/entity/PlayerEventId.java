package entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

/**
 * An embeddable class representing the composite primary key for PlayerEvent
 * entities. It combines playerId and eventId to uniquely identify a
 * PlayerEvent.
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Embeddable
public class PlayerEventId implements Serializable {

    @Column(name = "player_id")
    private Long playerId;
    
    @Column(name = "event_id")
    private Long eventId;

    /**
     * Default constructor for PlayerEventId.
     */
    public PlayerEventId() {
    }

    /**
     * Parameterized constructor for PlayerEventId.
     *
     * @param playerId The ID of the player.
     * @param eventId The ID of the event.
     */
    public PlayerEventId(Long playerId, Long eventId) {
        this.playerId = playerId;
        this.eventId = eventId;
    }

    /**
     * Get the playerId.
     *
     * @return The playerId.
     */
    public Long getPlayerId() {
        return playerId;
    }

    /**
     * Set the playerId.
     *
     * @param playerId The playerId to set.
     */
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    /**
     * Get the eventId.
     *
     * @return The eventId.
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * Set the eventId.
     *
     * @param eventId The eventId to set.
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * Generate the hashCode for PlayerEventId.
     *
     * @return The generated hashCode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.playerId);
        hash = 97 * hash + Objects.hashCode(this.eventId);
        return hash;
    }

    /**
     * Check equality between two PlayerEventId instances.
     *
     * @param obj The object to compare for equality.
     * @return True if equal, false otherwise.
     */
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
        final PlayerEventId other = (PlayerEventId) obj;
        if (!Objects.equals(this.playerId, other.playerId)) {
            return false;
        }
        if (!Objects.equals(this.eventId, other.eventId)) {
            return false;
        }
        return true;
    }

    /**
     * Get the string representation of PlayerEventId.
     *
     * @return The string representation of PlayerEventId.
     */
    @Override
    public String toString() {
        return "PlayerEventId{" + "playerId=" + playerId + ", eventId=" + eventId + '}';
    }

}
