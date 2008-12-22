package mytime.test;

import junit.framework.TestCase;
import mytime.app.AppMainWindow;
import mytime.app.AppRoot;
import mytime.app.AppTrayIcon;
import mytime.app.IUIMainWindow;
import mytime.app.IUIRoot;
import mytime.app.IUITrayIcon;

public class MyTimeTestCase extends TestCase implements IUIRoot, IUITrayIcon, IUIMainWindow {

    private AppTrayIcon _appTrayIcon = null;
    private String _tooltip = null;
    private boolean _exitRequested = false;
    private boolean _isRunning = true;
    private Object _mainwindowIsVisible = false;
    private AppMainWindow _appMainWindow;

    public IUITrayIcon showTrayIcon(AppTrayIcon appTrayIcon, boolean isRunning) {
	_appTrayIcon = appTrayIcon;
	_isRunning = isRunning;
	return this;
    }

    public IUIMainWindow showMainWindow(AppMainWindow appMainWindow) {
	_appMainWindow = appMainWindow;
	_mainwindowIsVisible = true;
	return this;
    }

    public void setTooltip(String tooltip) {
	_tooltip = tooltip;
    }

    public void setRunning(boolean isRunning) {
	_isRunning = isRunning;
    }

    public void destroyTrayIcon() {
	_appTrayIcon = null;
    }

    public void setVisibility(boolean isVisible) {
	_mainwindowIsVisible = isVisible;
    }

    public void destroyMainWindow() {
	_appMainWindow = null;
    }

    public void exit() {
	assertTrue(_exitRequested);
	_exitRequested = false;
    }

    @Override
    public void setUp() {
	assertNull(_appTrayIcon);
	assertNull(_tooltip);
	AppRoot.Start(this);
	assertNotNull(_appTrayIcon);
	assertEquals(_tooltip, "MyTime");
	assertEquals(_isRunning, false);
    }

    @Override
    public void tearDown() {
	_exitRequested = true;
	_appTrayIcon.doExit();
	assertFalse(_exitRequested);
	assertNull(_appTrayIcon);
	assertNull(_appMainWindow);
    }

    public void testExitImmediately() {
	// everything happens in setUp() and tearDown()
    }

    public void testStartThenStopTimer() {
	assertEquals(_isRunning, false);

	_appTrayIcon.doToggleTimer();
	assertEquals(_isRunning, true);

	_appTrayIcon.doToggleTimer();
	assertEquals(_isRunning, false);

	_appTrayIcon.doToggleTimer();
	assertEquals(_isRunning, true);

	_appTrayIcon.doToggleTimer();
	assertEquals(_isRunning, false);
    }

    public void testShowWindowThenImmediatelyExit() {
	assertEquals(_mainwindowIsVisible, false);

	_appTrayIcon.doToggleWindows();
	assertEquals(_mainwindowIsVisible, true);
    }

    public void testShowThenHideWindow() {
	assertEquals(_mainwindowIsVisible, false);

	_appTrayIcon.doToggleWindows();
	assertEquals(_mainwindowIsVisible, true);

	_appTrayIcon.doToggleWindows();
	assertEquals(_mainwindowIsVisible, false);

	_appTrayIcon.doToggleWindows();
	assertEquals(_mainwindowIsVisible, true);

	_appMainWindow.doMinimize();
	assertEquals(_mainwindowIsVisible, false);
    }
}
