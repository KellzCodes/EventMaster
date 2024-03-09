package model;

public class Organizer extends User {

    // Constructors
    public Organizer() {
        super(); // Call User's constructor
    }

    public Organizer(int userID, String username, String email) {
        super(userID, username, email); // Call User's constructor with common attributes
    }

    public int getID(){
        return super.getUserID();
    }

    // Override toString() method for better readability when printing Organizer objects
    @Override
    public String toString() {
        return "Organizer{" +
                "userID=" + getUserID() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}