
import entity.User;
import java.util.HashSet;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity JPA class for Organizer data. This class inherits from User class.
 * Organizer own attributtes are company and web.
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Entity
@Table(name = "users", schema = "esportsdb")
@NamedQueries({
    @NamedQuery(name = "findAllEvents", query = "SELECT e from Events e")
})
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
    @XmlTransient
    @OneToMany(mappedBy = "users")
    private HashSet<Event> events;

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
    public HashSet<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of users belonging to this department.
     *
     * @param events A Set of {@link Event}.
     */
    public void setEvents(HashSet<Event> events) {
        this.events = events;
    }

    @Override
    //TO-DO: write this code properly
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    //TO-DO: write this code properly
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
        final Organizer other = (Organizer) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organizer{" + "company=" + company + ", web=" + web + ", events=" + events + '}';
    }

}
