package ejb;

import entity.Event;
import javax.ejb.Local;

/**
 * Manages operations related to events within the application storage.
 * Operations include adding, updating, and deleting events.
 * 
 * @author Ander Goirigolzarri Iturburu
 */
@Local
public interface EventEJBLocal {
    
    /**
     * Adds a new event to the application storage.
     *
     * @param event The {@link Event} object representing the event to be
     * created.
     * @throws Exception If an issue occurs while storing the event.
     */
    public void createEvent(Event event) throws Exception;

    /**
     * Modifies an existing event's data in the application storage.
     *
     * @param event The {@link Event} object containing updated event data.
     * @throws Exception If an issue occurs during the update process.
     */
    public void updateEvent(Event event) throws Exception;

    /**
     * Removes an event from the application storage.
     *
     * @param event The {@link Event} object representing the event to be
     * deleted.
     * @throws Exception If an issue occurs while deleting the event.
     */
    public void deleteEvent(Event event) throws Exception;
}
