package model;

/**
 * Represents a guest, inheriting from the User class.
 * This allows for extending User functionality specifically for guests.
 */
public class Guest extends User {
    // Guest-specific properties can be added here.

    // Constructors
    public Guest() {
        super(); // Calls the default constructor of the User class.
    }

    public Guest(int userID, String username, String email) {
        super(userID, username, email); // Calls the constructor of the User class that initializes User properties.
    }

    // Override toString() method for better readability when printing Guest objects, if necessary.
    @Override
    public String toString() {
        return "Guest{" + super.toString() + "}";
    }
}