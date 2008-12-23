package mytime.ui.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import mytime.app.AppMainWindow;
import mytime.app.IUIMainWindow;

/**
 * The UI for the main window.
 */
@SuppressWarnings("serial")
public class SwingUIMainWindow extends JFrame implements IUIMainWindow {

    AppMainWindow _appMainWindow;

    /**
     * Create and show the main window.
     * 
     * @param appMainWindow the application facade for this main window
     */
    public SwingUIMainWindow(AppMainWindow appMainWindow) {
	super("MyTime");
	_appMainWindow = appMainWindow;

	// TODO: add UI components here

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
		_appMainWindow.doMinimize();
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

    /**
     * Destroy this main window. After this, this object may not be used anymore
     * 
     */
    public void destroyMainWindow() {
	dispose();
	_appMainWindow = null; // just to make sure we don't use it anymore
    }

}
