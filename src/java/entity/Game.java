/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andoni Sanz
 */
@Entity
@Table(name = "game", schema = "esportsdb")
@NamedQueries({
    @NamedQuery(name = "findAllGames",
            query = "SELECT g FROM Game g")
    ,
    @NamedQuery(name = "findGamesByName",
            query = "SELECT g FROM Game g WHERE g.name = :name")
    ,
    @NamedQuery(name = "findGamesByGenre",
            query = "SELECT g FROM Game g WHERE g.genre = :genre")
    ,
    @NamedQuery(name = "findGamesByPlatform",
            query = "SELECT g FROM Game g WHERE g.platform = :platform")
    ,
    @NamedQuery(name = "findGamesByPVPType",
            query = "SELECT g FROM Game g WHERE g.PVPType = :pvpType")
    ,
    @NamedQuery(name = "findGamesByReleaseDate",
            query = "SELECT g FROM Game g WHERE g.releaseDate = :releaseDate")
    ,
    @NamedQuery(name = "findAllGamesCreatedByAdmin",
            query = "SELECT g FROM Game g WHERE g.admin.id IN (SELECT a.id FROM Admin a WHERE a.username = :adminUsername)")
    ,
    @NamedQuery(name = "findGamesByGenreAndReleaseDate",
            query = "SELECT g FROM Game g WHERE g.genre = :genre AND g.releaseDate > (SELECT AVG(g2.releaseDate) FROM Game g2 WHERE g2.genre = :genre)")
})
@XmlRootElement
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Admin admin;

    @OneToMany(mappedBy = "game")
    private Set<Event> events;

    private String name;
    private String genre;
    private String platform;

    @Enumerated(EnumType.STRING)
    private PVPType PVPType;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public PVPType getPVPType() {
        return PVPType;
    }

    public void setPVPType(PVPType PVPType) {
        this.PVPType = PVPType;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the admin
     */
    public Admin getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * @return the events
     */
    @XmlTransient
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final Game other = (Game) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
