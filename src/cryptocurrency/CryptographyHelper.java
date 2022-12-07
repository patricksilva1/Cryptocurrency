package cryptocurrency;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;

public class CryptographyHelper {

    // SHA-256 hash (256 bits = 64 hexadecimal characters)
    public static String generateHash(String data) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(data.getBytes("UTF-8"));

            // We want to end up with hexadecimal values, not bytes.
            StringBuilder hexadecimalString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hexadecimal = Integer.toHexString(0xff & hash[i]);

                // Padding 64 Characters
                if (hexadecimal.length() == 1) {
                    hexadecimalString.append('0');
                }

                hexadecimalString.append(hexadecimal);
            }

            return hexadecimalString.toString();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /*
     * Generate Public Key and Private Key
     * Private Key: 256 bits long random integer
     * Public Key: point on the elliptic curve
     * (x, y) - both of these values are 256 bits long
     */
    public static KeyPair ellipticCurveCrypto() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");

            ECGenParameterSpec params = new ECGenParameterSpec("prime256v1");

            keyPairGenerator.initialize(params);

            return keyPairGenerator.generateKeyPair();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
