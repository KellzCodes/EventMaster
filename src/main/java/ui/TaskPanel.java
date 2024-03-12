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
                // Update task logic
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete task logic
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
