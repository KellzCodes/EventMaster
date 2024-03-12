package ui;

import dao.OrganizerDAO;
import dao.UserDAO;
import model.Organizer;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrganizerPanel extends JPanel {
    private JTable organizersTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public OrganizerPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table for organizers
        organizersTable = new JTable();
        organizersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(organizersTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Organizer");
        updateButton = new JButton("Update Organizer");
        deleteButton = new JButton("Delete Organizer");

        refreshOrganizersTable();

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDAO userDAO = new UserDAO();
                List<User> eligibleUsers = userDAO.getEligibleUsersForOrganizer();

                JComboBox<User> usersComboBox = new JComboBox<>();
                for (User user : eligibleUsers) {
                    usersComboBox.addItem(user);
                }

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Select User:"));
                panel.add(usersComboBox);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Organizer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    User selectedUser = (User) usersComboBox.getSelectedItem();
                    OrganizerDAO organizerDAO = new OrganizerDAO();
                    boolean success = organizerDAO.addOrganizer(selectedUser.getUserID());
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Organizer added successfully.");
                        refreshOrganizersTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add organizer.");
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update organizer logic
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete organizer logic
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshOrganizersTable() {
        OrganizerDAO organizerDAO = new OrganizerDAO();
        List<Organizer> organizers = organizerDAO.getAllOrganizers(); // Fetch updated list of organizers

        // Define column names for the table
        String[] columnNames = {"Organizer ID", "Username", "Email"};

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model with organizer data
        for (Organizer organizer : organizers) {
            Object[] row = new Object[]{
                    organizer.getID(),
                    organizer.getUsername(),
                    organizer.getEmail()
            };
            model.addRow(row);
        }

        // Set the model on the table
        organizersTable.setModel(model);
    }
}
