package mytime.ui.swing;

import mytime.app.AppRoot;
import mytime.app.AppTrayIcon;
import mytime.app.IUIRoot;
import mytime.app.IUITrayIcon;

/**
 * The root of the Swing UI of the MyTime application; this is a singleton.
 * 
 */
public class SwingUIRoot implements IUIRoot {

    /**
     * @param args The command line arguments (currently unused).
     */
    public static void main(String[] args) {
	AppRoot.Start(new SwingUIRoot());
    }

    /**
     * Create and show a new {@link SwingUITrayIcon}.
     * 
     * @return the created icon
     * 
     * @see mytime.app.IUIRoot#showTrayIcon(mytime.app.AppTrayIcon)
     */
    public IUITrayIcon showTrayIcon(AppTrayIcon appTrayIcon) {
	return new SwingUITrayIcon(appTrayIcon);
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
