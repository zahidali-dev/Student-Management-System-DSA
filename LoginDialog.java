

import javax.swing.*;
import java.awt.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Runnable onSuccess;

    public LoginDialog(Frame parent, Runnable onSuccess) {
        super(parent, "Login", false);
        this.onSuccess = onSuccess;

        setUndecorated(true);
        setSize(800, 450);
        setLocationRelativeTo(parent);
        setBackground(new Color(0, 0, 0, 0));

        // 🌌 Background Panel
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bg = new ImageIcon(getClass().getResource("/icon/login_bg.png")).getImage();
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(new Color(35, 55, 110));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        add(backgroundPanel);

        
          // ❌ Close Button (resized icon)
ImageIcon icon = new ImageIcon(getClass().getResource("/icon/exit.png"));
Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // resize icon
JButton closeBtn = new JButton(new ImageIcon(scaled));

closeBtn.setContentAreaFilled(false);
closeBtn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
closeBtn.setFocusPainted(false);
closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
closeBtn.setOpaque(false);
closeBtn.addActionListener(e -> dispose());

// Add to top-right corner
JPanel topPanel = new JPanel(new BorderLayout());
topPanel.setOpaque(false);
topPanel.add(closeBtn, BorderLayout.EAST);
backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // 🔐 Login Form
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        backgroundPanel.add(loginPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        loginPanel.add(userLabel, gbc);

        usernameField = new JTextField(16);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(new Color(255, 255, 255, 220));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(16);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(255, 255, 255, 220));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // ✅ Rounded Small Login Button (only this changed)
        JButton loginBtn = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color = getModel().isPressed() ? new Color(30, 80, 210)
                        : getModel().isRollover() ? new Color(80, 130, 255)
                        : new Color(50, 100, 230);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        loginBtn.setPreferredSize(new Dimension(110, 32));
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setOpaque(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> handleLogin());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; // stops stretching
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginBtn, gbc);

        setVisible(true);
    }

    private void handleLogin() {
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            String u = usernameField.getText().trim();
            String p = new String(passwordField.getPassword());
            if (u.equals("admin") && p.equals("2645")) {
                if (onSuccess != null) onSuccess.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        });
    }
}
