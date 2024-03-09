package ui;

import model.User;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Initialize the table for user-event associations
        userEventsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userEventsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize controls for adding associations
        JPanel controlPanel = new JPanel();
        userComboBox = new JComboBox<>(); // Populate with users
        eventComboBox = new JComboBox<>(); // Populate with events
        addButton = new JButton("Add Association");
        deleteButton = new JButton("Remove Association");

        // Setup the control panel
        controlPanel.add(new JLabel("User:"));
        controlPanel.add(userComboBox);
        controlPanel.add(new JLabel("Event:"));
        controlPanel.add(eventComboBox);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);

        // Add action listeners
        addButton.addActionListener(e -> {
            // Logic to add user-event association
        });
        deleteButton.addActionListener(e -> {
            // Logic to remove selected association
        });

        add(controlPanel, BorderLayout.NORTH);
    }
}
