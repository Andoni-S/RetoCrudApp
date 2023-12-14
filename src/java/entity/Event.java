
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity JPA class for Event data. This class contains the properties for the
 * events managed in the app.
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Entity
@Table(name = "events", schema = "esportsdb")
public class Event implements Serializable {

    /**
     * Id field for event entity.
     */
    @Id
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
    private String date;
    /**
     * Prize of the event for the winner.
     */
    private Float prize;
    /**
     * Percentage of the prize perceive by the NGO.
     */
    private Float donation;
    /**
     * Number of players participating in the event.
     */
    private Integer playerNum;
    /**
     * {@link Game} of the Event.
     */
    @ManyToOne
    private String game;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the ong
     */
    public String getOng() {
        return ong;
    }

    /**
     * @param ong the ong to set
     */
    public void setOng(String ong) {
        this.ong = ong;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the prize
     */
    public Float getPrize() {
        return prize;
    }

    /**
     * @param prize the prize to set
     */
    public void setPrize(Float prize) {
        this.prize = prize;
    }

    /**
     * @return the donation
     */
    public Float getDonation() {
        return donation;
    }

    /**
     * @param donation the donation to set
     */
    public void setDonation(Float donation) {
        this.donation = donation;
    }

    /**
     * @return the playerNum
     */
    public Integer getPlayerNum() {
        return playerNum;
    }

    /**
     * @param playerNum the playerNum to set
     */
    public void setPlayerNum(Integer playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * Gets the {@link Game} of the Event.
     *
     * @return the game
     */
    public String getGame() {
        return game;
    }

    /**
     * Sets the {@link Game} of the Event.
     *
     * @param game the game to set
     */
    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Event other = (Event) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", name=" + name + ", location=" + location + ", ong=" + ong + ", date=" + date + ", prize=" + prize + ", donation=" + donation + ", playerNum=" + playerNum + '}';
    }

}
