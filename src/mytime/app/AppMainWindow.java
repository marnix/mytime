package mytime.app;

import javax.swing.ButtonModel;

import mytime.app.models.NegatedButtonModel;

public class AppMainWindow {

    IUIMainWindow _uiMainWindow;
    private boolean _isVisible;
    final AppRoot _appRoot;

    public AppMainWindow(AppRoot appRoot) {
	_appRoot = appRoot;
	_isVisible = false;
    }

    public void setUIMainWindow(IUIMainWindow uiMainWindow) {
	_uiMainWindow = uiMainWindow;
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

    public ButtonModel getStartButtonModel() {
	return new NegatedButtonModel(_appRoot.getIsRunningModel());
    }

    public ButtonModel getPauseButtonModel() {
	return _appRoot.getIsRunningModel();
    }

    public void doStartTimer() {
	_appRoot.toggleTimer();
    }

    public void doPauseTimer() {
	_appRoot.toggleTimer();
    }

    public void destroy() {
	_uiMainWindow.destroyMainWindow();
    }
}
