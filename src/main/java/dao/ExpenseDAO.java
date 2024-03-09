package dao;

import model.Expense;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public Expense insertExpense(Expense expense) {
        String sql = "INSERT INTO Expense (BudgetID, Amount, Description, Date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, expense.getBudgetID());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getDescription());
            pstmt.setDate(4, expense.getDate());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        expense.setExpenseID(generatedKeys.getInt(1));
                        return expense;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Expense getExpenseById(int expenseId) {
        String sql = "SELECT * FROM Expense WHERE ExpenseID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, expenseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Expense(
                        rs.getInt("ExpenseID"),
                        rs.getInt("BudgetID"),
                        rs.getDouble("Amount"),
                        rs.getString("Description"),
                        rs.getDate("Date")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM Expense";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("ExpenseID"),
                        rs.getInt("BudgetID"),
                        rs.getDouble("Amount"),
                        rs.getString("Description"),
                        rs.getDate("Date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expenses;
    }

    public boolean updateExpense(Expense expense) {
        String sql = "UPDATE Expense SET BudgetID = ?, Amount = ?, Description = ?, Date = ? WHERE ExpenseID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, expense.getBudgetID());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getDescription());
            pstmt.setDate(4, expense.getDate());
            pstmt.setInt(5, expense.getExpenseID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteExpense(int expenseId) {
        String sql = "DELETE FROM Expense WHERE ExpenseID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, expenseId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
