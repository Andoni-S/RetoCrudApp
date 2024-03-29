package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a method to hash a password using SHA-256 algorithm. If hashing
 * fails, logs error using standard Java logger.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class Hash {

    private static final Logger LOGGER = Logger.getLogger(Hash.class.getName());

    /**
     * Hashes the given password using the SHA-256 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password in hexadecimal format.
     */
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE,
                    "Error occurred while hashing password.", e);
            return null;
        }
    }

}
