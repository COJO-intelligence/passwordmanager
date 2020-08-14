package main.java.gui.login;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import main.java.gui.storage.storageMain;
import main.java.login.Login;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class MainUI {

    public static void main(String[] args) {
        FlatDarculaLaf.install();
        JFrame frame = new JFrame("PASSWORD MANAGER");
        try {
            Login login = new Login();
            if (login.isPasswordSet())
            {
                startLoginFrame(frame);
            }
            else
            {
                startSetPassFrame(frame);
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setResizable(false);
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    storageMain storageGUI = new storageMain("test.encrypt");
                    storageGUI.writeListPanel("test.encrypt");
                    if (JOptionPane.showConfirmDialog(frame,
                            "The passwords were saved!", "Success!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        System.exit(0);

                    }
                }
            });

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        finally {


        }


    }

    private static void startLoginFrame(JFrame frame)
    {
        LoginUI loginUI = new LoginUI();
        frame.setContentPane(loginUI.getMainPanel());
        frame.getRootPane().setDefaultButton(loginUI.getLoginButton());
    }

    private static void startSetPassFrame(JFrame frame)
    {
        SetPassUI setPassUI = new SetPassUI();
        frame.setContentPane(setPassUI.getPassPanel());
        frame.getRootPane().setDefaultButton(setPassUI.getLoginButton());
    }
}
