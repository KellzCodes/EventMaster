package model;

/**
 * Represents a user in the system.
 */
public class User {
    // Primary key, unique identifier for each user.
    private int userID;

    // Username of the user, must be unique.
    private String username;

    // Email of the user, must be unique.
    private String email;

    // Constructors
    public User() {
        // Default constructor
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(int userID, String username, String email) {
        this.userID = userID;
        this.username = username;
        this.email = email;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Override toString() method for better readability when printing User objects.
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}