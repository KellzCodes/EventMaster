package dao;

import model.Guest;
import model.GuestRsvpStatus;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {

    public List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM User INNER JOIN Guest ON User.UserID = Guest.UserID";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Guest guest = new Guest();
                guest.setUserID(rs.getInt("UserID"));
                guest.setUsername(rs.getString("Username"));
                guest.setEmail(rs.getString("Email"));
                guests.add(guest);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return guests;
    }

    public List<GuestRsvpStatus> getAllGuestRsvpStatus() {
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
                guestRsvpStatus.setEventID(rs.getInt("EventID")); // This might be null if not all guests have RSVPs
                String rsvpStatusStr = rs.getString("RSVPStatus");
                if (rsvpStatusStr != null) {
                    // Convert the string to uppercase before attempting to match it with enum values
                    GuestRsvpStatus.RsvpStatus rsvpStatus = GuestRsvpStatus.RsvpStatus.valueOf(rsvpStatusStr.toUpperCase());
                    guestRsvpStatus.setRsvpStatus(rsvpStatus);
                } else {
                    // Handle the case where RSVPStatus is null, potentially by setting a default value or leaving it null
                    guestRsvpStatus.setRsvpStatus(null);
                }
                //guestRsvpStatus.setRsvpStatus(rs.getString("RSVPStatus"));
                guestRsvpStatuses.add(guestRsvpStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guestRsvpStatuses;
    }

    public boolean addGuest(int userId) {
        String sql = "INSERT INTO Guest (UserID) VALUES (?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            int affectedRows = pstmt.executeUpdate();

            // Return true if one row was affected, indicating the guest was added successfully
            return affectedRows == 1;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        }
    }

    public Guest getGuestById(int guestId) {
        Guest guest = null;
        String sql = "SELECT User.UserID, User.Username, User.Email FROM User JOIN Guest ON User.UserID = Guest.UserID WHERE Guest.UserID = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guestId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                guest = new Guest();
                guest.setUserID(rs.getInt("UserID"));
                guest.setUsername(rs.getString("Username"));
                guest.setEmail(rs.getString("Email"));

            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return guest;
    }

    public boolean deleteGuest(int guestUserID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        try {
            conn = DatabaseConfig.getConnection();
            // Start transaction
            conn.setAutoCommit(false);

            // Optionally, delete associated RSVP records first
            String deleteRsvpSql = "DELETE FROM Guest_rsvp WHERE UserID = ?";
            pstmt = conn.prepareStatement(deleteRsvpSql);
            pstmt.setInt(1, guestUserID);
            pstmt.executeUpdate();

            // Delete guest
            String deleteGuestSql = "DELETE FROM Guest WHERE UserID = ?";
            pstmt = conn.prepareStatement(deleteGuestSql);
            pstmt.setInt(1, guestUserID);
            int affectedRows = pstmt.executeUpdate();

            // Commit transaction
            conn.commit();
            success = affectedRows > 0;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Cleanup
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return success;
    }
}
