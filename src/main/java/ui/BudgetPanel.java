package ui;

import dao.BudgetDAO;
import model.Budget;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BudgetPanel extends JPanel {
    private JTable budgetsTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public BudgetPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table for budgets
        budgetsTable = new JTable();
        budgetsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(budgetsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Budget");
        updateButton = new JButton("Update Budget");
        deleteButton = new JButton("Delete Budget");

        refreshBudgetsTable();

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Form fields for budget details
                JTextField eventIDField = new JTextField(20);
                JTextField totalAmountField = new JTextField(20);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Event ID:"));
                panel.add(eventIDField);
                panel.add(new JLabel("Total Amount:"));
                panel.add(totalAmountField);

                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Add New Budget", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int eventID = Integer.parseInt(eventIDField.getText());
                        double totalAmount = Double.parseDouble(totalAmountField.getText());

                        Budget newBudget = new Budget(eventID, totalAmount);

                        BudgetDAO budgetDAO = new BudgetDAO();
                        budgetDAO.insertBudget(newBudget);

                        // Refresh the budgets table to include the new budget
                        refreshBudgetsTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers for event ID and total amount.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error adding the budget: " + ex.getMessage());
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = budgetsTable.getSelectedRow();
                if (selectedRow >= 0) {

                    int budgetId = (Integer) budgetsTable.getModel().getValueAt(selectedRow, 0);

                    BudgetDAO budgetDAO = new BudgetDAO();
                    Budget budgetToUpdate = budgetDAO.getBudgetById(budgetId);
                    if (budgetToUpdate != null) {
                        JTextField eventIDField = new JTextField(String.valueOf(budgetToUpdate.getEventID()), 20);
                        JTextField totalAmountField = new JTextField(String.valueOf(budgetToUpdate.getTotalAmount()), 20);

                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Event ID:"));
                        panel.add(eventIDField);
                        panel.add(new JLabel("Total Amount:"));
                        panel.add(totalAmountField);

                        int result = JOptionPane.showConfirmDialog(null, panel,
                                "Update Budget", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                int eventID = Integer.parseInt(eventIDField.getText());
                                double totalAmount = Double.parseDouble(totalAmountField.getText());

                                budgetToUpdate.setEventID(eventID);
                                budgetToUpdate.setTotalAmount(totalAmount);

                                budgetDAO.updateBudget(budgetToUpdate);

                                // Refresh the budgets table to show the updated budget
                                refreshBudgetsTable();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Please enter valid numbers for event ID and total amount.");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error updating the budget: " + ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Budget not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a budget to update.");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = budgetsTable.getSelectedRow();
                if (selectedRow >= 0) {

                    int budgetId = (Integer) budgetsTable.getModel().getValueAt(selectedRow, 0);

                    // Prompt the user to confirm deletion
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this budget?", "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        BudgetDAO budgetDAO = new BudgetDAO();
                        try {
                            boolean success = budgetDAO.deleteBudget(budgetId);
                            if (success) {
                                JOptionPane.showMessageDialog(null, "Budget deleted successfully.");
                                refreshBudgetsTable(); // Refresh the table to show the update
                            } else {
                                JOptionPane.showMessageDialog(null, "Error deleting budget.");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error deleting budget: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a budget to delete.");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshBudgetsTable() {

        BudgetDAO budgetDAO = new BudgetDAO();
        List<Budget> budgets = budgetDAO.getAllBudgets(); // Fetch updated list of budgets

        // Define column names for the table
        String[] columnNames = {"Budget ID", "Event ID", "Total Amount"};

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model with budget data
        for (Budget budget : budgets) {
            Object[] row = new Object[]{
                    budget.getBudgetID(),
                    budget.getEventID(),
                    budget.getTotalAmount()
            };
            model.addRow(row);
        }

        // Set the model on the table
        budgetsTable.setModel(model);
    }
}
