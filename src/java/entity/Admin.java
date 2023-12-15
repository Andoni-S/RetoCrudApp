/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2dam
 */
@Entity
@DiscriminatorValue("3")
@NamedQueries({
    @NamedQuery(name="findGameCreatedByAdmin",
            query="SELECT g FROM Game g WHERE user.id = :userId"),
    @NamedQuery(name = "findAllAdmins",
                query = "SELECT u FROM user u where u.user_type = 3 ORDER BY u.username DESC"),
    @NamedQuery(name = "findAdminByPermissions",
                query = "SELECT u FROM User u WHERE u.permissions = :permissions"),
})
public class Admin extends User {
    
    @OneToMany(mappedBy = "admin")
    private List<User> games;
    
    @Enumerated(EnumType.ORDINAL)
    private Enum permissions;

    @XmlTransient
    public List<User> getGames() {
        return games;
    }

    public void setGames(List<User> games) {
        this.games = games;
    }

    public Enum getPermissions() {
        return permissions;
    }

    public void setPermissions(Enum permissions) {
        this.permissions = permissions;
    }
    
}
