/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Organizer;
import entity.User;
import java.util.List;
import javax.ejb.Local;

/**
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
    
    public Organizer findOrganizerByUsername() throws Exception;
    
}
