package dao;

import model.UserEvents;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserEventsDAO {

    public boolean addUserToEvent(int userId, int eventId) {
        String sql = "INSERT INTO User_Events (UserID, EventID) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removeUserFromEvent(int userId, int eventId) {
        String sql = "DELETE FROM User_Events WHERE UserID = ? AND EventID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<UserEvents> getUsersForEvent(int eventId) {
        List<UserEvents> userEvents = new ArrayList<>();
        String sql = "SELECT * FROM User_Events WHERE EventID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                userEvents.add(new UserEvents(rs.getInt("UserID"), rs.getInt("EventID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userEvents;
    }

    public List<UserEvents> getEventsForUser(int userId) {
        List<UserEvents> userEvents = new ArrayList<>();
        String sql = "SELECT * FROM User_Events WHERE UserID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                userEvents.add(new UserEvents(rs.getInt("UserID"), rs.getInt("EventID")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userEvents;
    }
}
