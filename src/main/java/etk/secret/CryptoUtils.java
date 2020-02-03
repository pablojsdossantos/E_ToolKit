package etk.secret;

import etk.exceptions.UncheckedException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Pablo JS dos Santos
 */
public class CryptoUtils {
    public static String randomAESKey() {
        PasswordGenerator generator = new PasswordGenerator(16, true, true, true, true);
        return generator.generate();
    }

    public static String encryptAES(String value, String secretKey) {
        try {
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new UncheckedException("ESCUEA23", "Cryptography error", e);
        }
    }

    public static String encryptSHA1(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] encrypted = digest.digest(string.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            throw new UncheckedException("ESCUTS39", "Cryptography error", ex);
        }
    }
}
