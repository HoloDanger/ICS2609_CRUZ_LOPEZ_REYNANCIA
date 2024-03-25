package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class AuthenticationService {

    private static final List<User> users = new ArrayList<>();
    private final String url;
    private final String dbUsername;
    private final String dbPassword;

    public AuthenticationService(String url, String dbUsername, String dbPassword, ServletContext servletContext) {
        this.url = url;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        loadUserData(servletContext);
    }

    public void updatePasswordsToEncrypted(ServletContext servletContext) {
        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM USER_INFO";
            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("USERNAME");
                    String plainTextPassword = rs.getString("PASSWORD");
                    String encryptedPassword = Security.encrypt(plainTextPassword, servletContext);
                    updatePassword(con, username, encryptedPassword);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update passwords", e);
        } catch (NullPointerException e) {
            // Log the exception or perform any necessary error handling
            throw new RuntimeException("NullPointerException occurred while loading user data", e);
        }
    }

    private void updatePassword(Connection con, String username, String encryptedPassword) throws SQLException {
        String updateQuery = "UPDATE USER_INFO SET PASSWORD = ? WHERE USERNAME = ?";
        try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
            ps.setString(1, encryptedPassword);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }

    private void loadUserData(ServletContext servletContext) {
        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM USER_INFO ORDER BY username";

            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String username = null;
                    String encryptedPassword = null;
                    String role = null;

                    // Handle potential null values
                    if (rs.getString("USERNAME") != null) {
                        username = rs.getString("USERNAME").trim();
                    }
                    if (rs.getString("PASSWORD") != null) {
                        encryptedPassword = rs.getString("PASSWORD").trim();
                    }
                    if (rs.getString("ROLE") != null) {
                        role = rs.getString("ROLE").trim();
                    }

                    // Decrypt the password
                    String decryptedPassword = null;
                    if (encryptedPassword != null) {
                        decryptedPassword = Security.decrypt(encryptedPassword, servletContext);
                    }

                    // Create a User object only if username and role are not null
                    if (username != null && role != null) {
                        users.add(new User(username, decryptedPassword, role));
                    } else {
                        // Log or handle the case when username or role is null
                        System.out.println("Skipping user record with null username or role");
                    }
                }
            }
        } catch (SQLException e) {
            // Log the exception or perform any necessary error handling
            throw new RuntimeException("Failed to load user data", e);
        } catch (NullPointerException e) {
            // Log the exception or perform any necessary error handling
            throw new RuntimeException("NullPointerException occurred while loading user data", e);
        }
    }

    public User authenticate(String username, String password) throws NullValueException, AuthenticationException {
        try {
            if (username == null && password == null || username.isEmpty() && password.isEmpty()) {
                throw new NullValueException("Username and password cannot be empty");
            }

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }

            if (!isUsernameValid(username)) {
                throw new AuthenticationException("Invalid username");
            } else if (!isPasswordValid(username, password)) {
                throw new AuthenticationException("Invalid password");
            } else {
                throw new AuthenticationException("Invalid username and password");
            }
        } catch (NullPointerException e) {
            // Log the exception or perform any necessary error handling
            throw new AuthenticationException("An error occurred during authentication", e);
        }
    }

    private boolean isUsernameValid(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    private boolean isPasswordValid(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .anyMatch(user -> user.getPassword().equals(password));
    }

    public static class AuthenticationException extends Exception {

        public AuthenticationException(String message) {
            super(message);
        }

        public AuthenticationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class NullValueException extends Exception {

        public NullValueException(String message) {
            super(message);
        }

        public NullValueException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
