package main.java;

import com.formdev.flatlaf.FlatDarculaLaf;
import main.java.gui.login.LoginUI;
import main.java.gui.login.SetPassUI;
import main.java.login.Login;
import main.java.storage.FileOperations;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainUI {

    public static Logger LOGGER = Logger.getLogger(MainUI.class.getName());

    public static void main(String[] args) throws IOException {
        FileOperations.startUserDirectory();
        Path logPath = Paths.get(FileOperations.directoryPath, "pm.log");
        FileHandler handler = new FileHandler(String.valueOf(logPath), true);
        LOGGER.addHandler(handler);
        FlatDarculaLaf.install();
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
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        } catch (Exception e) {
            treatError(e, frame, 1);
        }
    }

    private static void startLoginFrame(JFrame frame) {
        LoginUI loginUI = new LoginUI();
        frame.setContentPane(loginUI.getMainPanel());
        frame.getRootPane().setDefaultButton(loginUI.getLoginButton());
    }

    private static void startSetPassFrame(JFrame frame) {
        SetPassUI setPassUI = new SetPassUI();
        frame.setContentPane(setPassUI.getPassPanel());
        frame.getRootPane().setDefaultButton(setPassUI.getLoginButton());
    }

    public static void treatError(Exception e, JFrame frame, int exitCode) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        JOptionPane.showMessageDialog(frame, "Something went wrong...\nPlease, send an email with the pm.log file at cojo.intelligence@gmail.com", "FATAL ERROR!", JOptionPane.ERROR_MESSAGE);
        System.exit(exitCode);
    }
}
