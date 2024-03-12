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
                // Add task logic
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
