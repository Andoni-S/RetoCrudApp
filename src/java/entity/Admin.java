/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andoni Sanz
 */
@Entity
@Table(name = "users", schema = "esportsdb")
@DiscriminatorValue("3")
@NamedQueries({
    @NamedQuery(name="findGameCreatedByAdmin",
            query="SELECT g FROM Game g WHERE user.id = :userId"),
})
public class Admin extends User {
    
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
