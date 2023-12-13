/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@Entity
@Table(name="users",schema="esportsdb")
@NamedQuery(name="findPlayerByLogin", query="SELECT p FROM User u WHERE user_type=1 ORDER BY p.name DESC")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("1")
public class Player implements Serializable {
    @Id
    private Long id;
    
    private Integer level;

    public Long getId() {
        return id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.level);
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
        final Player other = (Player) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.level, other.level)) {
            return false;
        }
        return true;
    }
    
}
