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
    }

    public void doMinimize() {
	if (_appRoot.areWindowsVisible()) {
	    _appRoot.toggleWindows();
	}
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

    public void destroy() {
	_uiMainWindow.destroyMainWindow();
    }
}
