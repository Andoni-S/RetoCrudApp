package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * The SecurityService class provides functionality for loading a private key
 * and signing data using ECDSA (Elliptic Curve Digital Signature Algorithm).
 * The private key is loaded during class initialization.
 * 
 * This class assumes that the private key is stored in a file named "privateKey.pem"
 * within the "src/security" directory.
 * 
 * @author Ander Goirigolzarri Iturburu
 */
public class SecurityService {

    // Private key for signing data
    private static PrivateKey privateKey;

    // Static block to load the private key during class initialization
    static {
        try {
            privateKey = loadPrivateKey("privateKey.pem");
        } catch (Exception e) {
            // Wrap the exception and rethrow it as a runtime exception
            throw new RuntimeException("Error loading private key", e);
        }
    }

    /**
     * Loads the private key from a file with the given fileName.
     * The file is expected to be in the "src/security" directory.
     * 
     * @param fileName The name of the file containing the private key.
     * @return The loaded private key.
     * @throws Exception If an error occurs during the file read or key generation.
     */
    private static PrivateKey loadPrivateKey(String fileName) throws Exception {
        Path path = Paths.get(System.getProperty("user.dir"), "src", "security", fileName);
        byte[] keyBytes = Files.readAllBytes(path);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(spec);
    }

    /**
     * Signs the provided data using ECDSA with the loaded private key.
     * 
     * @param data The data to be signed.
     * @return The signature of the data.
     * @throws Exception If an error occurs during the signature process.
     */
    public static byte[] signData(byte[] data) throws Exception {
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }
}
