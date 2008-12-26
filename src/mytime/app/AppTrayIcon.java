package mytime.app;

import mytime.app.models.ToggleButtonModel;

/** The application facade that represents the tray icon, called from {@link IUITrayIcon}. */

public class AppTrayIcon {

    private IUITrayIcon _uiTrayIcon;
    private final AppRoot _appRoot;

    AppTrayIcon(AppRoot appRoot) {
	_appRoot = appRoot;
    }

    public ToggleButtonModel getIsRunningModel() {
	return _appRoot.getIsRunningModel();
    }

    void setUITrayIcon(IUITrayIcon trayIcon) {
	_uiTrayIcon = trayIcon;
	_uiTrayIcon.setTooltip("MyTime");
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
    public void userPerformedExit() {
	_appRoot.exit();
    }

    void destroy() {
	_uiTrayIcon.destroy();
	_uiTrayIcon = null; // to make sure we don't use it anymore
    }
}
