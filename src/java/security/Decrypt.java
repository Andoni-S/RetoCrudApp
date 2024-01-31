package security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * The Decrypt class provides a method to decrypt an encrypted password using
 * the ECIES algorithm with a private key. The encrypted password is expected to
 * be encoded in Base64 format. The private key is read from a DER file. Bouncy
 * Castle provider is used for cryptographic operations.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class Decrypt {

    private static final Logger LOGGER = Logger.getLogger(Decrypt.class.getName());

    /**
     * Decrypts an encrypted password using the ECIES algorithm with a private
     * key.
     *
     * @param encryptedPassword The encrypted password to decrypt.
     * @return The decrypted password as a String.
     */
    public String decrypt(String encryptedPassword) {
        String decryptedPassword = null;
        try {
            // Add Bouncy Castle provider
            Security.addProvider(new BouncyCastleProvider());

            // Retrieve the private key
            byte[] privateKeyBytes;
            try (FileInputStream fis = new FileInputStream(
                    Paths.get(System.getProperty("user.home"),
                            "\\esportshub\\security", "privateKey.der")
                            .toString()
            )) {
                privateKeyBytes = new byte[fis.available()];
                fis.read(privateKeyBytes);
            }

            // Generate private key from the DER encoded bytes
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC"); // "BC" indica el uso del proveedor Bouncy Castle
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Configure the ECIES algorithm
            Cipher cipher = Cipher.getInstance("ECIES", "BC");

            // Initialize the cipher with the private key for decryption
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decode the encrypted password from Base64
            byte[] encryptedPasswordBytes = Base64.getDecoder().decode(encryptedPassword);

            // Decrypt the password
            byte[] decryptedBytes = cipher.doFinal(encryptedPasswordBytes);

            // Convert the decrypted bytes to String
            decryptedPassword = new String(decryptedBytes);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading private key file", e);
        } catch (InvalidKeySpecException e) {
            LOGGER.log(Level.SEVERE, "Invalid key specification", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Algorithm not found", e);
        } catch (NoSuchProviderException e) {
            LOGGER.log(Level.SEVERE, "Provider not found", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.log(Level.SEVERE, "Padding not found", e);
        } catch (InvalidKeyException e) {
            LOGGER.log(Level.SEVERE, "Invalid key", e);
        } catch (BadPaddingException e) {
            LOGGER.log(Level.SEVERE, "Bad padding", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.log(Level.SEVERE, "Illegal block size", e);
        }
        return decryptedPassword;
    }
}
