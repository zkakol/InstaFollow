package view;

import model.User;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import static java.lang.Math.round;

public class LoginView extends JFrame {

    private JButton blogin;
    private JPanel loginPanel;
    private JTextField username;
    private JTextField password;
    private JLabel userLabel;
    private JLabel passLabel;

    public LoginView() {
        setTitle("InstaFollow - Login");
        ImageIcon logo = new ImageIcon("resources/main/instagram_logo.png");
        setIconImage(logo.getImage());
        setUIFont(new FontUIResource("Helvetica", Font.BOLD, 12));

        setSize(300, 200);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) round((dimension.getWidth() / 2));
        int y = (int) round((dimension.getHeight() / 2));
        setLocation(x, y);

        blogin = new JButton("Login");
        loginPanel = new JPanel();
        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");
        username = new JTextField(15);
        password = new JPasswordField(15);

        loginPanel.setLayout(null);
        username.setBounds(95, 30, 150, 20);
        password.setBounds(95, 65, 150, 20);
        blogin.setBounds(110, 100, 80, 20);
        userLabel.setBounds(30, 28, 80, 20);
        passLabel.setBounds(30, 63, 80, 20);

        loginPanel.add(blogin);
        loginPanel.add(username);
        loginPanel.add(password);
        loginPanel.add(userLabel);
        loginPanel.add(passLabel);

        getContentPane().add(loginPanel);
        getRootPane().setDefaultButton(blogin);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        blogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blogin.setEnabled(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                attemptLogin(username.getText(), password.getText(), blogin);
                setCursor(Cursor.getDefaultCursor());
            }

        });

    }

    private static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) UIManager.put(key, f);
        }

    }

    private static void attemptLogin(String username, String password, JButton blogin) {
        User newUser = new User(username, password);
        if (newUser.getIsLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Successfully logged into Instagram!", "Success!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Ran into an issue while logging in.\nPlease try again!", "Error", JOptionPane.ERROR_MESSAGE);
            blogin.setEnabled(true);
        }

    }

    private class LoginListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                attemptLogin(username.getText(), password.getText(), blogin);
            }

        }

    }
}
