package rescuelink;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VolunteerDashboard extends JFrame {
    private Volunteer loggedInVolunteer;
    private VolunteerDAO volunteerDAO = new VolunteerDAO();
    private final AlertDAO alertDAO = new AlertDAO();
    private final AssignmentDAO assignmentDAO = new AssignmentDAO();

    public VolunteerDashboard() {
        setTitle("Volunteer Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel(new GridLayout(4, 2));
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JButton loginBtn = new JButton("Login");

        loginPanel.add(new JLabel("Name:"));
        loginPanel.add(nameField);
        loginPanel.add(new JLabel("Phone No:"));
        loginPanel.add(phoneField);
        loginPanel.add(new JLabel(""));
        loginPanel.add(loginBtn);

        add(loginPanel);

        loginBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            loggedInVolunteer = volunteerDAO.login(name, phone);

            if (loggedInVolunteer != null) {
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Login failed. Check name and phone.");
            }
        });
    }

    private void showDashboard() {
        getContentPane().removeAll();
        setTitle("Welcome, " + loggedInVolunteer.getName());
        setSize(600, 400);

        JTabbedPane tabs = new JTabbedPane();

        // Assigned Victims
        JPanel victimPanel = new JPanel(new BorderLayout());
        JTextArea victimArea = new JTextArea();
        List<Victim> victims = assignmentDAO.getAssignedVictims(loggedInVolunteer.getVolunteerId());
        for (Victim v : victims) {
            victimArea.append(v.getName() + " - " + v.getLocation() + " - " + v.getCondition() + "\n");
        }
        victimPanel.add(new JScrollPane(victimArea));
        tabs.add("Assigned Victims", victimPanel);

        // Alerts
        JPanel alertPanel = new JPanel(new BorderLayout());
        JTextArea alertArea = new JTextArea();
        List<Alert> alerts = alertDAO.getAlertsForUser(loggedInVolunteer);
        for (Alert a : alerts) {
            alertArea.append("[" + a.getCreatedAt() + "] " + a.getMessage() + "\n");
        }
        alertPanel.add(new JScrollPane(alertArea));
        tabs.add("Alerts", alertPanel);

        // Rescue Ops & Badges (Placeholder)
        tabs.add("Rescue Operations", new JLabel("Coming soon..."));
        tabs.add("Badges", new JLabel("Coming soon..."));

        add(tabs);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VolunteerDashboard().setVisible(true));
    }
}
