package security;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The KeyGenerator class generates ECDH public and private keys and stores them
 * in files. The generated keys are stored in the "security" directory relative
 * to the application's working directory. The elliptic curve used is
 * "secp256r1".
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class KeyGenerator {

    private static final Logger LOGGER = Logger.getLogger(KeyGenerator.class.getName());

    /**
     * The main method that launches the key generation and storage program.
     *
     * @param args The command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        try {
            // Generate KeyPair using ECDH algorithm
            KeyPair keyPair = generateKeyPair();

            // Store public key in a file
            storeKey(keyPair.getPublic(), "publicKey.der");

            // Store private key in a file
            storeKey(keyPair.getPrivate(), "privateKey.der");

            LOGGER.log(Level.INFO, "Public and private keys generated and stored successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating or storing keys", e);
        }
    }

    /**
     * Generates a KeyPair using the ECDH algorithm with the "secp256r1"
     * elliptic curve.
     *
     * @return The generated KeyPair.
     * @throws NoSuchAlgorithmException if the specified algorithm is not
     * available.
     * @throws InvalidAlgorithmParameterException if the specified algorithm
     * parameters are invalid.
     */
    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256r1");
        keyPairGenerator.initialize(ecGenParameterSpec);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Stores the provided Key in a file with the given file name in the
     * "security" package.
     *
     * @param key The Key to be stored.
     * @param fileName The name of the file to store the key.
     * @throws Exception if an error occurs during the file operation.
     */
    private static void storeKey(Key key, String fileName) throws Exception {
        Path path = Paths.get(System.getProperty("user.home"), "\\esportshub\\security", fileName);
        Files.createDirectories(path.getParent());
        try (FileOutputStream out = new FileOutputStream(path.toFile())) {
            out.write(key.getEncoded());
        }
    }
}
