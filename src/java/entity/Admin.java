/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andoni Sanz
 */
@Entity
@Table(name = "admin", schema = "esportsdb")
@XmlRootElement
public class Admin extends User {

    private Date entryDate;
    
    @OneToMany(mappedBy = "admin")
    private Set<Game> games;
    
    @XmlTransient
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
