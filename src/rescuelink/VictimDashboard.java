package rescuelink;

import javax.swing.*;
import java.awt.*;

public class VictimDashboard extends JFrame {

    private final Victim victim;

    public VictimDashboard(Victim victim) {
        this.victim = victim;

        setTitle("Victim Dashboard - " + victim.getName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Buttons
        JButton viewAlerts = new JButton("View My Alerts");
        JButton viewAssignments = new JButton("View My Assignments");

        JPanel panel = new JPanel();
        panel.add(viewAlerts);
        panel.add(viewAssignments);
        add(panel, BorderLayout.CENTER);

        viewAlerts.addActionListener(e -> {
            VictimAlerts alertsPanel = new VictimAlerts(victim);
            alertsPanel.setVisible(true);
        });

        viewAssignments.addActionListener(e -> {
            VictimAssignments assignmentsPanel = new VictimAssignments(victim);
            assignmentsPanel.setVisible(true);
        });
    }
}
