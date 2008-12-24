package mytime.app;

/**
 * 
 * AppRoot is the application facade that represents the entire application. It has a one-to-one relationship with the root UI
 * object, an {@link IUIRoot}. The entry point of the application is {@link AppRoot#Start(IUIRoot)}.
 * 
 */
public class AppRoot {

    private final IUIRoot _uiRoot;
    private final AppTrayIcon _appTrayIcon;
    private boolean _isRunning = false; // TODO: create a separate 'timer is running' model object
    private AppMainWindow _appMainWindow;

    /**
     * Starts the application, and connects it to the UI (i.e., the {@link IUIRoot} object).
     * 
     * @param uiRoot the root UI object for the application
     * @return the created application facade for the application
     */
    public static AppRoot Start(IUIRoot uiRoot) {
	return new AppRoot(uiRoot);
    }

    private AppRoot(IUIRoot uiRoot) {
	_uiRoot = uiRoot;
	// create application facade and UI...
	_appTrayIcon = new AppTrayIcon(this);
	IUITrayIcon uiTrayIcon = _uiRoot.showTrayIcon(_appTrayIcon, false);
	// ...and connect them
	_appTrayIcon.setUITrayIcon(uiTrayIcon);
    }

    IUIRoot getUIRoot() {
	return _uiRoot;
    }

    public void toggleTimer() {
	_isRunning = !_isRunning;
	_appTrayIcon.setRunning(_isRunning);
	if (_appMainWindow != null) {
	    _appMainWindow.setRunning(_isRunning);
	}
    }

    public void toggleWindows() {
	if (_appMainWindow == null) {
	    // create application facade and UI...
	    _appMainWindow = new AppMainWindow(this);
	    IUIMainWindow uiMainWindow = _uiRoot.showMainWindow(_appMainWindow);
	    // ...and connect them
	    _appMainWindow.setUIMainWindow(uiMainWindow);
	}
	_appMainWindow.toggleVisibility();
	_appTrayIcon.updateVisibility();
    }

    public boolean isTimerRunning() {
	return _isRunning;
    }

    public boolean areWindowsVisible() {
	return _appMainWindow != null && _appMainWindow.getVisibility();
    }

    public void exit() {
	_appTrayIcon.destroy();
	if (_appMainWindow != null) {
	    _appMainWindow.destroy();
	}
	_uiRoot.exit();
    }

    /**
     * Only necessary to keep Mockito tests simple...
     * 
     * @return the tray icon
     */
    public AppTrayIcon getAppTrayIcon() {
	return _appTrayIcon;
    }

    /**
     * Only necessary to keep Mockito tests simple...
     * 
     * @return the main window
     */
    public AppMainWindow getAppMainWindow() {
	return _appMainWindow;
    }
}
