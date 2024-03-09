package dao;

import model.Task;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public Task insertTask(Task task) {
        String sql = "INSERT INTO Task (EventID, Description, Status, Coordinator) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, task.getEventID());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getStatus().name());
            pstmt.setInt(4, task.getCoordinator());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        task.setTaskID(generatedKeys.getInt(1));
                        return task;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Task getTaskById(int taskId) {
        String sql = "SELECT * FROM Task WHERE TaskID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Task task = new Task();
                task.setTaskID(rs.getInt("TaskID"));
                task.setEventID(rs.getInt("EventID"));
                task.setDescription(rs.getString("Description"));
                task.setStatus(Task.Status.valueOf(rs.getString("Status")));
                task.setCoordinator(rs.getInt("Coordinator"));
                return task;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task();
                task.setTaskID(rs.getInt("TaskID"));
                task.setEventID(rs.getInt("EventID"));
                task.setDescription(rs.getString("Description"));
                task.setStatus(Task.Status.valueOf(rs.getString("Status")));
                task.setCoordinator(rs.getInt("Coordinator"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public boolean updateTask(Task task) {
        String sql = "UPDATE Task SET EventID = ?, Description = ?, Status = ?, Coordinator = ? WHERE TaskID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, task.getEventID());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getStatus().name());
            pstmt.setInt(4, task.getCoordinator());
            pstmt.setInt(5, task.getTaskID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM Task WHERE TaskID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
