package main.java.gui.login;

import main.java.gui.storage.storageUI;
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
        Color resultOriginalFG = resultLabel.getForeground();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login(new String(passwordField.getPassword()));
                    if (login.validateUserPassword())
                    {
                        goToNextFrame();
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
//        resetButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Login login = null;
//                try {
//                    login = new Login();
//                    login.resetPassword();
//                    startSetPassFrame();
//                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
//                    noSuchAlgorithmException.printStackTrace();
//                } catch (PasswordExistsException passwordExistsException) {
//                    passwordExistsException.printStackTrace();
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
//
//            }
//        });
        resetButton.setVisible(false);
    }

    private void goToNextFrame()
    {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        topFrame.dispose();
        JFrame frame = new JFrame("Storage");
        storageUI storageGUI = new storageUI();
        frame.setContentPane(storageGUI.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void startSetPassFrame()
    {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        topFrame.dispose();
        JFrame frame = new JFrame("SetPassUI");
        SetPassUI setPassUI = new SetPassUI();
        frame.setContentPane(setPassUI.getPassPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.getRootPane().setDefaultButton(setPassUI.getLoginButton());
        frame.setResizable(false);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
