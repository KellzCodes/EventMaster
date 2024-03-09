package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestPanel extends JPanel {
    private JTable guestTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton updateRsvpButton; // Button to update RSVP status

    public GuestPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table for guests and their RSVP status
        guestTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(guestTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Guest");
        updateButton = new JButton("Update Guest");
        deleteButton = new JButton("Delete Guest");
        updateRsvpButton = new JButton("Update RSVP Status"); // Button to update RSVP status

        // Action listeners for buttons
        addButton.addActionListener(e -> {
            // Add guest logic
        });
        updateButton.addActionListener(e -> {
            // Update guest logic
        });
        deleteButton.addActionListener(e -> {
            // Delete guest logic
        });
        updateRsvpButton.addActionListener(e -> {
            // Logic to update RSVP status
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateRsvpButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
