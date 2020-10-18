package main.java.manager;

import main.java.gui.login.LoginUI;
import main.java.gui.storage.StorageUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerManager implements ActionListener {

    //TODO schimba asta la 30 de minute
    private static final int SESSION_TIMEOUT = 3 * 1000; // 10 sec timeout for testing purposes
    private final Timer invalidationTimer = new Timer(SESSION_TIMEOUT, this);

    LoginUI loginUI;
    JFrame frame;

    public TimerManager(LoginUI mainLogin, JFrame jFrame) {

        loginUI = mainLogin;
        frame = jFrame;
        invalidationTimer.setRepeats(false);
        invalidationTimer.restart();

        // register listener to get all mouse/key events
        final AWTEventListener l = new AWTEventListener() {

            @Override
            public void eventDispatched(AWTEvent event) {
                // if any input event invoked - restart the timer to prolong the session
                invalidationTimer.restart();
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(l, AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // sets back to login
        frame.setContentPane(loginUI.getMainPanel());
        frame.getRootPane().setDefaultButton(loginUI.getLoginButton());
        frame.revalidate();
        invalidationTimer.restart();
    }
}
