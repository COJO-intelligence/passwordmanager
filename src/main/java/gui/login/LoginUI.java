package main.java.gui.login;

import main.java.gui.storage.storageMain;
import main.java.login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

//TODO try catch here
//TODO Finally for login
public class LoginUI {

    private JPanel mainPanel;
    private JLabel titleLabel;
    private JPasswordField passwordField;
    private JLabel welcomeLabel;
    private JLabel enterLabel;
    private JButton loginButton;
    private JLabel resultLabel;
    private JButton resetButton;

    public LoginUI() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login(new String(passwordField.getPassword()));
                    if (login.validateUserPassword())
                    {
                        changeContent();
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
        resetButton.setVisible(false);
    }

    private void changeContent()
    {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        storageMain storageGUI = new storageMain("test.encrypt");
        frame.setContentPane(storageGUI.getMainPanel());
        frame.revalidate();
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
