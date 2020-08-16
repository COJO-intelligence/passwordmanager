package main.java.gui.login;

import main.java.MainUI;
import main.java.gui.storage.StorageUI;
import main.java.login.Login;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

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
                MainUI.treatError(exception, (JFrame) SwingUtilities.getWindowAncestor(mainPanel), 4);
            }
        });
        resetButton.setVisible(false);
    }

    private void changeContent() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException, ClassNotFoundException {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        StorageUI storageGUI = new StorageUI();
        frame.setContentPane(storageGUI.getMainPanel());
        frame.revalidate();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                storageGUI.writeListPanel();
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
