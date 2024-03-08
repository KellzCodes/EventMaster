package model;

/**
 * Represents the association between Users and Events.
 */
public class UserEvents {
    private int userID; // Reference to the User ID
    private int eventID; // Reference to the Event ID

    // Constructors
    public UserEvents() {
        // Default constructor
    }

    public UserEvents(int userID, int eventID) {
        this.userID = userID;
        this.eventID = eventID;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    // Override toString() method for better readability when printing UserEvents objects
    @Override
    public String toString() {
        return "UserEvents{" +
                "userID=" + userID +
                ", eventID=" + eventID +
                '}';
    }
}


