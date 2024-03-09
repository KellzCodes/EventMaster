import ui.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Master");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Add tabs
            tabbedPane.addTab("Events", new EventPanel());
            tabbedPane.addTab("Budgets", new BudgetPanel());
            tabbedPane.addTab("Expenses", new ExpensePanel());
            tabbedPane.addTab("Tasks", new TaskPanel());
            tabbedPane.addTab("Users", new UserPanel());
            tabbedPane.addTab("Organizers", new OrganizerPanel());
            tabbedPane.addTab("Guests", new GuestPanel());
            tabbedPane.addTab("User Events", new UserEventsPanel());

            frame.add(tabbedPane, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
