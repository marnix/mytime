package mytime.ui.swing;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mytime.app.AppTrayIcon;
import mytime.app.IUITrayIcon;

/**
 * The UI object representing the tray icon.
 */
public class SwingUITrayIcon implements IUITrayIcon {

    private final AppTrayIcon _appTrayIcon;
    private java.awt.TrayIcon _awtTrayIcon;
    private CheckboxMenuItem _awtHideShowItem;
    private CheckboxMenuItem _awtToggleTimerMenuItem;
    private MenuItem _awtExitItem;

    /**
     * Create and show the AWT tray icon, making sure that all events are passed to the provided {@link AppTrayIcon}.
     * 
     * @param appTrayIcon the application facade for the tray icon
     * @param isRunning whether the tray icon should display the 'running' state or not
     */
    public SwingUITrayIcon(AppTrayIcon appTrayIcon, boolean isRunning) {
	_appTrayIcon = appTrayIcon;

	createComponents(isRunning);
	addEventListeners();
	connectModels();
	show();
    }

    private void createComponents(boolean isRunning) {
	_awtTrayIcon = new java.awt.TrayIcon(createImage(isRunning), null /* initially no tooltip */, createPopUpMenu());
	_awtTrayIcon.setImageAutoSize(true);
    }

    private Image createImage(boolean isRunning) {
	// TODO: create a static array of size two with two Image objects, for efficiency.
	return Toolkit.getDefaultToolkit().getImage(
		getClass().getResource(isRunning ? "icons/clock-run.png" : "icons/clock-stop.png"));
    }

    private PopupMenu createPopUpMenu() {
	PopupMenu popup = new PopupMenu();

	_awtHideShowItem = new CheckboxMenuItem("Show window");
	_awtHideShowItem.setState(_appTrayIcon.areWindowsVisible());
	popup.add(_awtHideShowItem);

	_awtToggleTimerMenuItem = new CheckboxMenuItem("Run timer");
	popup.add(_awtToggleTimerMenuItem);

	_awtExitItem = new MenuItem("Exit");
	popup.add(_awtExitItem);

	return popup;
    }

    private void addEventListeners() {
	_awtTrayIcon.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		_appTrayIcon.doToggleWindows();
	    }
	});

	_awtHideShowItem.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		assert e.getStateChange() == (_appTrayIcon.areWindowsVisible() ? ItemEvent.DESELECTED : ItemEvent.SELECTED);
		_appTrayIcon.doToggleWindows();
	    }
	});
	_awtToggleTimerMenuItem.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		_appTrayIcon.doToggleTimer();
	    }
	});
	_awtExitItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		_appTrayIcon.doExit();
	    }
	});
    }

    private void connectModels() {
	_appTrayIcon.getIsRunningModel().addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent a_e) {
		boolean isRunning = _appTrayIcon.getIsRunningModel().isEnabled();
		_awtTrayIcon.setImage(createImage(isRunning));
		_awtToggleTimerMenuItem.setState(isRunning);
	    }
	});
    }

    private void show() {
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

    public void setWindowsVisible(boolean areVisible) {
	_awtHideShowItem.setState(areVisible);
    }

    /**
     * Remove the AWT icon.
     * 
     * @see mytime.app.IUITrayIcon#destroy()
     */
    public void destroy() {
	SystemTray.getSystemTray().remove(_awtTrayIcon);
	_awtTrayIcon = null; // to make sure nobody tries to use it anymore
    }

}
