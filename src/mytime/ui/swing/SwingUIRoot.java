package mytime.ui.swing;

import mytime.app.AppRoot;
import mytime.app.AppTrayIcon;
import mytime.app.IUIRoot;
import mytime.app.IUITrayIcon;

public class SwingUIRoot implements IUIRoot {

    /**
     * @param args
     */
    public static void main(String[] args) {
        AppRoot.Start(new SwingUIRoot());
    }

    public IUITrayIcon showTrayIcon(AppTrayIcon appTrayIcon) {
        return new SwingUITrayIcon(appTrayIcon);
    }

    public void exit() {
        System.exit(0);
    }

}
