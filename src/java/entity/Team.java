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
    import javax.persistence.Id;
    import javax.persistence.Inheritance;
    import javax.persistence.InheritanceType;
    import javax.persistence.ManyToMany;
    import javax.persistence.NamedQuery;
    import javax.persistence.Table;
    import javax.xml.bind.annotation.XmlTransient;

    /**
     *
     * @author Jagoba Bartolomé Barroso
     */
    @Entity
    @Table(name="teams",schema="esportsdb")
    public class Team implements Serializable {
        @Id
        private Long id;
        @XmlTransient
        @ManyToMany(mappedBy = "PlayerTeam")
        Set<Player> playersInTeam;  

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
            int hash = 0;
            hash += (this.getId() != null ? this.getId().hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
              // Check if the object is the same reference as this (the same object)
            if (this == obj) {
                return true;
            }
            // Check if the passed object is null or not an instance of Team
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            // Convert the passed object to Team to compare the IDs
            Team team = (Team) obj;
            // Compare the IDs using Objects.equals to handle null values
            return Objects.equals(this.getId(), this.getId());
        }

    }
