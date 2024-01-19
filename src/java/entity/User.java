/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andoni Sanz
 */
@Entity
@Table(name="user",schema="esportsdb")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(name = "findAllUsers",
                query = "SELECT u FROM User u ORDER BY u.username DESC"),
    @NamedQuery(name = "findUserByUsername",
                query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "findUsersByEmail",
                query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "findUsersByName",
                query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "findUsersByBirthDate",
                query = "SELECT u FROM User u WHERE u.birthDate = :birthDate"),
    @NamedQuery(name = "findUsersByBirthDateBefore",
                query = "SELECT u FROM User u WHERE u.birthDate < :birthDate"),
    @NamedQuery(name = "findUsersByBirthDateAfter",
                query = "SELECT u FROM User u WHERE u.birthDate > :birthDate"),
    @NamedQuery(name = "findUsersByNameAndSurname",
                query = "SELECT u FROM User u WHERE u.name = :name AND u.surnames = :surnames"),
    @NamedQuery(name = "findUsersBornAfterAvgBirthDate",
                query = "SELECT u FROM User u WHERE u.birthDate > (SELECT AVG(u2.birthDate) FROM User u2)")
})
@XmlRootElement
public class User implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    
    @Column(unique=true)
    private String email;
    private String name;
    private String surnames;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    private String user_type;
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
}
