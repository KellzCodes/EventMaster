package ui;

import dao.ExpenseDAO;
import model.Expense;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ExpensePanel extends JPanel {
    private JTable expensesTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public ExpensePanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Initialize the table for expenses
        expensesTable = new JTable();
        expensesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(expensesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Expense");
        updateButton = new JButton("Update Expense");
        deleteButton = new JButton("Delete Expense");

        refreshExpensesTable();

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField budgetIDField = new JTextField(20);
                JTextField amountField = new JTextField(20);
                JTextField descriptionField = new JTextField(20);
                JTextField dateField = new JTextField(20); // Date format: YYYY-MM-DD

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Budget ID:"));
                panel.add(budgetIDField);
                panel.add(new JLabel("Amount:"));
                panel.add(amountField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Date (YYYY-MM-DD):"));
                panel.add(dateField);

                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Add New Expense", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int budgetID = Integer.parseInt(budgetIDField.getText());
                        double amount = Double.parseDouble(amountField.getText());
                        String description = descriptionField.getText();
                        LocalDate date = LocalDate.parse(dateField.getText());

                        Expense newExpense = new Expense(budgetID, amount, description, Date.valueOf(date));

                        ExpenseDAO expenseDAO = new ExpenseDAO();
                        Expense insertedExpense = expenseDAO.insertExpense(newExpense);
                        if (insertedExpense != null) {
                            JOptionPane.showMessageDialog(null, "Expense added successfully.");
                            refreshExpensesTable(); // Method to refresh the expenses table display
                        } else {
                            JOptionPane.showMessageDialog(null, "This Budget does not exist. Please enter valid Budget.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers for Budget ID and Amount.");
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter the date in format YYYY-MM-DD.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error adding the expense: " + ex.getMessage());
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = expensesTable.getSelectedRow();
                if (selectedRow >= 0) {

                    int expenseId = (Integer) expensesTable.getModel().getValueAt(selectedRow, 0);

                    ExpenseDAO expenseDAO = new ExpenseDAO();
                    Expense expenseToUpdate = expenseDAO.getExpenseById(expenseId);
                    if (expenseToUpdate != null) {
                        JTextField budgetIDField = new JTextField(String.valueOf(expenseToUpdate.getBudgetID()), 20);
                        JTextField amountField = new JTextField(String.valueOf(expenseToUpdate.getAmount()), 20);
                        JTextField descriptionField = new JTextField(expenseToUpdate.getDescription(), 20);
                        JTextField dateField = new JTextField(expenseToUpdate.getDate().toString(), 20); // Assuming getDate() returns a java.sql.Date or similar

                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        panel.add(new JLabel("Budget ID:"));
                        panel.add(budgetIDField);
                        panel.add(new JLabel("Amount:"));
                        panel.add(amountField);
                        panel.add(new JLabel("Description:"));
                        panel.add(descriptionField);
                        panel.add(new JLabel("Date (YYYY-MM-DD):"));
                        panel.add(dateField);

                        int result = JOptionPane.showConfirmDialog(null, panel,
                                "Update Expense", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                expenseToUpdate.setBudgetID(Integer.parseInt(budgetIDField.getText()));
                                expenseToUpdate.setAmount(Double.parseDouble(amountField.getText()));
                                expenseToUpdate.setDescription(descriptionField.getText());
                                expenseToUpdate.setDate(Date.valueOf(LocalDate.parse(dateField.getText())));

                                boolean success = expenseDAO.updateExpense(expenseToUpdate);
                                if (success) {
                                    JOptionPane.showMessageDialog(null, "Expense updated successfully.");
                                    refreshExpensesTable(); // Method to refresh the expenses table display
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error updating expense.");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Please enter valid numbers for Budget ID and Amount.");
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(null, "Please enter the date in format YYYY-MM-DD.");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error updating the expense: " + ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Expense not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an expense to update.");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = expensesTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int expenseId = (Integer) expensesTable.getModel().getValueAt(selectedRow, 0);

                    // Prompt for confirmation
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this expense?", "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        ExpenseDAO expenseDAO = new ExpenseDAO();
                        boolean success = expenseDAO.deleteExpense(expenseId);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Expense deleted successfully.");
                            refreshExpensesTable(); // Refresh table to reflect the deletion
                        } else {
                            JOptionPane.showMessageDialog(null, "Error deleting expense.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an expense to delete.");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshExpensesTable() {
        ExpenseDAO expenseDAO = new ExpenseDAO();
        List<Expense> expenses = expenseDAO.getAllExpenses(); // Fetch updated list of expenses

        // Define column names for the table
        String[] columnNames = {"Expense ID", "Budget ID", "Amount", "Description", "Date"};

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model with expense data
        for (Expense expense : expenses) {
            Object[] row = new Object[]{
                    expense.getExpenseID(),
                    expense.getBudgetID(),
                    expense.getAmount(),
                    expense.getDescription(),
                    expense.getDate()

            };
            model.addRow(row);
        }

        // Set the model on the table
        expensesTable.setModel(model);
    }
}
