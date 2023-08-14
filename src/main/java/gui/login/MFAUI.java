package gui.login;

import com.google.zxing.WriterException;
import launcher.MainUI;
import launcher.StartUI;
import login.MFA;

import javax.swing.*;
import java.io.IOException;

public class MFAUI {
    private JPanel mfaPanel;
    private JButton nextButton;
    private JLabel qrLabel;
    private JCheckBox qrCompleted;

    public JPanel getMfaPanel() {
        return mfaPanel;
    }

    public MFAUI() {
        MFA mfa = new MFA();
        try {
            String barCodeUrl = mfa.getGoogleAuthenticatorBarCode(mfa.generateSecretKey(), MainUI.user.getEmail(), "COJO Intelligence");
            byte[] qrImage = mfa.getQrImage(barCodeUrl);
            qrLabel.setIcon(new ImageIcon(qrImage));
        }
        catch (WriterException | IOException e) {
            e.printStackTrace();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mfaPanel);
            JOptionPane.showMessageDialog(frame, "Something is wrong with QR Code generation!", "Multiple Factor Authentication Error!", JOptionPane.ERROR_MESSAGE);
        }

        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mfaPanel);
            int option = JOptionPane.showConfirmDialog(frame, "Would you like to configure biometric authentication?","Biometrics Authentication", JOptionPane.YES_NO_OPTION);
            if(option == 0) {
                //JOptionPane.showMessageDialog(frame, "Not yet implemented", "Wrong!", JOptionPane.ERROR_MESSAGE);
                FaceBiometricsSignUpUI faceBiometricsSignUpUI = new FaceBiometricsSignUpUI();
                frame.setContentPane(faceBiometricsSignUpUI.getFaceBiometricsUIPanel());
            } else {
                StartUI startUI1 = new StartUI();
                frame.setContentPane(startUI1.getStartPanel());
            }
            frame.revalidate();
        });

        qrCompleted.setSelected(false);
        qrCompleted.addActionListener(e -> {
            if(qrCompleted.isSelected()) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mfaPanel);
                JOptionPane.showMessageDialog(frame, "2FA is mandatory! Please make sure that the account is visible in Google Authenticator!", "Warning!", JOptionPane.WARNING_MESSAGE);
            }
            nextButton.setEnabled(qrCompleted.isSelected());
        });
    }

}