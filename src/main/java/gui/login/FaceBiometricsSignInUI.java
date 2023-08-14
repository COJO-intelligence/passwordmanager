package gui.login;

import gui.storage.StorageUI;
import launcher.MainUI;
import login.Biometrics;

import javax.swing.*;

public class FaceBiometricsSignInUI {


    private JButton doneButton;
    private JLabel bioLabel;
    private JButton startButton;
    private JTextField emailTextField;
    private JPanel bioPanel;

    public JPanel getFaceBiometricsUIPanel() {
        return bioPanel;
    }

    public FaceBiometricsSignInUI() {
        ImageIcon bioImageIcon = new ImageIcon("src/main/resources/lena.png");
        doneButton.setEnabled(false);
        startButton.addActionListener(e -> {
            try {
                MainUI.user.setEmail(emailTextField.getText());
                startButton.setEnabled(false);
                startButton.paintImmediately(startButton.getVisibleRect());
                bioLabel.setText("Loading...");
                bioLabel.paintImmediately(bioLabel.getVisibleRect());
                Thread.sleep(5000);
                bioLabel.setText("");
                bioLabel.setIcon(bioImageIcon);
                bioLabel.paintImmediately(bioLabel.getVisibleRect());
                Biometrics biometrics = new Biometrics();
                MainUI.user.setPassword(biometrics.getContent(biometrics.getFeatures()));
                Thread.sleep(5000);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bioPanel);
                JOptionPane.showMessageDialog(frame, "Finish signing in by pressing on the DONE button!", "Biometric Done!", JOptionPane.INFORMATION_MESSAGE);
                doneButton.setEnabled(true);
            } catch (Exception exception) {
                exception.printStackTrace();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bioPanel);
                JOptionPane.showMessageDialog(frame, "Something is wrong with biometrics acquisition!", "Biometric Error!", JOptionPane.ERROR_MESSAGE);
                startButton.setEnabled(true);
                startButton.paintImmediately(startButton.getVisibleRect());
            }
        });

        doneButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bioPanel);
            try {
                StorageUI storageUI = new StorageUI();
                frame.setContentPane(storageUI.getMainPanel());
                frame.revalidate();
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Something is wrong with biometrics acquisition!", "Biometric Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


}
