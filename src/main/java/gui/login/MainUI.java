package main.java.gui.login;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import main.java.login.Login;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainUI {

    public static void main(String[] args) {
        FlatDarculaLaf.install();
        try {
            Login login = new Login();
            if (login.isPasswordSet())
            {
                startLoginFrame();
            }
            else
            {
                startSetPassFrame();
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        finally {


        }


    }

    private static void startLoginFrame()
    {
        JFrame frame = new JFrame("LoginUI");
        LoginUI loginUI = new LoginUI();
        frame.setContentPane(loginUI.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getRootPane().setDefaultButton(loginUI.getLoginButton());
    }

    private static void startSetPassFrame()
    {
        JFrame frame = new JFrame("SetPassUI");
        SetPassUI setPassUI = new SetPassUI();
        frame.setContentPane(setPassUI.getPassPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getRootPane().setDefaultButton(setPassUI.getLoginButton());
    }
}
