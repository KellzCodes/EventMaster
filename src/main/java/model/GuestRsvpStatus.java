package model;

/**
 * Represents a guest along with their RSVP status for an event.
 */
public class GuestRsvpStatus {
    private int userID; // From User table
    private String username; // From User table
    private String email; // From User table
    private int eventID; // From Guest_rsvp table
    private RsvpStatus rsvpStatus; // Enum for the RSVP status

    // Enum to represent RSVP status options
    public enum RsvpStatus {
        PENDING, CONFIRMED, DECLINED
    }

    // Constructors
    public GuestRsvpStatus() {
    }

    public GuestRsvpStatus(int userID, String username, String email, int eventID, RsvpStatus rsvpStatus) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.eventID = eventID;
        this.rsvpStatus = rsvpStatus;
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

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public RsvpStatus getRsvpStatus() {
        return rsvpStatus;
    }

    public void setRsvpStatus(RsvpStatus rsvpStatus) {
        this.rsvpStatus = rsvpStatus;
    }

    // Override toString() method for better readability when printing GuestWithRsvp objects
    @Override
    public String toString() {
        return "GuestRsvpStatus{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", eventID=" + eventID +
                ", rsvpStatus=" + rsvpStatus +
                '}';
    }
}
