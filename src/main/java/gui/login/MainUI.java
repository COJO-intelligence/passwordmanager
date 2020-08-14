package main.java.gui.login;

import com.formdev.flatlaf.FlatIntelliJLaf;
import main.java.login.Login;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainUI {

    public static Logger LOGGER = Logger.getLogger(MainUI.class.getName());

    public static void main(String[] args) throws IOException {


        FileHandler handler = new FileHandler("pm.log", true);
        LOGGER.addHandler(handler);

        FlatIntelliJLaf.install();

        JFrame frame = new JFrame("PASSWORD MANAGER");
        try {
            Login login = new Login();
            if (login.isPasswordSet()) {
                startLoginFrame(frame);
            } else {
                startSetPassFrame(frame);
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setResizable(false);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(frame, "Something went wrong... Email pm.log file at gigi@gmail.com", "FATAL ERROR!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }
    }

    private static void startLoginFrame(JFrame frame){
        LoginUI loginUI = new LoginUI();
        frame.setContentPane(loginUI.getMainPanel());
        frame.getRootPane().setDefaultButton(loginUI.getLoginButton());
    }

    private static void startSetPassFrame(JFrame frame){
        SetPassUI setPassUI = new SetPassUI();
        frame.setContentPane(setPassUI.getPassPanel());
        frame.getRootPane().setDefaultButton(setPassUI.getLoginButton());
    }
}
