package dao;

import model.Budget;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {

    public Budget insertBudget(Budget budget) {
        String sql = "INSERT INTO Budget (EventID, TotalAmount) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, budget.getEventID());
            pstmt.setDouble(2, budget.getTotalAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        budget.setBudgetID(generatedKeys.getInt(1));
                        return budget;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Budget getBudgetById(int budgetId) {
        String sql = "SELECT * FROM Budget WHERE BudgetID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budgetId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Budget(
                        rs.getInt("BudgetID"),
                        rs.getInt("EventID"),
                        rs.getDouble("TotalAmount")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Budget getBudgetByEventId(int eventId) {
        String sql = "SELECT * FROM Budget WHERE EventID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int budgetId = rs.getInt("BudgetID");
                double totalAmount = rs.getDouble("TotalAmount");
                return new Budget(budgetId, eventId, totalAmount);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching budget by event ID: " + e.getMessage());
            // Consider proper exception handling or logging
        }
        return null; // Return null if no budget found or in case of an error
    }

    public List<Budget> getAllBudgets() {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT * FROM Budget";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                budgets.add(new Budget(
                        rs.getInt("BudgetID"),
                        rs.getInt("EventID"),
                        rs.getDouble("TotalAmount")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return budgets;
    }

    public boolean updateBudget(Budget budget) {
        String sql = "UPDATE Budget SET EventID = ?, TotalAmount = ? WHERE BudgetID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budget.getEventID());
            pstmt.setDouble(2, budget.getTotalAmount());
            pstmt.setInt(3, budget.getBudgetID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteBudget(int budgetId) {
        String sql = "DELETE FROM Budget WHERE BudgetID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budgetId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean doesEventHaveBudget(int eventId) {
        String sql = "SELECT COUNT(*) FROM Budget WHERE EventID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // If count is greater than 0, then a budget exists for this event
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking if event has a budget: " + e.getMessage());
        }
        return false; // Return false if no budget found or in case of an error
    }
}
