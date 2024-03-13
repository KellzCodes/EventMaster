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

        // Add action listeners
        addButton.addActionListener(e -> {
            // Logic to add user-event association
        });
        deleteButton.addActionListener(e -> {
            // Logic to remove selected association
        });
    }
}
