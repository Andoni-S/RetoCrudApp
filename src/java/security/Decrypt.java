package security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;

import javax.crypto.Cipher;
import java.util.Base64;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class Decrypt {

    public String decrypt(String encryptedPassword) {
        String decryptedPassword = null;
        try {
            // Agrega el proveedor Bouncy Castle
            Security.addProvider(new BouncyCastleProvider());

            // Recupera la clave privada
            byte[] privateKeyBytes;
            try (FileInputStream fis = new FileInputStream(
                    Paths.get(System.getProperty("user.home"),
                            "\\esportshub\\security", "privateKey.der")
                            .toString()
            )) {
                privateKeyBytes = new byte[fis.available()];
                fis.read(privateKeyBytes);
            }

            KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC"); // "BC" indica el uso del proveedor Bouncy Castle
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Configura el algoritmo ECIES
            Cipher cipher = Cipher.getInstance("ECIES", "BC");

            // Configura los parámetros de la curva elíptica
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ecSpec);

            // Decodifica la contraseña encriptada de Base64
            byte[] encryptedPasswordBytes = Base64.getDecoder().decode(encryptedPassword);

            byte[] decryptedBytes = cipher.doFinal(encryptedPasswordBytes);

            // Convierte los bytes desencriptados a String
            decryptedPassword = new String(decryptedBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedPassword;
    }
}
