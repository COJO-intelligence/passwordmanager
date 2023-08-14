package gui.login;

import launcher.MainUI;
import login.Login;
import storage.CryptographicOperations;

import javax.swing.*;

public class SignUpUI {

    private JPanel signUpPanel;
    private JTextField emailTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField passwordTextField;
    private JTextField retypePasswordTextField;
    private JButton signUpButton;

    public SignUpUI () {
        signUpButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(signUpPanel);
            try {
                boolean ok = true;
                Login login = new Login();
                if (!login.isValidEmailAddress((emailTextField.getText()))) {
                    JOptionPane.showMessageDialog(frame, "Email should be valid!", "Email error!", JOptionPane.ERROR_MESSAGE);
                    ok = false;
                } else {
                    if (!login.checkPasswordStrength(passwordTextField.getText())) {
                        ok = false;
                        JOptionPane.showMessageDialog(frame, "Password must contain lower and upper characters, digits and be 6-30 in length", "Email error!", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (!login.checkPasswords(passwordTextField.getText(), retypePasswordTextField.getText())) {
                            ok = false;
                            JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Password error!", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (
                                    login.validateTextField(emailTextField.getText()) || login.validateTextField(passwordTextField.getText()) || login.validateTextField(retypePasswordTextField.getText())
                            ) {
                                ok = false;
                                JOptionPane.showMessageDialog(frame, "Text should be between 8 and 100 characters", "Text fields error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                if(ok) {
                    MainUI.user.setPassword(new String(CryptographicOperations.getHash(passwordTextField.getText().toCharArray())));
                    System.out.println(MainUI.user.getPassword());

                    int response = login.register(emailTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), CryptographicOperations.hashPasswordBcrypt(MainUI.user.getPassword()));
                    if (response == 201) {
                        MainUI.user.setEmail(emailTextField.getText());
                        MainUI.user.setFirstName(firstNameTextField.getText());
                        MainUI.user.setLastName(lastNameTextField.getText());
                        MFAUI mfaui = new MFAUI();
                        frame.setContentPane(mfaui.getMfaPanel());
                        frame.revalidate();
                    } else {
                        throw new Exception("No response from server");
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Something is wrong with the connection!", "Connection error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getSignUpPanel() {
        return signUpPanel;
    }

}
