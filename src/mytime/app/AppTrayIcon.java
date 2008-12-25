package mytime.app;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** The application facade that represents the tray icon, called from {@link IUITrayIcon}. */

public class AppTrayIcon {

    private IUITrayIcon _uiTrayIcon;
    private final AppRoot _appRoot;
    private final ChangeListener _isRunningChangeListener;

    AppTrayIcon(AppRoot appRoot) {
	_appRoot = appRoot;
	_isRunningChangeListener = new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		setRunning(_appRoot.getIsRunningModel().isEnabled());
	    }
	};
	_appRoot.getIsRunningModel().addChangeListener(_isRunningChangeListener);
    }

    void setUITrayIcon(IUITrayIcon trayIcon) {
	_uiTrayIcon = trayIcon;
	_uiTrayIcon.setTooltip("MyTime");
    }

    public void setRunning(boolean isRunning) {
	_uiTrayIcon.setRunning(isRunning);
    }

    public boolean areWindowsVisible() {
	return _appRoot.areWindowsVisible();
    }

    public void doToggleTimer() {
	_appRoot.toggleTimer();
    }

    public void doToggleWindows() {
	_appRoot.toggleWindows();
    }

    public void updateVisibility() {
	_uiTrayIcon.setWindowsVisible(areWindowsVisible());
    }

    /** Called by the UI when the 'exit' option of the tray icon has been activated. */
    public void doExit() {
	_appRoot.exit();
    }

    void destroy() {
	_uiTrayIcon.destroy();
	_appRoot.getIsRunningModel().removeChangeListener(_isRunningChangeListener);
	_uiTrayIcon = null; // to make sure we don't use it anymore
    }
}
