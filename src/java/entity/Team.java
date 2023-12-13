/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name="teams",schema="esportsdb")
public class Team implements Serializable {
    @Id
    private Long id;
    
    private String name;
    private Date foundation;
    private String coach;
    private Long captainID;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getFoundation() {
        return foundation;
    }

    public String getCoach() {
        return coach;
    }

    public Long getCaptainID() {
        return captainID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoundation(Date foundation) {
        this.foundation = foundation;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public void setCaptainID(Long captainID) {
        this.captainID = captainID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.foundation);
        hash = 37 * hash + Objects.hashCode(this.coach);
        hash = 37 * hash + Objects.hashCode(this.captainID);
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
        final Team other = (Team) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.coach, other.coach)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.foundation, other.foundation)) {
            return false;
        }
        if (!Objects.equals(this.captainID, other.captainID)) {
            return false;
        }
        return true;
    }
    
}
