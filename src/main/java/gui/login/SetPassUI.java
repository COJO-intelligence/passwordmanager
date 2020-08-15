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
import java.util.Arrays;
import java.util.logging.Level;

public class SetPassUI {
    private JPasswordField passwordField;
    private JPanel passPanel;
    private JPasswordField passwordFieldConf;
    private JButton loginButton;
    private JLabel resultLabel;

    public SetPassUI() {
        loginButton.addActionListener(e -> {
            if (Arrays.equals(passwordField.getPassword(), passwordFieldConf.getPassword())) {
                try {
                    Login login = new Login(new String(passwordField.getPassword()));
                    if (login.checkPasswordStrength()) {
                        login.setUserPassword();
                        changeContent();
                    } else {
                        resultLabel.setText("Password must contain lower and upper characters, digits and be 6-20 in length");
                        resultLabel.setForeground(Color.red);
                    }
                } catch (Exception exception) {
                    MainUI.treatError(exception, (JFrame) SwingUtilities.getWindowAncestor(passPanel), 3);
                }
            } else {
                resultLabel.setText("Passwords do not match!");
                resultLabel.setForeground(Color.red);
            }
        });
    }

    private void changeContent() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException, ClassNotFoundException {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(passPanel);
        storageUI storageGUI = new storageUI();
        frame.setContentPane(storageGUI.getMainPanel());
        frame.revalidate();
        JOptionPane.showMessageDialog(frame,
                "Hi!\n" +
                        "This is where you will store your passwords and all its related information.\n" +
                        "Enjoy our solution and don't forget to hit the SAVE button!\n" +
                        "Please report any issues at cojo.intelligence@gmail.com.", "Welcome!", JOptionPane.INFORMATION_MESSAGE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                storageGUI.writeListPanel();
                System.exit(0);
            }
        });
    }

    public JPanel getPassPanel() {
        return passPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
