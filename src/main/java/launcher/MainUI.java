package launcher;

import com.formdev.flatlaf.FlatDarculaLaf;
import manager.TimerManager;
import storage.User;

import javax.swing.*;


public class MainUI {

    public static User user = new User();

    public static void main(String[] args) {
        FlatDarculaLaf.install();
        JFrame frame = new JFrame("PASSWORD MANAGER");
        try {
            startUIFrame(frame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        } catch (Exception e) {
            treatError(frame, 1);
        }
    }

    private static void startUIFrame(JFrame frame) {
        StartUI startUI = new StartUI();
        SwingUtilities.invokeLater(() -> new TimerManager(startUI, frame));
        frame.setContentPane(startUI.getStartPanel());
    }

    public static void treatError(JFrame frame, int exitCode) {
        JOptionPane.showMessageDialog(frame, "Something went wrong...\n :(", "Fatal error!", JOptionPane.ERROR_MESSAGE);
        System.exit(exitCode);
    }

}
