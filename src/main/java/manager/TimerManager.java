package manager;

import launcher.StartUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerManager implements ActionListener {

    private static final int SESSION_TIMEOUT = 15 * 60 * 1000;
    private final Timer invalidationTimer = new Timer(SESSION_TIMEOUT, this);

    StartUI startUI;
    JFrame frame;

    public TimerManager(StartUI mainLogin, JFrame jFrame) {
        startUI = mainLogin;
        frame = jFrame;
        invalidationTimer.setRepeats(false);
        invalidationTimer.restart();
        final AWTEventListener l = event -> invalidationTimer.restart();
        Toolkit.getDefaultToolkit().addAWTEventListener(l, AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setContentPane(startUI.getStartPanel());
        //frame.getRootPane().setDefaultButton(startUI.getLoginButton());
        frame.revalidate();
        invalidationTimer.restart();
    }
}
