package main.java.manager;

import main.java.gui.login.LoginUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerManager implements ActionListener {

    private static final int SESSION_TIMEOUT = 15 * 60 * 1000;
    private final Timer invalidationTimer = new Timer(SESSION_TIMEOUT, this);

    LoginUI loginUI;
    JFrame frame;

    public TimerManager(LoginUI mainLogin, JFrame jFrame) {

        loginUI = mainLogin;
        frame = jFrame;
        invalidationTimer.setRepeats(false);
        invalidationTimer.restart();
        final AWTEventListener l = event -> invalidationTimer.restart();
        Toolkit.getDefaultToolkit().addAWTEventListener(l, AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setContentPane(loginUI.getMainPanel());
        frame.getRootPane().setDefaultButton(loginUI.getLoginButton());
        frame.revalidate();
        invalidationTimer.restart();
    }
}
