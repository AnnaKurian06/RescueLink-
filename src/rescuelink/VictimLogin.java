package rescuelink;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VictimLogin extends JFrame {

    private final JTextField idField;
    private final JButton loginButton;
    private final VictimModule vm;

    public VictimLogin() throws SQLException {
        vm = new VictimModule();

        setTitle("Victim Login");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Enter Victim ID:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(15);
        add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        add(loginButton, gbc);

        loginButton.addActionListener(e -> login());
    }

    private void login() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Victim ID.");
            return;
        }

        int victimId;
        try {
            victimId = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid numeric ID.");
            return;
        }

        Victim victim = vm.getVictimById(victimId);
        if (victim == null) {
            JOptionPane.showMessageDialog(this, "Victim ID not found!");
            return;
        }

        // Open Victim Dashboard
        VictimDashboard dashboard = new VictimDashboard(victim);
        dashboard.setVisible(true);
        this.dispose(); // Close login window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new VictimLogin().setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
