package ui;

import dao.EventDAO;
import dao.OrganizerDAO;
import model.Event;
import model.Organizer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class EventPanel extends JPanel {
    private JTable eventsTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public EventPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table for events
        eventsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Event");
        updateButton = new JButton("Update Event");
        deleteButton = new JButton("Delete Event");

        // show all events in database
        refreshEventsTable();

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField eventNameField = new JTextField(20);
                JTextField eventDateField = new JTextField(20);
                JTextField eventLocationField = new JTextField(20);
                JComboBox<Organizer> organizerComboBox = new JComboBox<>(); // Assuming Organizer is your model class

                // Populate organizerComboBox with Organizer instances
                OrganizerDAO organizerDAO = new OrganizerDAO();
                List<Organizer> organizers = organizerDAO.getAllOrganizers();
                for (Organizer organizer : organizers) {
                    organizerComboBox.addItem(organizer);
                }

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Event Name:"));
                panel.add(eventNameField);
                panel.add(new JLabel("Event Date (YYYY-MM-DD):"));
                panel.add(eventDateField);
                panel.add(new JLabel("Event Location:"));
                panel.add(eventLocationField);
                panel.add(new JLabel("Organizer:"));
                panel.add(organizerComboBox);

                int result = JOptionPane.showConfirmDialog(null, panel, "New Event",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String eventName = eventNameField.getText();
                        Date eventDate = Date.valueOf(eventDateField.getText()); // Converts string to java.sql.Date
                        String eventLocation = eventLocationField.getText();
                        Organizer selectedOrganizer = (Organizer) organizerComboBox.getSelectedItem();
                        int organizerId = selectedOrganizer.getID(); // Assuming Organizer model has getId()

                        // Instantiate your Event object here including the organizerId
                        model.Event event = new Event(eventName, organizerId, eventDate, eventLocation);

                        // Use your EventDAO to save the new event, including organizerId
                        EventDAO eventDAO = new EventDAO();
                        eventDAO.insertEvent(event);

                        // Refresh the events table
                        refreshEventsTable();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error saving the event: " + ex.getMessage());
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update event logic
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete event logic
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshEventsTable() {
        // Assuming eventDAO is your EventDAO implementation instance
        EventDAO eventDAO = new EventDAO();
        List<Event> events = eventDAO.getAllEvents(); // Fetch updated list of events

        // Define column names for the table
        String[] columnNames = {"Event ID", "Event Name", "Event Date", "Event Location"};

        // Create a new table model (you might be using a custom model in your application)
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model with event data
        for (Event event : events) {
            Object[] row = new Object[]{
                    event.getEventId(),
                    event.getEventName(),
                    event.getDate(),
                    event.getLocation()
            };
            model.addRow(row);
        }

        // Set the model on the table
        eventsTable.setModel(model);
    }
}