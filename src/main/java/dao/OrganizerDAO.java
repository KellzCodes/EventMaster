package dao;

import model.Organizer;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizerDAO {

    public List<Organizer> getAllOrganizers() {
        List<Organizer> organizers = new ArrayList<>();
        String sql = "SELECT * FROM User INNER JOIN Organizer ON User.UserID = Organizer.UserID";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Organizer organizer = new Organizer();
                organizer.setUserID(rs.getInt("UserID"));
                organizer.setUsername(rs.getString("Username"));
                organizer.setEmail(rs.getString("Email"));
                organizers.add(organizer);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return organizers;
    }

    public boolean addOrganizer(int userId) {
        String sql = "INSERT INTO Organizer (UserID) VALUES (?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding organizer: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteOrganizer(int userId) {
        String sql = "DELETE FROM Organizer WHERE UserID = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
