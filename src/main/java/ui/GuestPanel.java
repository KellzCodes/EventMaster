package ui;

import dao.EventDAO;
import dao.GuestDAO;
import dao.GuestRsvpStatusDAO;
import dao.UserDAO;
import model.GuestRsvpStatus;
import model.Event;

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
            UserDAO userDAO = new UserDAO();
            EventDAO eventDAO = new EventDAO();
            GuestDAO guestDAO = new GuestDAO();
            GuestRsvpStatusDAO guestRsvpStatusDAO = new GuestRsvpStatusDAO();

            // Initialize JTextField for user input
            JTextField userTextField = new JTextField(20); // Adjust size as needed

            // Fetch the list of events
            List<Event> events = eventDAO.getAllEvents();
            JComboBox<Event> eventComboBox = new JComboBox<>(events.toArray(new Event[0]));

            // RSVP Status selection
            JComboBox<GuestRsvpStatus.RsvpStatus> rsvpStatusComboBox = new JComboBox<>(GuestRsvpStatus.RsvpStatus.values());

            // Create panel to hold the components
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Enter User ID:"));
            panel.add(userTextField);
            panel.add(new JLabel("Select Event:"));
            panel.add(eventComboBox);
            panel.add(new JLabel("RSVP Status:"));
            panel.add(rsvpStatusComboBox);

            // Show dialog to get user input
            int result = JOptionPane.showConfirmDialog(null, panel, "Add Guest with RSVP", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String userIdInput = userTextField.getText();
                Event selectedEvent = (Event) eventComboBox.getSelectedItem();
                GuestRsvpStatus.RsvpStatus selectedRsvpStatus = (GuestRsvpStatus.RsvpStatus) rsvpStatusComboBox.getSelectedItem();

                // Check for valid user input before proceeding
                if (!userIdInput.trim().isEmpty()) {

                    boolean successGuest = guestDAO.addGuest(Integer.parseInt(userIdInput));
                    if (successGuest) {
                        boolean successRsvp = guestRsvpStatusDAO.addRsvpStatus(Integer.parseInt(userIdInput), selectedEvent.getEventId(), selectedRsvpStatus);
                        if (successRsvp) {
                            JOptionPane.showMessageDialog(null, "Guest and RSVP status added successfully.");
                            refreshGuestsTable(); // Refresh the guest list
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add RSVP status.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add guest.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User ID cannot be empty.");
                }
            }

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
