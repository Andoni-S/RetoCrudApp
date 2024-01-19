/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name = "team_event", schema = "esportsdb")
@XmlRootElement
public class TeamEvent implements Serializable {

    @EmbeddedId
    private TeamEventId Id;
    @MapsId("teamId")
    @ManyToOne
    private Team team;
    @MapsId("eventId")
    @ManyToOne
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
}
