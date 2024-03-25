package test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import org.apache.commons.codec.binary.*;

public class Security {

    public static String encrypt(String strToEncrypt, ServletContext servletContext) {
        String encryptedString = null;
        try {
            // Retrieve the encryption key from the DD
            String encryptionKey = servletContext.getInitParameter("ENCRYPTION_KEY");
            String cipherAlgorithm = servletContext.getInitParameter("CIPHER_ALGORITHM");

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            final SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return encryptedString;
    }

    public static String decrypt(String codeDecrypt, ServletContext servletContext) {
        String decryptedString = null;
        try {
            String encryptionKey = servletContext.getInitParameter("ENCRYPTION_KEY");
            String cipherAlgorithm = servletContext.getInitParameter("CIPHER_ALGORITHM");
            
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            final SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedString = new String(cipher.doFinal(Base64.decodeBase64(codeDecrypt)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return decryptedString;
    }
}
