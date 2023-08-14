package gui.login;

import launcher.StartUI;
import login.Biometrics;

import javax.swing.*;

public class FaceBiometricsSignUpUI {
    private JButton doneButton;
    private JLabel bioLabel;
    private JButton startButton;
    private JPanel bioPanel;

    public JPanel getFaceBiometricsUIPanel() {
        return bioPanel;
    }

    public FaceBiometricsSignUpUI() {
        ImageIcon bioImageIcon = new ImageIcon("src/main/resources/lena.png");
        doneButton.setEnabled(false);
        startButton.addActionListener(e -> {
            try {
                startButton.setEnabled(false);
                startButton.paintImmediately(startButton.getVisibleRect());
                bioLabel.setText("Loading...");
                bioLabel.paintImmediately(bioLabel.getVisibleRect());
                Thread.sleep(5000);
                bioLabel.setText("");
                bioLabel.setIcon(bioImageIcon);
                bioLabel.paintImmediately(bioLabel.getVisibleRect());
                Biometrics biometrics = new Biometrics();
                String features = biometrics.getFeatures();
                Thread.sleep(5000);
                int response = biometrics.sendContent(features);
                if(response == 201) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bioPanel);
                    JOptionPane.showMessageDialog(frame, "Finish signing up by pressing on the DONE button!", "Biometric Done!", JOptionPane.INFORMATION_MESSAGE);
                    doneButton.setEnabled(true);
                } else {
                    throw new Exception("Connection error");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bioPanel);
                JOptionPane.showMessageDialog(frame, "Something is wrong with biometrics acquisition!", "Biometric Error!", JOptionPane.ERROR_MESSAGE);
            }
        });

        doneButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bioPanel);
            StartUI startUI1 = new StartUI();
            frame.setContentPane(startUI1.getStartPanel());
            frame.revalidate();
        });

    }


}
