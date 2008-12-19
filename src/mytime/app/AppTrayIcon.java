package mytime.app;

public class AppTrayIcon {

    private IUITrayIcon _uiTrayIcon;
    private final AppRoot _appRoot;

    public AppTrayIcon(AppRoot appRoot) {
	_appRoot = appRoot;
    }

    public void setUITrayIcon(IUITrayIcon trayIcon) {
	_uiTrayIcon = trayIcon;
	_uiTrayIcon.setTooltip("MyTime");
    }

    public void doExit() {
	_uiTrayIcon.destroy();
	_uiTrayIcon = null; // to make sure we don't use it anymore
	_appRoot.getUIRoot().exit();
    }
}
