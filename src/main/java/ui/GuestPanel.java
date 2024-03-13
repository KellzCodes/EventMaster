package ui;

import dao.GuestDAO;
import model.GuestRsvpStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuestPanel extends JPanel {
    private JTable guestsTable;
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
        guestsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(guestsTable);
        guestsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Guest");
        updateButton = new JButton("Update Guest");
        deleteButton = new JButton("Delete Guest");
        updateRsvpButton = new JButton("Update RSVP Status"); // Button to update RSVP status

        refreshGuestsTable();

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

    private void refreshGuestsTable() {
        GuestDAO guestDAO = new GuestDAO();
        List<GuestRsvpStatus> guestsWithRsvp = guestDAO.getAllGuestRsvpStatus(); // Fetch combined guest and RSVP data

        // Define column names for the table
        String[] columnNames = {"User ID", "Username", "Email", "Event ID", "RSVP Status"};

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model
        for (GuestRsvpStatus guest : guestsWithRsvp) {
            Object[] row = new Object[]{
                    guest.getUserID(),
                    guest.getUsername(),
                    guest.getEmail(),
                    guest.getEventID(),
                    guest.getRsvpStatus()
            };
            model.addRow(row);
        }

        // Set the model on the table
        guestsTable.setModel(model);
    }
}
