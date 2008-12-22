package mytime.ui.swing;

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

	// add UI components
	pack();
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent a_event) {
		// on close, we minimize to the tray icon
		_appMainWindow.doMinimize();
	    }
	});
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	connectModel();
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
