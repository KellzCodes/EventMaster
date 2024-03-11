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
                // Add budget logic
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update budget logic
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete budget logic
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
