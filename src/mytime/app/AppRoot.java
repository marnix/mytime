package mytime.app;

import java.util.Date;

import mytime.app.models.IValueModel;
import mytime.app.models.ToggleButtonModel;
import mytime.app.models.ValueHolder;

/**
 * 
 * AppRoot is the application facade that represents the entire application. It has a one-to-one relationship with the root UI
 * object, an {@link IUIRoot}. The entry point of the application is {@link AppRoot#Start(IUIRoot)}.
 * 
 */
public class AppRoot {

    private final IUIRoot _uiRoot;
    private final AppTrayIcon _appTrayIcon;
    private ToggleButtonModel _isRunningModel;
    private AppMainWindow _appMainWindow;
    private final ValueHolder<Date> _startTimeModel;

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

	_isRunningModel = new ToggleButtonModel();
	_startTimeModel = new ValueHolder<Date>(null);

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
	_isRunningModel.toggle();
	_startTimeModel.setValue(_uiRoot.getTime());
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

    public ToggleButtonModel getIsRunningModel() {
	return _isRunningModel;
    }

    public boolean areWindowsVisible() {
	return _appMainWindow != null && _appMainWindow.getVisibility();
    }

    public void exit() {
	_appTrayIcon.destroy();
	if (_appMainWindow != null) {
	    _appMainWindow.destroy();
	}
	_isRunningModel = null;
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

    public IValueModel<Date> getStartTimeModel() {
	return _startTimeModel;
    }
}
