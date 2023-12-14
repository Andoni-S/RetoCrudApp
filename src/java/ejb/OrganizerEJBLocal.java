package ejb;

import entity.Organizer;
import java.util.List;
import javax.ejb.Local;

/**
 * Manages operations associated with organizers in the application, including
 * retrieval based on different criteria.
 *
 * @author Ander Goirigolzarri Iturburu
 */
@Local
public interface OrganizerEJBLocal {

    /**
     * Finds a List of {@link Organizer} objects containing data for all
     * organizers in the application data storage.
     *
     * @return A List of {@link Organizer} objects.
     * @throws Exception If there is any Exception during processing.
     */
    public List<Organizer> findAllOrganizers() throws Exception;

    /**
     * Finds a {@link Organizer} object matching the username provided.
     *
     * @param username The username of the {@link Organizer} to be found.
     * @return The {@link Organizer} with the provided username.
     * @throws Exception If there is any Exception during processing.
     */
    public Organizer findOrganizerByUsername(String username) throws Exception;

    /**
     * Finds a List of {@link Organizer} objects containing data for all
     * organizers in the application data storage matching the provided company.
     *
     * @param company The company of the list of {@link Organizers} to be found.
     * @return A List of {@link Organizer} objects.
     * @throws Exception
     */
    public List<Organizer> findOrganizersByCompany(String company) throws Exception;
}
