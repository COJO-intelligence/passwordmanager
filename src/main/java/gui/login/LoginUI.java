package main.java.gui.login;

import com.formdev.flatlaf.FlatLightLaf;
import main.java.gui.storage.storageMain;
import main.java.login.Login;
import main.java.login.PasswordExistsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginUI {

    private JPanel mainPanel;
    private JLabel titleLabel;
    private JPasswordField passwordField;
    private JLabel welcomeLabel;
    private JLabel enterLabel;
    private JButton loginButton;
    private JLabel resultLabel;
    private JButton resetButton;

    public LoginUI() {
        Color resultOriginalFG = resultLabel.getForeground();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login(new String(passwordField.getPassword()));
                    if (login.validateUserPassword())
                    {
                        goToNextFrame();
                    }
                    else
                    {
                        resultLabel.setText("INVALID password! Try again");
                        resultLabel.setForeground(Color.red);
                    }
                } catch (NoSuchAlgorithmException | IOException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        });
//        resetButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Login login = null;
//                try {
//                    login = new Login();
//                    login.resetPassword();
//                    startSetPassFrame();
//                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
//                    noSuchAlgorithmException.printStackTrace();
//                } catch (PasswordExistsException passwordExistsException) {
//                    passwordExistsException.printStackTrace();
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
//
//            }
//        });
        resetButton.setVisible(false);
    }

    private void goToNextFrame()
    {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        topFrame.dispose();
        JFrame frame = new JFrame("Storage");
        storageMain storageGUI = new storageMain("test.encrypt");
        frame.setContentPane(storageGUI.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                storageGUI.writeListPanel("test.encrypt");
                if (JOptionPane.showConfirmDialog(frame,
                        "Nu faci, dumneata, ordine la mine in birou!\nVezi ca ti-am salvat fisierul, Pitica Nenorocita!", "Aici e mana lui Videanu",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);

                }
            }
        });

    }

    private void startSetPassFrame()
    {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        topFrame.dispose();
        JFrame frame = new JFrame("SetPassUI");
        SetPassUI setPassUI = new SetPassUI();
        frame.setContentPane(setPassUI.getPassPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.getRootPane().setDefaultButton(setPassUI.getLoginButton());
        frame.setResizable(false);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
