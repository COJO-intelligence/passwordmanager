package main.java.gui.login;

import main.java.gui.storage.storageUI;
import main.java.login.Login;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.logging.Level;

public class LoginUI {

    private JPanel mainPanel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel resultLabel;
    private JButton resetButton;

    public LoginUI() {

        loginButton.addActionListener(e -> {
            try {
                Login login = new Login(new String(passwordField.getPassword()));
                if (login.validateUserPassword()) {
                    changeContent();
                } else {
                    resultLabel.setText("INVALID password! Try again");
                    resultLabel.setForeground(Color.red);
                }
            } catch (Exception exception) {
                MainUI.LOGGER.log(Level.SEVERE, exception.getMessage());
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(mainPanel), "Something went wrong... Email pm.log file at gigi@gmail.com", "FATAL ERROR!", JOptionPane.ERROR_MESSAGE);
                System.exit(2);
            }
        });
        resetButton.setVisible(false);
    }

    private void changeContent() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException, ClassNotFoundException {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        storageUI storageGUI = new storageUI();
        frame.setContentPane(storageGUI.getMainPanel());
        frame.revalidate();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                storageGUI.writeListPanel();
                JOptionPane.showMessageDialog(frame, "Passwords saved!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
