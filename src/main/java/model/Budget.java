package model;

/**
 * Represents a budget for an event with corresponding details.
 */
public class Budget {
    // Primary key, unique identifier for each budget.
    private int budgetID;

    // Foreign key reference to the Event table.
    private int eventID;

    // Total amount allocated for the event's budget.
    private double totalAmount;

    // Constructors
    public Budget() {
        // Default constructor
    }

    public Budget(int eventID, double totalAmount) {
        this.eventID = eventID;
        this.totalAmount = totalAmount;
    }

    public Budget(int budgetID, int eventID, double totalAmount) {
        this.budgetID = budgetID;
        this.eventID = eventID;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public int getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(int budgetID) {
        this.budgetID = budgetID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Override toString() method for better readability when printing Budget objects.
    @Override
    public String toString() {
        return "Budget{" +
                "budgetID=" + budgetID +
                ", eventID=" + eventID +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

