package ui;

import dao.ExpenseDAO;
import model.Expense;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                // Add expense logic
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update expense logic
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete expense logic
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
