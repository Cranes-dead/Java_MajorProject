import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupWindow extends JFrame {

    public SignupWindow() {
        setTitle("Signup");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 230, 230)); // Light gray background
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components

        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.DARK_GRAY); // Dark gray text
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JTextField userText = new JTextField(20);
        userText.setBackground(Color.WHITE); // White background
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userText, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.DARK_GRAY); // Dark gray text
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(Color.WHITE); // White background
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        JButton signupButton = new JButton("Signup");
        signupButton.setBackground(Color.GREEN); // Green background
        signupButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        panel.add(signupButton, gbc);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordField.getPassword());

                try (Connection connection = DatabaseManager.getConnection()) {
                    String query = "INSERT INTO users (username, password) VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, password); // Note: Storing passwords in plain text is not secure

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(SignupWindow.this, "Signup successful!");
                        new LoginWindow().setVisible(true);
                        dispose(); // Close the signup window
                    } else {
                        JOptionPane.showMessageDialog(SignupWindow.this, "Signup failed.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SignupWindow.this, "Database error.");
                }
            }
        });

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
}
