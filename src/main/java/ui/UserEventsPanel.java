package ui;

import config.DatabaseConfig;
import dao.EventDAO;
import dao.UserDAO;
import dao.UserEventsDAO;
import model.User;
import model.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class UserEventsPanel extends JPanel {
    private JTable userEventsTable;
    private JButton addButton;
    private JButton deleteButton;
    private JComboBox<User> userComboBox;
    private JComboBox<Event> eventComboBox;

    public UserEventsPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table and add it to a JScrollPane
        userEventsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userEventsTable);
        add(scrollPane, BorderLayout.CENTER); // Adds the table in the center

        // Create individual panels for each combo box and buttons
        JPanel userComboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userComboBox = new JComboBox<>();
        userComboBoxPanel.add(userComboBox);

        JPanel eventComboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        eventComboBox = new JComboBox<>();
        eventComboBoxPanel.add(eventComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Association");
        deleteButton = new JButton("Remove Association");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        // Create a panel for controls and add it to the bottom
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        controlsPanel.add(userComboBoxPanel);
        controlsPanel.add(eventComboBoxPanel);
        controlsPanel.add(buttonPanel);

        // Add the controls panel to the bottom of the main panel
        add(controlsPanel, BorderLayout.SOUTH);

        refreshUserEventsTable();
        populateComboBoxes();

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            UserEventsDAO userEventsDAO = new UserEventsDAO();
            @Override
            public void actionPerformed(ActionEvent e) {
                User selectedUser = (User) userComboBox.getSelectedItem();
                Event selectedEvent = (Event) eventComboBox.getSelectedItem();
                if (selectedUser == null || selectedEvent == null) {
                    JOptionPane.showMessageDialog(null, "Please select both a user and an event.");
                    return;
                }

                boolean success = userEventsDAO.addUserToEvent(selectedUser.getUserID(), selectedEvent.getEventId());
                if (success) {
                    JOptionPane.showMessageDialog(null, "Association added successfully.");
                    refreshUserEventsTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add association.");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            UserEventsDAO userEventsDAO = new UserEventsDAO();
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userEventsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int userId = (Integer) userEventsTable.getValueAt(selectedRow, 0);
                    int eventId = (Integer) userEventsTable.getValueAt(selectedRow, 2);

                    boolean success = userEventsDAO.removeUserFromEvent(userId, eventId);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Association removed successfully.");
                        refreshUserEventsTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to remove association.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an association to delete.");
                }
            }
        });
    }

    public void refreshUserEventsTable() {
        String[] columnNames = {"UserID", "Username", "EventID", "EventName", "Date"}; // Define column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Initialize model with column names
        userEventsTable.setModel(model); // Set the model to the table

        String query = "SELECT User.UserID, User.Username, Event.EventID, Event.EventName, Event.Date FROM User "
                + "JOIN user_events ON User.UserID = user_events.UserID "
                + "JOIN Event ON user_events.EventID = Event.EventID";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("UserID");
                String username = rs.getString("Username");
                int eventId = rs.getInt("EventID");
                String eventName = rs.getString("EventName");
                Date date = rs.getDate("Date");

                model.addRow(new Object[]{userId, username, eventId, eventName, date}); // Add row to the model
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateComboBoxes() {
        UserDAO userDAO = new UserDAO();
        EventDAO eventDAO = new EventDAO();
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            userComboBox.addItem(user);
        }

        List<Event> events = eventDAO.getAllEvents();
        for (Event event : events) {
            eventComboBox.addItem(event);
        }
    }
}
