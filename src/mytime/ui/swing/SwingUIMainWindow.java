package mytime.ui.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mytime.app.AppMainWindow;
import mytime.app.IUIMainWindow;

/**
 * The UI for the main window.
 */
@SuppressWarnings("serial")
public class SwingUIMainWindow extends JFrame implements IUIMainWindow {

    AppMainWindow _appMainWindow;
    private final JButton _jStartButton;
    private final JButton _jPauseButton;

    /**
     * Create and show the main window.
     * 
     * @param appMainWindow the application facade for this main window
     */
    public SwingUIMainWindow(AppMainWindow appMainWindow) {
	super("MyTime");
	_appMainWindow = appMainWindow;

	JPanel pane = new JPanel();
	getContentPane().add(pane);

	_jStartButton = new JButton("|>");
	_jStartButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		_appMainWindow.doStartTimer();
	    }
	});
	pane.add(_jStartButton);
	_jStartButton.setEnabled(false);

	_jPauseButton = new JButton("||");
	_jPauseButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		_appMainWindow.doPauseTimer();
	    }
	});
	pane.add(_jPauseButton);
	_jPauseButton.setEnabled(false);

	pack();
	// TODO: Remember the window position and size from a previous run, and move the window to the same place it was.
	// This can be stored through the Preferences class.
	moveToLowerRightHandCorner();

	// TODO: Make sure that the icon image is the same as the tray icon image, probably by moving knowledge about the icon to
	// SwingUIRoot.

	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		// on close, we minimize to the tray icon
		_appMainWindow.doClose();
	    }

	    @Override
	    public void windowIconified(WindowEvent e) {
		// on close, we minimize to the tray icon
		_appMainWindow.doMinimize();
	    }
	});
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	connectModel();
    }

    private void moveToLowerRightHandCorner() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/*
	 * We subtract an arbitrary number of pixels, to make sure that we're clear of any toolbar and such.
	 */
	setLocation(screenSize.width - getWidth() - 64, screenSize.height - getHeight() - 64);
    }

    private void connectModel() {
	// TODO: fill in when UI components have been added
    }

    /**
     * Change the visibility for this main window
     * 
     * @param isVisible true or false
     * 
     */
    public void setVisibility(boolean isVisible) {
	setVisible(isVisible);
	if (isVisible) {
	    setState(NORMAL);
	    toFront();
	}
    }

    public void setStartEnabled(boolean enabled) {
	_jStartButton.setEnabled(enabled);
    }

    public void setPauseEnabled(boolean enabled) {
	_jPauseButton.setEnabled(enabled);
    }

    /**
     * Destroy this main window. After this, this object may not be used anymore
     * 
     */
    public void destroyMainWindow() {
	dispose();
	_appMainWindow = null; // just to make sure we don't use it anymore
    }

}
