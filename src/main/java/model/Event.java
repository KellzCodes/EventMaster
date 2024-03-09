package model;

import java.sql.Date;

/**
 * Represents an event entity corresponding to the Event table in the database.
 */
public class Event {
    private int eventId;
    private String eventName;
    private int organizerId;
    private Date date;
    private String location;

    /**
     * Constructs an Event without parameters.
     */
    public Event() {
    }

    /**
     * Constructs an Event with all the fields.
     *
     * @param eventId     The ID of the event.
     * @param eventName   The name of the event.
     * @param organizerId The ID of the organizer.
     * @param date        The date of the event.
     * @param location    The location of the event.
     */
    public Event(int eventId, String eventName, int organizerId, Date date, String location) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.organizerId = organizerId;
        this.date = date;
        this.location = location;
    }

    public Event(String eventName, int organizerId, Date date, String location) {
        this.eventName = eventName;
        this.organizerId = organizerId;
        this.date = date;
        this.location = location;
    }

    // Getters and setters for each field
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public int getOrganizerID() {
        return organizerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Overriding toString() for easy printing of Event details
    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", organizerId=" + organizerId +
                ", date=" + date +
                ", location='" + location + '\'' +
                '}';
    }



}