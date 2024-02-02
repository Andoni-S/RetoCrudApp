package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents the participation of a Team in an Event with the associated result.
 *
 * The TeamEvent class is an entity class representing the participation
 * of a Team in an Event with the associated result in the esports system.
 * It includes attributes such as Id, team, event, and result.
 *
 * @author Jagoba Bartolomé Barroso
 * @author Ander Goirigolzarri Iturburu
 */
@Entity
@Table(name = "team_event", schema = "esportsdb")
@XmlRootElement
public class TeamEvent implements Serializable {

    @EmbeddedId
    private TeamEventId Id;

    @ManyToOne
    @MapsId("teamId")
    private Team team;

    @ManyToOne
    @MapsId("eventId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    @Enumerated(EnumType.STRING)
    private Result result;

    public TeamEventId getId() {
        return Id;
    }

    public Team getTeam() {
        return team;
    }

    public Event getEvent() {
        return event;
    }

    public Result getResult() {
        return result;
    }

    public void setId(TeamEventId Id) {
        this.Id = Id;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.Id);
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
        if (!Objects.equals(this.Id, other.Id)) {
            return false;
        }
        return true;
    }
}
