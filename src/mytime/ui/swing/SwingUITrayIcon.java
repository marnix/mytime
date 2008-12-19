package mytime.ui.swing;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mytime.app.AppTrayIcon;
import mytime.app.IUITrayIcon;

public class SwingUITrayIcon implements IUITrayIcon {

    private final AppTrayIcon _appTrayIcon;
    private java.awt.TrayIcon _trayIcon;

    /**
     * Create and show the tray icon, making sure that all events are passed to the provided {@link AppTrayIcon}.
     */
    public SwingUITrayIcon(AppTrayIcon appTrayIcon) {
	_appTrayIcon = appTrayIcon;
	_trayIcon = new java.awt.TrayIcon(createImage(), null /* initially no tooltip */, createPopUpMenu());
	_trayIcon.setImageAutoSize(true);
	showTrayIcon();
    }

    private Image createImage() {
	return Toolkit.getDefaultToolkit().getImage(getClass().getResource("crummy-stopwatch.gif"));
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

    private void showTrayIcon() {
	try {
	    SystemTray.getSystemTray().add(_trayIcon);
	} catch (AWTException ex) {
	    // TODO: Replace this emergency handler by something more generic,
	    // which we could use in multiple places.
	    ex.printStackTrace();
	    _appTrayIcon.doExit();
	}
    }

    public void setTooltip(String tooltip) {
	_trayIcon.setToolTip(tooltip);
    }

    public void destroy() {
	SystemTray.getSystemTray().remove(_trayIcon);
	_trayIcon = null; // to make sure nobody tries to use it anymore
    }

}
