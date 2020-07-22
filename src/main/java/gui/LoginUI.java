package main.java.gui;

import main.java.login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginUI {

    private JPanel mainPanel;
    private JLabel titleLabel;
    private JPasswordField passwordField;
    private JLabel welcomeLabel;
    private JLabel enterLabel;
    private JButton loginButton;
    private JLabel resultLabel;

    public LoginUI() {
        Color resultOriginalFG = resultLabel.getForeground();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                resultLabel.setText("Login succesful. Password is: " + new String(passwordField.getPassword()));

                try {
                    Login login = new Login(new String(passwordField.getPassword()));
                    if (login.validateUserPassword())
                    {
                        resultLabel.setText("Login successful!");
                        resultLabel.setForeground(resultOriginalFG);
                    }
                    else
                    {
                        resultLabel.setText("INVALID password! Try again");
                        resultLabel.setForeground(Color.red);
                    }
                } catch (NoSuchAlgorithmException | IOException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginUI");
        LoginUI loginUI = new LoginUI();
        frame.setContentPane(loginUI.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.getRootPane().setDefaultButton(loginUI.loginButton);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
