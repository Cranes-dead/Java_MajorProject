import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWindow extends JFrame {

    public LoginWindow() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN); // Green background
        loginButton.setForeground(Color.WHITE); // White text
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordField.getPassword());

                // Simulate login validation
                try (Connection connection = DatabaseManager.getConnection()) {
                    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, password); // Note: Storing passwords in plain text is not secure

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(LoginWindow.this, "Login successful!");
                        // Here you can open the main application window
                        new RailwayReservationSystem().setVisible(true);
                        dispose(); // Close the login window
                    } else {
                        JOptionPane.showMessageDialog(LoginWindow.this, "Invalid username or password.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginWindow.this, "Database error.");
                }
            }
        });

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
}
