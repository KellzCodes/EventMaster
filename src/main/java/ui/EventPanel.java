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
        eventsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
                JComboBox<Organizer> organizerComboBox = new JComboBox<>();

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

                        // Instantiate Event object
                        model.Event event = new Event(eventName, organizerId, eventDate, eventLocation);

                        // Use EventDAO to save the new event
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
                int selectedRow = eventsTable.getSelectedRow();
                if (selectedRow >= 0) {

                    int eventId = (Integer) eventsTable.getModel().getValueAt(selectedRow, 0);

                    EventDAO eventDAO = new EventDAO();
                    Event eventToUpdate = eventDAO.getEventById(eventId);
                    OrganizerDAO organizerDAO = new OrganizerDAO();
                    List<Organizer> organizers = organizerDAO.getAllOrganizers();

                    if (eventToUpdate != null) {
                        JTextField eventNameField = new JTextField(eventToUpdate.getEventName(), 20);
                        JTextField eventDateField = new JTextField(eventToUpdate.getDate().toString(), 20);
                        JTextField eventLocationField = new JTextField(eventToUpdate.getLocation(), 20);
                        JComboBox<Organizer> organizerComboBox = new JComboBox<>();

                        // Populate the JComboBox with organizers
                        Organizer selectedOrganizer = null;
                        for (Organizer organizer : organizers) {
                            organizerComboBox.addItem(organizer);
                            if (organizer.getID() == eventToUpdate.getOrganizerID()) {
                                selectedOrganizer = organizer;
                            }
                        }
                        organizerComboBox.setSelectedItem(selectedOrganizer);

                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Event Name:"));
                        panel.add(eventNameField);
                        panel.add(new JLabel("Event Date (YYYY-MM-DD):"));
                        panel.add(eventDateField);
                        panel.add(new JLabel("Event Location:"));
                        panel.add(eventLocationField);
                        panel.add(new JLabel("Organizer:"));
                        panel.add(organizerComboBox);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Update Event",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                eventToUpdate.setEventName(eventNameField.getText());
                                eventToUpdate.setDate(Date.valueOf(eventDateField.getText()));
                                eventToUpdate.setLocation(eventLocationField.getText());
                                Organizer updatedOrganizer = (Organizer) organizerComboBox.getSelectedItem();
                                eventToUpdate.setOrganizerId(updatedOrganizer.getID());

                                eventDAO.updateEvent(eventToUpdate);
                                refreshEventsTable();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error updating the event: " + ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No event selected or event not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an event to update.");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = eventsTable.getSelectedRow();
                if (selectedRow >= 0) {

                    int eventId = (Integer) eventsTable.getModel().getValueAt(selectedRow, 0);

                    // Prompt the user to confirm deletion
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this event?", "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            EventDAO eventDAO = new EventDAO();
                            boolean success = eventDAO.deleteEvent(eventId);
                            if (success) {
                                JOptionPane.showMessageDialog(null, "Event deleted successfully.");
                                refreshEventsTable(); // Refresh table to reflect the deletion
                            } else {
                                JOptionPane.showMessageDialog(null, "Error deleting event.");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error deleting event: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an event to delete.");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshEventsTable() {
        EventDAO eventDAO = new EventDAO();
        List<Event> events = eventDAO.getAllEvents(); // Fetch updated list of events

        // Define column names for the table
        String[] columnNames = {"Event ID", "Event Name", "Event Date", "Event Location"};

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