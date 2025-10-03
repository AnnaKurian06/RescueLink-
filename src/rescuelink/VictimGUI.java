package rescuelink;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VictimGUI extends JFrame {

    private final JTextField nameField;
    private final JTextField locationField;
    private final JTextField conditionField;
    private final JTextField peopleField;
    private final JComboBox<String> incidentCombo;
    private final JComboBox<String> severityCombo;
    private final JCheckBox immediateCheck;
    private final JButton reportButton;
    private final JButton alertsButton;

    private final VictimModule vm;
    private final String phoneNo; // Phone number passed from login
    private Victim lastReportedVictim; // store latest victim for alerts

    // ✅ Constructor takes phone number
    public VictimGUI(String phoneNo, VictimModule vm) {
        this.vm = vm;
        this.phoneNo = phoneNo;

        setTitle("Victim Registration / Report Incident");
        setSize(900, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 6, 10, 10));

        // Labels
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(new JLabel("Location:"));
        inputPanel.add(new JLabel("Condition:"));
        inputPanel.add(new JLabel("Incident Type:"));
        inputPanel.add(new JLabel("Severity:"));
        inputPanel.add(new JLabel("People Affected:"));
        inputPanel.add(new JLabel("Status:"));

        // Input Fields
        nameField = new JTextField();
        locationField = new JTextField();
        conditionField = new JTextField();
        incidentCombo = new JComboBox<>(new String[]{
                "Flood", "Fire", "Accident", "Landslide", "Earthquake",
                "Building Collapse", "Cyclone", "Medical Emergency", "Other"
        });
        severityCombo = new JComboBox<>(new String[]{"Mild", "Moderate", "Severe"});
        peopleField = new JTextField();

        JTextField statusField = new JTextField("Pending");
        statusField.setEditable(false);

        inputPanel.add(nameField);
        inputPanel.add(locationField);
        inputPanel.add(conditionField);
        inputPanel.add(incidentCombo);
        inputPanel.add(severityCombo);
        inputPanel.add(peopleField);
        inputPanel.add(statusField);

        add(inputPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        immediateCheck = new JCheckBox("Immediate Rescue");
        reportButton = new JButton("Register / Report Incident");
        alertsButton = new JButton("View My Alerts");

        bottomPanel.add(immediateCheck);
        bottomPanel.add(reportButton);
        bottomPanel.add(alertsButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        reportButton.addActionListener(e -> reportIncident());
        alertsButton.addActionListener(e -> openAlerts());
    }

    private void reportIncident() {
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String condition = conditionField.getText().trim();
        String incident = (String) incidentCombo.getSelectedItem();
        String severity = (String) severityCombo.getSelectedItem();
        int peopleAffected;

        if (name.isEmpty() || location.isEmpty() || condition.isEmpty() || peopleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            peopleAffected = Integer.parseInt(peopleField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number for People Affected.");
            return;
        }

        boolean immediate = immediateCheck.isSelected();

        // ✅ Use phoneNo from constructor
        Victim v = new Victim(
                0,             // victim_id (auto-increment)
                name,
                phoneNo,       // ✅ assign phone_no here
                location,
                condition,
                incident,
                severity,
                peopleAffected,
                immediate,
                "Pending"      // status
        );

        vm.addVictim(v);
        lastReportedVictim = v;

        JOptionPane.showMessageDialog(this, "Victim registered / incident reported successfully!");
        clearFields();
    }

    private void clearFields() {
        nameField.setText("");
        locationField.setText("");
        conditionField.setText("");
        incidentCombo.setSelectedIndex(0);
        severityCombo.setSelectedIndex(0);
        peopleField.setText("");
        immediateCheck.setSelected(false);
    }

    private void openAlerts() {
        if (lastReportedVictim == null) {
            JOptionPane.showMessageDialog(this, "No victim reported yet!");
            return;
        }
        VictimAlerts alertsPanel = new VictimAlerts(lastReportedVictim);
        alertsPanel.setVisible(true);
    }
}
