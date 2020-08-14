package main.java.gui.login;

import main.java.gui.storage.storageUI;
import main.java.login.Login;
import main.java.login.PasswordExistsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SetPassUI {
    private JPasswordField passwordField;
    private JPanel passPanel;
    private JLabel titleLabel;
    private JLabel enterLabel;
    private JPasswordField passwordFieldConf;
    private JButton loginButton;
    private JLabel resultLabel;

    public SetPassUI() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Arrays.equals(passwordField.getPassword(), passwordFieldConf.getPassword()))
                {
                    try {
                        Login login = new Login(new String(passwordField.getPassword()));
                        if (login.checkPasswordStrength()) {
                            login.setUserPassword();
                            changeContent();
                        }
                        else
                        {
                            resultLabel.setText("Password must contain lower and upper characters, digits and be 6-20 in length");
                            resultLabel.setForeground(Color.red);
                        }
                    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        noSuchAlgorithmException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (PasswordExistsException passwordExistsException) {
                        passwordExistsException.printStackTrace();
                    }
                }
                else
                {
                    resultLabel.setText("Passwords do not match!");
                    resultLabel.setForeground(Color.red);
                }
            }
        });
    }

    private void changeContent()
    {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(passPanel);
        storageUI storageGUI = new storageUI();
        frame.setContentPane(storageGUI.getMainPanel());
        frame.revalidate();
    }

    public JPanel getPassPanel() {
        return passPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
