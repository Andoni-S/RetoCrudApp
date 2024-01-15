package entity;

import java.util.Objects;
import java.util.Set;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity JPA class for Organizer data. This class inherits from User class.
 * Organizer own attributtes are company and web.
 *
 * @author Ander Goirigolzarri Iturburu
 */

//@Table(name = "organizer", schema = "esportsdb")
@Entity
@DiscriminatorValue("Organizer")
@XmlRootElement
public class Organizer extends User {

    /**
     * Name of the company to which the organizer belongs.
     */
    private String company;
    /**
     * Web address for the organizer's web.
     */
    private String web;
    /**
     * Set of {@link Event} belonging to the organizer.
     */
    @OneToMany(mappedBy = "organizer")
    private Set<Event> events;

    /**
     * Gets company value for Organizer.
     *
     * @return The company value.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets company value for Organizer.
     *
     * @param company The company value.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Gets web value for Organizer.
     *
     * @return The web value.
     */
    public String getWeb() {
        return web;
    }

    /**
     * Sets web value for Organizer.
     *
     * @param web The web value.
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     * Gets the set of the events belonging to this organizer.
     *
     * @return A Set of {@link Event}.
     */
    @XmlTransient
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of users belonging to this department.
     *
     * @param events A Set of {@link Event}.
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * HashCode method implementation of the entity.
     *
     * @return An integer value as hashcode for the object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    /**
     * Indicates whether some other Organizer is "equal to" this one. This
     * method implements an equivalence relation on non-null object references:
     * <ul>
     * <li><strong>It is reflexive:</strong> for any non-null reference value x,
     * x.equals(x) should return true.</li>
     * <li><strong>It is symmetric:</strong> for any non-null reference values x
     * and y, x.equals(y) should return true if and only if y.equals(x) returns
     * true.</li>
     * <li><strong>It is transitive:</strong> for any non-null reference values
     * x, y, and z, if x.equals(y) returns true and y.equals(z) returns true,
     * then x.equals(z) should return true.</li>
     * </ul>
     *
     * @param object The object to compare with this Organizer.
     * @return true if the given object represents an Organizer equivalent to
     * this organizer, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // Check if the object is the same reference as this (the same object)
        if (this == object) {
            return true;
        }
        // Check if the passed object is null or not an instance of Organizer
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        // Call the equals method of the base class (User) to check equality in the class hierarchy
        if (!super.equals(object)) {
            return false;
        }
        // Convert the passed object to Organizer to compare the IDs
        Organizer organizer = (Organizer) object;
        // Compare the IDs using Objects.equals to handle null values
        return Objects.equals(super.getId(), organizer.getId());
    }

    @Override
    public String toString() {
        return "javafxserverside.entity.Organizer[ username=" + super.getUsername() + " ]";
    }

}
