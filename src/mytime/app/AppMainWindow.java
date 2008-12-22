package mytime.app;

public class AppMainWindow {

    private IUIMainWindow _uiMainWindow;
    private boolean _isVisible;

    public AppMainWindow(AppRoot appRoot) {
	_isVisible = false;
    }

    public void setUIMainWindow(IUIMainWindow uiMainWindow) {
	_uiMainWindow = uiMainWindow;
    }

    public void doMinimize() {
	setVisibility(false);
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
