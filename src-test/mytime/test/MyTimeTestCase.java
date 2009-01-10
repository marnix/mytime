package mytime.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import junit.framework.TestCase;
import mytime.app.AppMainWindow;
import mytime.app.AppRoot;
import mytime.app.AppTrayIcon;
import mytime.app.IUIMainWindow;
import mytime.app.IUIRoot;
import mytime.app.IUITrayIcon;

public class MyTimeTestCase extends TestCase {

    private AppRoot _appRoot;
    private IUITrayIcon _mockUITrayIcon;
    private IUIRoot _mockUIRoot;
    private AppTrayIcon _appTrayIcon;
    private IUIMainWindow _mockUIMainWindow;

    @Override
    public void setUp() {
	_mockUIRoot = mock(IUIRoot.class);
	_mockUITrayIcon = mock(IUITrayIcon.class);
	when(_mockUIRoot.showTrayIcon(isA(AppTrayIcon.class), eq(false))).thenReturn(_mockUITrayIcon);
	_mockUIMainWindow = mock(IUIMainWindow.class);
	when(_mockUIRoot.showMainWindow(isA(AppMainWindow.class))).thenReturn(_mockUIMainWindow);

	_appRoot = AppRoot.Start(_mockUIRoot);

	_appTrayIcon = _appRoot.getAppTrayIcon();

	verifyNoMoreInteractions(_mockUIMainWindow);
    }

    public void testStartup() {
	verify(_mockUITrayIcon).setTooltip("MyTime");
	verify(_mockUIRoot, never()).showMainWindow(isA(AppMainWindow.class));
	assertEquals(_appRoot.areWindowsVisible(), false);
    }

    public void testExit() {
	_appTrayIcon.userPerformedExit();

	verify(_mockUITrayIcon).destroy();
	verify(_mockUIRoot).exit();
    }

    public void testStartThenStopTimerFromTrayIcon() {
	_appTrayIcon.doToggleTimer();

	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), true);

	_appTrayIcon.doToggleTimer();

	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), false);

	_appTrayIcon.doToggleTimer();

	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), true);

	_appTrayIcon.doToggleTimer();

	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), false);
    }

    public void testStartTimerFromTrayIconThenStopAndStartTimerFromMainWindow() {
	_appTrayIcon.doToggleTimer();

	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), true);

	_appTrayIcon.doToggleWindows();

	AppMainWindow appMainWindow = _appRoot.getAppMainWindow();
	assertEquals(appMainWindow.getStartButtonModel().isEnabled(), false);
	assertEquals(appMainWindow.getPauseButtonModel().isEnabled(), true);

	appMainWindow.userPerformsPauseTimer();

	assertEquals(appMainWindow.getStartButtonModel().isEnabled(), true);
	assertEquals(appMainWindow.getPauseButtonModel().isEnabled(), false);
	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), false);

	appMainWindow.userPerformsStartTimer();

	assertEquals(appMainWindow.getStartButtonModel().isEnabled(), false);
	assertEquals(appMainWindow.getPauseButtonModel().isEnabled(), true);
	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), true);
    }

    public void testStartTimerFromMainWindowThenStopAndStartTimerFromTrayIcon() {
	_appTrayIcon.doToggleWindows();

	AppMainWindow appMainWindow = _appRoot.getAppMainWindow();
	assertEquals(appMainWindow.getStartButtonModel().isEnabled(), true);
	assertEquals(appMainWindow.getPauseButtonModel().isEnabled(), false);

	appMainWindow.userPerformsStartTimer();

	assertEquals(appMainWindow.getStartButtonModel().isEnabled(), false);
	assertEquals(appMainWindow.getPauseButtonModel().isEnabled(), true);
	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), true);

	_appTrayIcon.doToggleTimer();

	assertEquals(appMainWindow.getStartButtonModel().isEnabled(), true);
	assertEquals(appMainWindow.getPauseButtonModel().isEnabled(), false);
	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), false);

	_appTrayIcon.doToggleTimer();

	assertEquals(appMainWindow.getStartButtonModel().isEnabled(), false);
	assertEquals(appMainWindow.getPauseButtonModel().isEnabled(), true);
	assertEquals(_appTrayIcon.getIsRunningModel().isEnabled(), true);
    }

    public void testShowWindowThenImmediatelyExit() {
	_appTrayIcon.doToggleWindows();

	assertEquals(_appRoot.areWindowsVisible(), true);
	verify(_mockUIMainWindow).setVisibility(true);
	verify(_mockUITrayIcon).setWindowsVisible(true);
    }

    public void testShowThenHideThenShowThenExitWindow() {
	_appTrayIcon.doToggleWindows();

	assertEquals(_appRoot.areWindowsVisible(), true);
	verify(_mockUIMainWindow).setVisibility(true);
	verify(_mockUITrayIcon).setWindowsVisible(true);

	_appTrayIcon.doToggleWindows();

	assertEquals(_appRoot.areWindowsVisible(), false);
	verify(_mockUIMainWindow).setVisibility(false);
	verify(_mockUITrayIcon).setWindowsVisible(false);

	_appTrayIcon.doToggleWindows();

	AppMainWindow appMainWindow = _appRoot.getAppMainWindow();
	assertEquals(_appRoot.areWindowsVisible(), true);
	verify(_mockUIMainWindow, times(2)).setVisibility(true);
	verify(_mockUITrayIcon, times(2)).setWindowsVisible(true);

	appMainWindow.userPerformedMinimize();

	assertEquals(_appRoot.areWindowsVisible(), false);
	verify(_mockUIMainWindow, times(2)).setVisibility(false);
	verify(_mockUITrayIcon, times(2)).setWindowsVisible(false);
    }

    public void testShowCurrentActivityInWindow() {
	_appTrayIcon.doToggleWindows();

	AppMainWindow appMainWindow = _appRoot.getAppMainWindow();
	PropertyChangeListener _mockPCL = mock(PropertyChangeListener.class);
	appMainWindow.getStartTimeModel().addPropertyChangeListener(_mockPCL);
	assertEquals(null, appMainWindow.getStartTimeModel().getValue());

	Date date1 = new Date("27 dec 2008 09:00");
	when(_mockUIRoot.getTime()).thenReturn(date1);

	appMainWindow.userPerformsStartTimer();

	verify(_mockPCL).propertyChange(isA(PropertyChangeEvent.class));
	assertEquals(date1, appMainWindow.getStartTimeModel().getValue());

	Date date2 = new Date(System.currentTimeMillis()); // new Date("27 dec 2008 09:15");
	when(_mockUIRoot.getTime()).thenReturn(date2);

	appMainWindow.userPerformsPauseTimer();

	assertEquals(date2, appMainWindow.getStartTimeModel().getValue());
	verify(_mockPCL, times(2)).propertyChange(isA(PropertyChangeEvent.class));
    }
}
