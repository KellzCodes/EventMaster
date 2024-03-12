package ui;

import dao.TaskDAO;
import model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TaskPanel extends JPanel {
    private JTable tasksTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public TaskPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table for tasks
        tasksTable = new JTable();
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tasksTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Task");
        updateButton = new JButton("Update Task");
        deleteButton = new JButton("Delete Task");

        refreshTasksTable();

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField eventIDField = new JTextField(20);
                JTextField descriptionField = new JTextField(20);
                JComboBox<Task.Status> statusComboBox = new JComboBox<>(Task.Status.values());
                JTextField coordinatorField = new JTextField(20);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Event ID:"));
                panel.add(eventIDField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Status:"));
                panel.add(statusComboBox);
                panel.add(new JLabel("Coordinator ID:"));
                panel.add(coordinatorField);

                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Add New Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int eventId = Integer.parseInt(eventIDField.getText());
                        String description = descriptionField.getText();
                        Task.Status status = (Task.Status) statusComboBox.getSelectedItem();
                        int coordinatorId = Integer.parseInt(coordinatorField.getText());

                        Task newTask = new Task();
                        // Set task properties here
                        newTask.setEventID(eventId);
                        newTask.setDescription(description);
                        newTask.setStatus(status);
                        newTask.setCoordinator(coordinatorId);

                        TaskDAO taskDAO = new TaskDAO();
                        Task insertedTask = taskDAO.insertTask(newTask);
                        if (insertedTask != null) { // Assuming success if a non-null Task is returned
                            JOptionPane.showMessageDialog(null, "Task added successfully.");
                            refreshTasksTable(); // Refresh the tasks table display
                        } else {
                            JOptionPane.showMessageDialog(null, "Error adding task.");
                        }
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers for Event ID and Coordinator ID.");
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Error adding the task: " + ex.getMessage());
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tasksTable.getSelectedRow();
                if (selectedRow >= 0) {

                    int taskId = (Integer) tasksTable.getModel().getValueAt(selectedRow, 0);

                    TaskDAO taskDAO = new TaskDAO();
                    Task taskToUpdate = taskDAO.getTaskById(taskId);
                    if (taskToUpdate != null) {
                        JTextField eventIDField = new JTextField(String.valueOf(taskToUpdate.getEventID()), 20);
                        JTextField descriptionField = new JTextField(taskToUpdate.getDescription(), 20);
                        JComboBox<Task.Status> statusComboBox = new JComboBox<>(Task.Status.values());
                        statusComboBox.setSelectedItem(taskToUpdate.getStatus());
                        JTextField coordinatorField = new JTextField(String.valueOf(taskToUpdate.getCoordinator()), 20);

                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Event ID:"));
                        panel.add(eventIDField);
                        panel.add(new JLabel("Description:"));
                        panel.add(descriptionField);
                        panel.add(new JLabel("Status:"));
                        panel.add(statusComboBox);
                        panel.add(new JLabel("Coordinator ID:"));
                        panel.add(coordinatorField);

                        int result = JOptionPane.showConfirmDialog(null, panel,
                                "Update Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                taskToUpdate.setEventID(Integer.parseInt(eventIDField.getText()));
                                taskToUpdate.setDescription(descriptionField.getText());
                                taskToUpdate.setStatus((Task.Status) statusComboBox.getSelectedItem());
                                taskToUpdate.setCoordinator(Integer.parseInt(coordinatorField.getText()));

                                boolean success = taskDAO.updateTask(taskToUpdate);
                                if (success) {
                                    JOptionPane.showMessageDialog(null, "Task updated successfully.");
                                    refreshTasksTable(); // Refresh the tasks table
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error updating task.");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Please enter valid numbers for Event ID and Coordinator ID.");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error updating the task: " + ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Task not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to update.");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tasksTable.getSelectedRow();
                if (selectedRow >= 0) {

                    int taskId = (Integer) tasksTable.getModel().getValueAt(selectedRow, 0);

                    // Prompt for confirmation
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this task?", "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        TaskDAO taskDAO = new TaskDAO();
                        boolean success = taskDAO.deleteTask(taskId);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Task deleted successfully.");
                            refreshTasksTable(); // Refresh your table to reflect the deletion
                        } else {
                            JOptionPane.showMessageDialog(null, "Error deleting task.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to delete.");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshTasksTable() {
        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getAllTasks(); // Fetch the updated list of tasks

        // Define column names for the table
        String[] columnNames = {"Task ID", "Event ID", "Description", "Status", "Coordinator ID"};

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model with task data
        for (Task task : tasks) {
            Object[] row = new Object[]{
                    task.getTaskID(),
                    task.getEventID(),
                    task.getDescription(),
                    task.getStatus().toString(),
                    task.getCoordinator()
            };
            model.addRow(row);
        }

        // Set the model on the table
        tasksTable.setModel(model);
    }
}
