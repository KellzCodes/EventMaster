package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetPanel extends JPanel {
    private JTable budgetsTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public BudgetPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout()); // Use BorderLayout for panel layout

        // Initialize the table for budgets
        budgetsTable = new JTable(); // Consider using a custom TableModel for budgets
        JScrollPane scrollPane = new JScrollPane(budgetsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons and add them to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Budget");
        updateButton = new JButton("Update Budget");
        deleteButton = new JButton("Delete Budget");

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
}
