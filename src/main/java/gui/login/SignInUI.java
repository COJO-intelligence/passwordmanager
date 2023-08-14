package gui.login;

import gui.storage.StorageUI;
import launcher.MainUI;
import login.Login;
import login.MFA;
import storage.CryptographicOperations;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

public class SignInUI {
    private JPanel signInPanel;
    private JButton signInButton;
    private JTextField emailTextField;
    private JTextField passwordTextField;

    public JPanel getSignInPanel() {
        return signInPanel;
    }

    public SignInUI() {
        Login login = new Login();
        signInButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(signInPanel);
            try {
                MainUI.user.setPassword(new String(CryptographicOperations.getHash(passwordTextField.getText().toCharArray())));
                System.out.println(MainUI.user.getPassword());
                MainUI.user.setEmail(emailTextField.getText());
                //LOGIN USER
                String googleCode = JOptionPane.showInputDialog("Please enter 2FA Code. Check Google Authenticator!");
                MFA mfa = new MFA();
                String mfaKey = login.getMFAKey();
                if(googleCode.equals(mfa.getTOTPCode(mfaKey))) {
                    StorageUI storageUI = new StorageUI();
                    frame.setContentPane(storageUI.getMainPanel());
                    frame.revalidate();
                } else {
                    JOptionPane.showMessageDialog(frame, "2FA Code invalid! Please type it again.", "2FA Code is not correct!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | CertificateException | NoSuchAlgorithmException | InvalidKeyException | UnrecoverableEntryException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | KeyStoreException | IllegalBlockSizeException | ClassNotFoundException | InvalidKeySpecException | KeyManagementException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Something went wrong...\n :(", "Fatal error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
