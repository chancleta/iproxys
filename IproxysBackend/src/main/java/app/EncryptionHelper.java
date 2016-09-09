package app;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.util.Base64;

/**
 * Created by Luis Pena on 8/20/2016.
 */
public class EncryptionHelper {

    private static final String ALGO = "DESede";
    private static final String password = "QUJSLOIAUJNCHDYQTGDJOAKN";

    public static String encrypt(String plain) {


        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedValue = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);

            SecretKey theKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, theKey);
            byte[] encVal = cipher.doFinal(plain.getBytes());
            encryptedValue = encoder.encodeToString(encVal);

            String keyForJS = encoder.encodeToString(password.getBytes());
            System.out.println(keyForJS);
        } catch (Exception e) {
        } finally {
            return encryptedValue;
        }
    }

    public static String decrypt(String encrypted) {
        Base64.Decoder decoder = Base64.getDecoder();
        String plainValue = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGO);

            SecretKey theKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, theKey);
            byte[] plainVal = cipher.doFinal(decoder.decode(encrypted.getBytes()));
            plainValue = new String(plainVal);
        } catch (Exception e) {} finally {
            return plainValue;
        }
    }
}
