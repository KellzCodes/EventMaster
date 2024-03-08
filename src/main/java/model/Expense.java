package model;

import java.sql.Date;

/**
 * Represents an expense related to a specific budget for an event.
 */
public class Expense {
    // Primary key, unique identifier for each expense.
    private int expenseID;

    // Foreign key reference to the Budget table.
    private int budgetID;

    // Amount spent.
    private double amount;

    // Description of the expense.
    private String description;

    // Date of the expense.
    private java.sql.Date date;

    // Constructors
    public Expense() {
        // Default constructor
    }

    public Expense(int budgetID, double amount, String description, java.sql.Date date) {
        this.budgetID = budgetID;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Expense(int expenseID, int budgetID, double amount, String description, Date date) {
        this.expenseID = expenseID;
        this.budgetID = budgetID;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Getters and setters
    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public int getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(int budgetID) {
        this.budgetID = budgetID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    // Override toString() method for better readability when printing Expense objects.
    @Override
    public String toString() {
        return "Expense{" +
                "expenseID=" + expenseID +
                ", budgetID=" + budgetID +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}


