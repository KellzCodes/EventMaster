package dao;

import model.Event;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public Event insertEvent(Event event) {
        String sql = "INSERT INTO Event (EventName, OrganizerID, Date, Location) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, event.getEventName());
            pstmt.setInt(2, event.getOrganizerID());
            pstmt.setDate(3, event.getDate());
            pstmt.setString(4, event.getLocation());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setEventId(generatedKeys.getInt(1));
                        return event;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Event getEventById(int eventId) {
        String sql = "SELECT * FROM Event WHERE EventID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Event(
                        rs.getInt("EventID"),
                        rs.getString("EventName"),
                        rs.getInt("OrganizerID"),
                        rs.getDate("Date"),
                        rs.getString("Location")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Event";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("EventID"),
                        rs.getString("EventName"),
                        rs.getInt("OrganizerID"),
                        rs.getDate("Date"),
                        rs.getString("Location")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    public boolean updateEvent(Event event) {
        String sql = "UPDATE Event SET EventName = ?, OrganizerID = ?, Date = ?, Location = ? WHERE EventID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, event.getEventName());
            pstmt.setInt(2, event.getOrganizerID());
            pstmt.setDate(3, event.getDate());
            pstmt.setString(4, event.getLocation());
            pstmt.setInt(5, event.getEventId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM Event WHERE EventID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}