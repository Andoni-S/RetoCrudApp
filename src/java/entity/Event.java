package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity JPA class for Event data. This class contains the properties for the
 * events managed in the app.
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Entity
@Table(name = "event", schema = "esportsdb")
@NamedQueries({
    @NamedQuery(name = "findEventsByOrganizer", query = "SELECT e FROM Event e WHERE e.organizer = :organizerName")
    ,
    @NamedQuery(name = "findEventsByGame", query = "SELECT e FROM Event e WHERE e.game = :gameName")
    ,
    @NamedQuery(name = "findEventsWonByPlayer", query = "SELECT pe.event FROM PlayerEvent pe\n"
            + "WHERE pe.player = :player AND pe.result = :result")
    ,
    @NamedQuery(name = "findEventsWonByTeam", query = "SELECT te.event FROM TeamEvent te\n"
            + "WHERE te.team = :team AND te.result = :result")
    ,
    @NamedQuery(name = "findEventsByONG", query = "SELECT e FROM Event e WHERE e.ong = :ongName")
})
@XmlRootElement
public class Event implements Serializable {

    /**
     * Id field for event entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Name of the event.
     */
    private String name;
    /**
     * Location of the event.
     */
    private String location;
    /**
     * Entity that is going to perceive the money from the donations.
     */
    private String ong;
    /**
     * Date of the event.
     */
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @JsonSerialize(as=Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date date;
    /**
     * Prize of the event for the winner.
     */
    private Float prize;
    /**
     * Percentage of the prize perceive by the NGO.
     */
    private Float donation;
    /**
     * Number of maximum participants in the event. Participants can be either
     * {@link Player} or {@link Team}.
     */
    private Integer participantNum;

    /**
     * {@link Game} of the Event.
     */
    @ManyToOne
    private Game game;
    /**
     * {@link Organizer} of the Event.
     */
    @ManyToOne
    private Organizer organizer;

    @OneToMany(mappedBy = "event")
    private Set<PlayerEvent> playerevents;

    @OneToMany(mappedBy = "event")
    private Set<TeamEvent> teamevents;


    /**
     * Gets id value of the Event.
     *
     * @return the id value.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id value of the Event.
     *
     * @param id the id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the Event.
     *
     * @return the name value.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Event.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the location of the event.
     *
     * @return the location value.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event.
     *
     * @param location the location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the NGO to which the event is donating.
     *
     * @return the ong value.
     */
    public String getOng() {
        return ong;
    }

    /**
     * Sets the NGO to which the event is donating.
     *
     * @param ong the ong to set.
     */
    public void setOng(String ong) {
        this.ong = ong;
    }

    /**
     * Gets the date of creation for the event.
     *
     * @return the date value.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of creation for the event.
     *
     * @param date the date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the prize pool for the event.
     *
     * @return the prize value.
     */
    public Float getPrize() {
        return prize;
    }

    /**
     * Sets the prize pool for the event.
     *
     * @param prize the prize to set.
     */
    public void setPrize(Float prize) {
        this.prize = prize;
    }

    /**
     * Gets the donation percentage for the event.
     *
     * @return the donation value.
     */
    public Float getDonation() {
        return donation;
    }

    /**
     * Sets the donation percentage for the event.
     *
     * @param donation the donation to set.
     */
    public void setDonation(Float donation) {
        this.donation = donation;
    }

    /**
     * Gets the number of players participating in the event.
     *
     * @return the participantNum value.
     */
    public Integer getParticipantNum() {
        return participantNum;
    }

    /**
     * Sets the number of players participating in the event.
     *
     * @param participantNum the participantNum to set
     */
    public void setParticipantNum(Integer participantNum) {
        this.participantNum = participantNum;
    }

    /**
     * Gets the {@link Game} of the Event.
     *
     * @return the game value.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the {@link Game} of the Event.
     *
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets the {@link Organizer} of the Event.
     *
     * @return the organizer value.
     */
    public Organizer getOrganizer() {
        return organizer;
    }

    /**
     * Sets the {@link Organizer} of the Event.
     *
     * @param organizer the organizer to set.
     */
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }


    /**
     * HashCode method implementation of the entity.
     *
     * @return An integer value as hashcode for the object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Indicates whether some other Event is "equal to" this one. This method
     * implements an equivalence relation on non-null object references:
     * <ul>
     * <li><strong>It is reflexive:</strong> for any non-null reference value x,
     * x.equals(x) should return true.</li>
     * <li><strong>It is symmetric:</strong> for any non-null reference values x
     * and y, x.equals(y) should return true if and only if y.equals(x) returns
     * true.</li>
     * <li><strong>It is transitive:</strong> for any non-null reference values
     * x, y, and z, if x.equals(y) returns true and y.equals(z) returns true,
     * then x.equals(z) should return true.</li>
     * </ul>
     *
     * @param object The object to compare with this Event.
     * @return true if the given object represents an Event equivalent to this
     * organizer, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // Check if the object is the same reference as this (the same object)
        if (this == object) {
            return true;
        }
        // Check if the passed object is null or not an instance of Event
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        // Convert the passed object to Event to compare the IDs
        Event other = (Event) object;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return " javafxserverside.entity.Event[id=" + id + ", name=" + name + ", location=" + location + ", ong=" + ong + ", date=" + date + ", prize=" + prize + ", donation=" + donation + ", playerNum=" + participantNum + "]";
    }

    /**
     * @return the teamevents
     */
    @XmlTransient
    public Set<TeamEvent> getTeamevents() {
        return teamevents;
    }

    /**
     * @param teamevents the teamevents to set
     */
    public void setTeamevents(Set<TeamEvent> teamevents) {
        this.teamevents = teamevents;
    }

    /**
     * @return the playerevents
     */
    @XmlTransient
    public Set<PlayerEvent> getPlayerevents() {
        return playerevents;
    }

    /**
     * @param playerevents the playerevents to set
     */
    public void setPlayerevents(Set<PlayerEvent> playerevents) {
        this.playerevents = playerevents;
    }

}
