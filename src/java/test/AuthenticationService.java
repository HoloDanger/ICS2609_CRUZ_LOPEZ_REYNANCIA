package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;

public class AuthenticationService {
    private static final List<User> users = new ArrayList<>();
    private final String cipherAlgorithm;
    private final SecretKeySpec secretKey;
    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;

    public AuthenticationService(String cipherAlgorithm, byte[] encryptionKey, String databaseUrl, String databaseUsername, String databasePassword) {
        this.cipherAlgorithm = cipherAlgorithm;
        this.secretKey = new SecretKeySpec(encryptionKey, 0, encryptionKey.length, "AES");
        this.databaseUrl = databaseUrl;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        loadUserData();
    }

    private void loadUserData() {
        try (Connection con = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            String query = "SELECT * FROM USER_INFO ORDER BY username";

            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getString("USERNAME").trim(),
                            rs.getString("PASSWORD").trim(),
                            rs.getString("ROLE").trim()));
                }
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to load user data", sqle);
        }
    }

    public User authenticate(String username, String password) throws IllegalArgumentException, AuthenticationException {
        String decryptedPassword = decrypt(password);

        if (username.isEmpty() && password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(decryptedPassword)) {
                return user;
            }
        }

        if (!isUsernameValid(username)) {
            throw new AuthenticationException("Invalid username");
        } else if (!isPasswordValid(decryptedPassword)) {
            throw new AuthenticationException("Invalid password");
        } else {
            throw new AuthenticationException("Invalid username and password");
        }
    }

    private boolean isUsernameValid(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        // TODO: Implement password validation logic here
        // For example, check if the password meets the minimum requirements
        return password.length() >= 8;
    }

    private String decrypt(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Failed to decrypt password", e);
        }
    }

    public static class AuthenticationException extends Exception {
        public AuthenticationException(String message) {
            super(message);
        }

        public AuthenticationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}