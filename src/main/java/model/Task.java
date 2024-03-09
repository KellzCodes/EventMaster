package model;

/**
 * Represents a task associated with an event.
 */
public class Task {
    // Primary key, unique identifier for each task.
    private int taskID;

    // Foreign key reference to the Event table.
    private int eventID;

    // Description of the task.
    private String description;

    // Status of the task, represented as an enum.
    private Status status;

    // Foreign key reference to the User table for the task coordinator.
    private int coordinator;

    // Enum for task status
    public enum Status {
        not_started, in_progress, completed
    }

    // Constructors
    public Task() {
        // Default constructor
    }

    public Task(int eventID, String description, Status status, int coordinator) {
        this.eventID = eventID;
        this.description = description;
        this.status = status;
        this.coordinator = coordinator;
    }

    // Getters and setters
    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(int coordinator) {
        this.coordinator = coordinator;
    }

    // Override toString() method for better readability when printing Task objects.
    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", eventID=" + eventID +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", coordinator=" + coordinator +
                '}';
    }
}