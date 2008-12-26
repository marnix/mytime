package mytime.ui.swing;

import mytime.app.AppMainWindow;
import mytime.app.AppRoot;
import mytime.app.AppTrayIcon;
import mytime.app.IUIMainWindow;
import mytime.app.IUIRoot;
import mytime.app.IUITrayIcon;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * The root of the Swing UI of the MyTime application; this is a singleton.
 * 
 */
public class SwingUIRoot implements IUIRoot {

    /**
     * @param args The command line arguments (currently unused).
     */
    public static void main(String[] args) {
	SwingUIRoot uiRoot = new SwingUIRoot();
	AppRoot appRoot = AppRoot.Start(uiRoot);
	uiRoot.setAppRoot(appRoot);
    }

    AppRoot _appRoot;

    public SwingUIRoot() {
	registerGlobalHotKey();
    }

    private void setAppRoot(AppRoot appRoot) {
	_appRoot = appRoot;
    }

    private void registerGlobalHotKey() {
	if (JIntellitype.isJIntellitypeSupported()) {
	    JIntellitype j = JIntellitype.getInstance();
	    final int ID_HOTKEY = 1;
	    j.registerHotKey(ID_HOTKEY, "WIN+T");
	    j.addHotKeyListener(new HotkeyListener() {
		public void onHotKey(int id) {
		    assert id == ID_HOTKEY;
		    _appRoot.doActivate();
		}
	    });
	}
    }

    /**
     * Create and show a new {@link SwingUITrayIcon}.
     * 
     * @param appTrayIcon the application facade for the tray icon
     * @param isRunning the initial running/stopped state of the icon
     * @return the created icon
     * 
     * @see mytime.app.IUIRoot#showTrayIcon(mytime.app.AppTrayIcon,boolean)
     */
    public IUITrayIcon showTrayIcon(AppTrayIcon appTrayIcon, boolean isRunning) {
	return new SwingUITrayIcon(appTrayIcon, isRunning);
    }

    /**
     * Create and show a new {@link SwingUIMainWindow}.
     * 
     * @param appMainWindow the application facade for the main window
     * @return the created window
     * 
     */
    public IUIMainWindow showMainWindow(AppMainWindow appMainWindow) {
	return new SwingUIMainWindow(appMainWindow);
    }

    /**
     * Exit the JVM.
     * 
     * @see mytime.app.IUIRoot#exit()
     */
    public void exit() {
	System.exit(0);
    }

}
