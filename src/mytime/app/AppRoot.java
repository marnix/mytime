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
    private boolean _isRunning = false;
    private AppMainWindow _appMainWindow;

    /**
     * Starts the application, and connects it to the UI (i.e., the {@link IUIRoot} object).
     * 
     * @param uiRoot the root UI object for the application
     */
    public static void Start(IUIRoot uiRoot) {
	new AppRoot(uiRoot);
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
    }

    public boolean isTimerRunning() {
	return _isRunning;
    }

    boolean areWindowsVisible() {
	return _appMainWindow != null && _appMainWindow.getVisibility();
    }

    public void exit() {
	_appTrayIcon.destroy();
	if (_appMainWindow != null) {
	    _appMainWindow.destroy();
	}
	_uiRoot.exit();
    }
}
