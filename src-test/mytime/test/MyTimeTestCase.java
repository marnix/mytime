package mytime.test;

import junit.framework.TestCase;
import mytime.app.AppRoot;
import mytime.app.AppTrayIcon;
import mytime.app.IUIRoot;
import mytime.app.IUITrayIcon;

public class MyTimeTestCase extends TestCase implements IUIRoot, IUITrayIcon {

    private AppTrayIcon _appTrayIcon = null;
    private String _tooltip = null;
    private boolean _exitRequested = false;

    public IUITrayIcon showTrayIcon(AppTrayIcon appTrayIcon) {
	_appTrayIcon = appTrayIcon;
	return this;
    }

    public void setTooltip(String tooltip) {
	_tooltip = tooltip;
    }

    public void exit() {
	assertTrue(_exitRequested);
	_exitRequested = false;
    }

    public void testExitImmediately() {
	assertNull(_appTrayIcon);
	assertNull(_tooltip);
	AppRoot.Start(this);
	assertNotNull(_appTrayIcon);
	assertEquals(_tooltip, "MyTime");

	_exitRequested = true;
	_appTrayIcon.doExit();
	assertFalse(_exitRequested);
	assertNull(_appTrayIcon);
    }

    public void destroy() {
	_appTrayIcon = null;
    }
}
