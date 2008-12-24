package mytime.app;

public class AppMainWindow {

    private IUIMainWindow _uiMainWindow;
    private boolean _isVisible;
    private final AppRoot _appRoot;

    public AppMainWindow(AppRoot appRoot) {
	_appRoot = appRoot;
	_isVisible = false;
    }

    public void setUIMainWindow(IUIMainWindow uiMainWindow) {
	_uiMainWindow = uiMainWindow;
	if (_appRoot.isTimerRunning()) {
	    _uiMainWindow.setPauseEnabled(true);
	} else {
	    _uiMainWindow.setStartEnabled(true);
	}
    }

    public void doMinimize() {
	if (_appRoot.areWindowsVisible()) {
	    _appRoot.toggleWindows();
	}
    }

    public void doClose() {
	doMinimize();
    }

    public boolean getVisibility() {
	return _isVisible;
    }

    public void setVisibility(boolean isVisible) {
	_uiMainWindow.setVisibility(isVisible);
    }

    public void toggleVisibility() {
	_isVisible = !_isVisible;
	setVisibility(_isVisible);
    }

    public void setRunning(boolean running) {
	assert running == _appRoot.isTimerRunning();
	if (running) {
	    _uiMainWindow.setStartEnabled(false);
	    _uiMainWindow.setPauseEnabled(true);
	} else {
	    _uiMainWindow.setStartEnabled(true);
	    _uiMainWindow.setPauseEnabled(false);
	}
    }

    public void doStartTimer() {
	assert !_appRoot.isTimerRunning();
	_appRoot.toggleTimer();
    }

    public void doPauseTimer() {
	assert _appRoot.isTimerRunning();
	_appRoot.toggleTimer();
    }

    public void destroy() {
	_uiMainWindow.destroyMainWindow();
    }
}
