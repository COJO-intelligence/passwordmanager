package launcher;

import gui.login.FaceBiometricsSignInUI;
import gui.login.SignInUI;
import gui.login.SignUpUI;

import javax.swing.*;

public class StartUI {
    private JPanel startPanel;
    private JButton signInButton;
    private JButton signUpButton;
    private JCheckBox biometricsCheckBox;

    public StartUI() {
        signInButton.addActionListener(e -> {
            if (biometricsCheckBox.isSelected()) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(startPanel);
                FaceBiometricsSignInUI faceBiometricsSignInUI = new FaceBiometricsSignInUI();
                frame.setContentPane(faceBiometricsSignInUI.getFaceBiometricsUIPanel());
                frame.revalidate();
            } else {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(startPanel);
                SignInUI signInUI = new SignInUI();
                frame.setContentPane(signInUI.getSignInPanel());
                frame.revalidate();
            }
        });
        signUpButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(startPanel);
            SignUpUI signUpUI = new SignUpUI();
            frame.setContentPane(signUpUI.getSignUpPanel());
            frame.revalidate();
        });
    }

    public JPanel getStartPanel() {
        return startPanel;
    }

}
