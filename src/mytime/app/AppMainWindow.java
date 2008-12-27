package mytime.app;

import javax.swing.ButtonModel;

import mytime.app.models.NegatedButtonModel;

public class AppMainWindow {

    private IUIMainWindow _uiMainWindow;
    private boolean _isVisible;
    private final AppRoot _appRoot;

    public AppMainWindow(AppRoot appRoot) {
	_appRoot = appRoot;
	_isVisible = false;
    }

    void setUIMainWindow(IUIMainWindow uiMainWindow) {
	_uiMainWindow = uiMainWindow;
    }

    public void userPerformedMinimize() {
	if (_appRoot.areWindowsVisible()) {
	    _appRoot.toggleWindows();
	}
    }

    public void userAttemptedClose() {
	userPerformedMinimize();
    }

    boolean getVisibility() {
	return _isVisible;
    }

    void setVisibility(boolean isVisible) {
	_uiMainWindow.setVisibility(isVisible);
    }

    void toggleVisibility() {
	_isVisible = !_isVisible;
	setVisibility(_isVisible);
    }

    public ButtonModel getStartButtonModel() {
	return new NegatedButtonModel(_appRoot.getIsRunningModel());
    }

    public ButtonModel getPauseButtonModel() {
	return _appRoot.getIsRunningModel();
    }

    public void userPerformsStartTimer() {
	_appRoot.toggleTimer();
    }

    public void userPerformsPauseTimer() {
	_appRoot.toggleTimer();
    }

    void destroy() {
	_uiMainWindow.destroy();
    }
}
