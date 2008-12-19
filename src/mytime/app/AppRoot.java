package mytime.app;

/**
 * 
 * AppRoot is the application facade that represents the entire application. It has a one-to-one relationship with the root UI
 * object, an {@link IUIRoot}. The entry point of the application is {@link AppRoot#Start(IUIRoot)}.
 * 
 */
public class AppRoot {

    private final IUIRoot _uiRoot;

    public static void Start(IUIRoot uiRoot) {
	new AppRoot(uiRoot);
    }

    public AppRoot(IUIRoot uiRoot) {
	_uiRoot = uiRoot;
	// create application facade and UI object...
	AppTrayIcon appTrayIcon = new AppTrayIcon(this);
	IUITrayIcon uiTrayIcon = _uiRoot.showTrayIcon(appTrayIcon);
	// ...and connect them
	appTrayIcon.setUITrayIcon(uiTrayIcon);
    }

    public IUIRoot getUIRoot() {
	return _uiRoot;
    }
}
