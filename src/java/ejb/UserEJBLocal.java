package ejb;

import exception.ReadException;
import exception.CreateException;
import exception.UpdateException;
import exception.DeleteException;
import entity.User;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserEJBLocal {
    /**
     * Finds a {@link User} by its login.
     *
     * @param login The login for the user to be found.
     * @return The {@link User} object containing user data.
     * @throws ReadException If an error occurs during the read operation.
     */
    public User findUserByLogin(String login) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     *
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findAllUsers() throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users with the specified username.
     *
     * @param username The username to search for.
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUserByUsername(String username) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users with the specified email.
     *
     * @param email The email to search for.
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUsersByEmail(String email) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users with the specified name.
     *
     * @param name The name to search for.
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUsersByName(String name) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users with the specified birth date.
     *
     * @param birthDate The birth date to search for.
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUsersByBirthDate(Date birthDate) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users born before the specified date.
     *
     * @param birthDate The date to compare.
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUsersByBirthDateBefore(Date birthDate) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users born after the specified date.
     *
     * @param birthDate The date to compare.
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUsersByBirthDateAfter(Date birthDate) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users with the specified name and surname.
     *
     * @param name     The name to search for.
     * @param surnames The surnames to search for.
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUsersByNameAndSurname(String name, String surnames) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for users born after the average birth date.
     *
     * @return A List of {@link User} objects.
     * @throws ReadException If an error occurs during the read operation.
     */
    public List<User> findUsersBornAfterAvgBirthDate() throws ReadException;

    /**
     * Creates a new {@link User}.
     *
     * @param newUser The {@link User} object to be created.
     * @return The created {@link User} object.
     * @throws CreateException If an error occurs during the create operation.
     */
    public User createUser(User newUser) throws CreateException;

    /**
     * Updates an existing {@link User}.
     *
     * @param userToUpdate The {@link User} object to be updated.
     * @return The updated {@link User} object.
     * @throws UpdateException If an error occurs during the update operation.
     */
    public User updateUser(User userToUpdate) throws UpdateException;

    /**
     * Deletes a {@link User} by its ID.
     *
     * @param userToDelete The {@link User} object to be deleted.
     * @throws DeleteException If an error occurs during the delete operation.
     */
    public void deleteUser(User userToDelete) throws DeleteException;
}
