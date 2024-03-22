package test;

import java.nio.charset.StandardCharsets;
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
    private final String url;
    private final String DB_username;
    private final String DB_password;

    public AuthenticationService(String cipherAlgorithm, String encryptionKey, String url, String DB_username, String DB_password) {
        this.cipherAlgorithm = cipherAlgorithm;
        byte[] keyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
        this.url = url;
        this.DB_username = DB_username;
        this.DB_password = DB_password;
        loadUserData();
    }

    private void loadUserData() {
        try (Connection con = DriverManager.getConnection(url, DB_username, DB_password)) {
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

        if (password == null) {
            return false;
        }

        // Check if the password meets the minimum length requirement
        if (password.length() < 8) {
            return false;
        }

        // Check if the password contains at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Check if the password contains at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Check if the password contains at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Check if the password contains at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }

        // Password meets all the requirements
        return true;
    }

    private String decrypt(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (InvalidKeyException e) {
            // Print out the detailed error message and stack trace
            e.printStackTrace();
            throw new RuntimeException("Failed to decrypt password due to invalid key", e);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
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
