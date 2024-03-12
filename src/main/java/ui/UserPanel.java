package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserPanel extends JPanel {
    private JTable usersTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public UserPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table for users
        usersTable = new JTable();
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add User");
        updateButton = new JButton("Update User");
        deleteButton = new JButton("Delete User");

        refreshUsersTable();

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField usernameField = new JTextField(20);
                JTextField emailField = new JTextField(20);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Username:"));
                panel.add(usernameField);
                panel.add(new JLabel("Email:"));
                panel.add(emailField);

                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Add New User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText();
                    String email = emailField.getText();

                    UserDAO userDAO = new UserDAO();
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setEmail(email);

                    try {
                        User insertedUser = userDAO.insertUser(newUser);
                        if (insertedUser != null) { // A non-null User indicates success
                            JOptionPane.showMessageDialog(null, "User added successfully.");
                            refreshUsersTable(); // Method to refresh the users table display
                        } else {
                            JOptionPane.showMessageDialog(null, "Error adding user.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error adding the user: " + ex.getMessage());
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update user logic
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete user logic
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshUsersTable() {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers(); // Fetch updated list of users

        // Define column names for the table
        String[] columnNames = {"UserID", "Username", "Email"};

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model with user data
        for (User user : users) {
            Object[] row = new Object[]{
                    user.getUserID(),
                    user.getUsername(),
                    user.getEmail()
            };
            model.addRow(row);
        }

        // Set the model on the table
        usersTable.setModel(model);
    }
}
