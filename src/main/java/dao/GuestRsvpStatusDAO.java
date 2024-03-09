package dao;

import model.GuestRsvpStatus;
import model.GuestRsvpStatus.RsvpStatus;
import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestRsvpStatusDAO {

    public List<GuestRsvpStatus> getAllGuestsRsvpStatus() {
        List<GuestRsvpStatus> guestRsvpStatuses = new ArrayList<>();
        String sql = "SELECT User.UserID, User.Username, User.Email, Guest_rsvp.EventID, Guest_rsvp.RSVPStatus " +
                "FROM User " +
                "JOIN Guest ON User.UserID = Guest.UserID " +
                "LEFT JOIN Guest_rsvp ON Guest.UserID = Guest_rsvp.UserID";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                GuestRsvpStatus guestRsvpStatus = new GuestRsvpStatus();
                guestRsvpStatus.setUserID(rs.getInt("UserID"));
                guestRsvpStatus.setUsername(rs.getString("Username"));
                guestRsvpStatus.setEmail(rs.getString("Email"));
                guestRsvpStatus.setEventID(rs.getInt("EventID"));
                // Convert the string value of RSVPStatus to enum
                String rsvpStatusStr = rs.getString("RSVPStatus");
                if (rsvpStatusStr != null) {
                    guestRsvpStatus.setRsvpStatus(GuestRsvpStatus.RsvpStatus.valueOf(rsvpStatusStr.toUpperCase()));
                }else{
                    guestRsvpStatus.setRsvpStatus(GuestRsvpStatus.RsvpStatus.PENDING);
                }
                guestRsvpStatuses.add(guestRsvpStatus);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return guestRsvpStatuses;
    }

    public boolean updateGuestRsvpStatus(int userId, int eventId, RsvpStatus rsvpStatus) {
        String sql = "UPDATE Guest_rsvp SET RSVPStatus = ? WHERE UserID = ? AND EventID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, rsvpStatus.name());
            pstmt.setInt(2, userId);
            pstmt.setInt(3, eventId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                conn.commit(); // Commit the transaction if the update was successful
                return true;
            } else {
                conn.rollback(); // Rollback if no rows were updated
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on exception
                } catch (SQLException ex) {
                    System.out.println("SQL Exception on rollback: " + ex.getMessage());
                }
            }
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("SQL Exception on prepared statement close: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit to true
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("SQL Exception on connection close: " + e.getMessage());
                }
            }
        }
    }

    public boolean addRsvpStatus(int userId, int eventId, GuestRsvpStatus.RsvpStatus rsvpStatus) {
        String sql = "INSERT INTO Guest_rsvp (UserID, EventID, RSVPStatus) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE RSVPStatus = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId);
            pstmt.setString(3, rsvpStatus.name());
            pstmt.setString(4, rsvpStatus.name()); // For updating RSVPStatus in case the entry already exists

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if the insert/update was successful
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            return false; // Return false if there was an SQL exception
        }
    }

    public GuestRsvpStatus.RsvpStatus getRsvpStatus(int guestUserID, int eventID) {
        GuestRsvpStatus.RsvpStatus rsvpStatus = null; // Default to null, assuming it might be possible there's no RSVP
        String sql = "SELECT RSVPStatus FROM Guest_rsvp WHERE UserID = ? AND EventID = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guestUserID);
            pstmt.setInt(2, eventID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String statusStr = rs.getString("RSVPStatus");
                if (statusStr != null) {
                    rsvpStatus = GuestRsvpStatus.RsvpStatus.valueOf(statusStr.toUpperCase());
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return rsvpStatus;
    }
}
