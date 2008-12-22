package mytime.ui.swing;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mytime.app.AppTrayIcon;
import mytime.app.IUITrayIcon;

/**
 * The UI object representing the tray icon.
 */
public class SwingUITrayIcon implements IUITrayIcon {

    final AppTrayIcon _appTrayIcon;
    private java.awt.TrayIcon _awtTrayIcon;

    /**
     * Create and show the AWT tray icon, making sure that all events are passed to the provided {@link AppTrayIcon}.
     * 
     * @param appTrayIcon the application facade for the tray icon
     * @param isRunning whether the tray icon should display the 'running' state or not
     */
    public SwingUITrayIcon(AppTrayIcon appTrayIcon, boolean isRunning) {
	_appTrayIcon = appTrayIcon;
	_awtTrayIcon = new java.awt.TrayIcon(createImage(isRunning), null /* initially no tooltip */, createPopUpMenu());
	_awtTrayIcon.setImageAutoSize(true);
	_awtTrayIcon.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
		    switch (e.getClickCount()) {
		    case 3:
			// a triple left-click on the icon: toggle the timer
			//
			// (Why did I use the triple-click for this? well, a single-click event is not only generated on a
			// single-click, but on a double-click, right before the e.getClickCount() == 2 event. Sigh.)
			_appTrayIcon.doToggleTimer();
			break;
		    case 2:
			_appTrayIcon.doToggleMainWindow();
			break;
		    default:
			// ignore triple-clicks
		    }
		} else {
		    // ignore the other buttons
		}
	    }
	});
	showAWTTrayIcon();
    }

    private Image createImage(boolean isRunning) {
	// TODO: create a static array of size two with two Image objects, for efficiency.
	return Toolkit.getDefaultToolkit().getImage(getClass().getResource(isRunning ? "clock-run.png" : "clock-stop.png"));
    }

    private PopupMenu createPopUpMenu() {
	PopupMenu popup = new PopupMenu();

	// create menu item for the exit action
	MenuItem defaultItem = new MenuItem("Exit");
	defaultItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		_appTrayIcon.doExit();
	    }
	});
	popup.add(defaultItem);

	// TODO: add other menu items

	return popup;
    }

    private void showAWTTrayIcon() {
	try {
	    SystemTray.getSystemTray().add(_awtTrayIcon);
	} catch (AWTException ex) {
	    // TODO: Replace this emergency handler by something more generic,
	    // which we could use in multiple places.
	    ex.printStackTrace();
	    _appTrayIcon.doExit();
	}
    }

    /**
     * Set the tooltip on the AWT icon.
     * 
     * @see mytime.app.IUITrayIcon#setTooltip(java.lang.String)
     */
    public void setTooltip(String tooltip) {
	_awtTrayIcon.setToolTip(tooltip);
    }

    /**
     * Set the running/stopped state.
     * 
     * @param isRunning whether or not the icon shows 'running'
     * 
     */
    public void setRunning(boolean isRunning) {
	_awtTrayIcon.setImage(createImage(isRunning));
    }

    /**
     * Remove the AWT icon.
     * 
     * @see mytime.app.IUITrayIcon#destroyTrayIcon()
     */
    public void destroyTrayIcon() {
	SystemTray.getSystemTray().remove(_awtTrayIcon);
	_awtTrayIcon = null; // to make sure nobody tries to use it anymore
    }

}
