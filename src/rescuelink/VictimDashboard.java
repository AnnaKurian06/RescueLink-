package rescuelink;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VictimDashboard extends JFrame {
    private final Victim victim;
    private final VictimModule vm;

    public VictimDashboard(Victim victim, VictimModule vm) {
        this.victim = victim;
        this.vm = vm;

        setTitle("Victim Dashboard - " + victim.getName());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title / Welcome panel
        JLabel welcomeLabel = new JLabel("Welcome, " + victim.getName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        // Center buttons panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // 1️⃣ View Alerts Button
        JButton viewAlertsBtn = new JButton("📢 View My Alerts");
        viewAlertsBtn.addActionListener(e -> new VictimAlerts(victim).setVisible(true));

        // 2️⃣ Report Incident / Registration Form Button
        JButton reportIncidentBtn = new JButton("📝 Report New Incident");
        reportIncidentBtn.addActionListener(e -> {
            try {
                VictimGUI reportForm = new VictimGUI(victim, vm); // Pass current victim & module
                reportForm.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening incident form.");
            }
        });

        // 3️⃣ Logout Button
        JButton logoutBtn = new JButton("🚪 Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        centerPanel.add(viewAlertsBtn);
        centerPanel.add(reportIncidentBtn);
        centerPanel.add(logoutBtn);

        add(centerPanel, BorderLayout.CENTER);
    }
}
