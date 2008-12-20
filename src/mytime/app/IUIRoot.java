package mytime.app;

/**
 * This interface represents the UI of the application as a whole, as the application facade see it.
 */
public interface IUIRoot {

    /**
     * Instructs the UI to show the tray icon, and let it call back to the {@link AppTrayIcon} on user interaction.
     * 
     * @param trayIcon the application facade for the tray icon
     * @param isRunning whether the tray icon should display the 'running' state or not
     * @return the tray icon
     */
    IUITrayIcon showTrayIcon(AppTrayIcon trayIcon, boolean isRunning);

    /**
     * Instructs the UI to exit.
     */
    void exit();

}
