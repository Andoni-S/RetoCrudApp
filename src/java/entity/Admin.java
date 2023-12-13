/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author 2dam
 */
@Entity
@DiscriminatorValue("3")
@NamedQueries({
    @NamedQuery(name="findGameCreatedByAdmin",
            query="SELECT g FROM Game g WHERE user.id = :userId"),
    @NamedQuery(name="findAllUsers",
            query="SELECT u FROM User u"
    ),
    @NamedQuery(name="updateGame",
            query="UPDATE Game g SET g.property = :newValue WHERE g.user.id = :userId"),
})
public class Admin extends User {
    
    @OneToMany(mappedBy = "users")
    private List<User> games;
    
    @Enumerated(EnumType.ORDINAL)
    private Enum permissions;
    
}
