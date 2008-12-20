package mytime.app;

/**
 * This interface represents the UI of the tray icon, as the application facade see it.
 */
public interface IUITrayIcon {

    /**
     * Instructs the UI to set the tooltip.
     * 
     * @param tooltip the string to show to the end user.
     */
    public void setTooltip(String tooltip);

    /**
     * Instructs the UI to set the 'running' state
     * 
     * @param isRunning whether or not the icon should show the timer is running
     */
    public void setRunning(boolean isRunning);

    /**
     * Instructs the UI to destroy the tray icon. After this action, this {@link IUITrayIcon} object may not be used anymore.
     */
    public void destroy();

}
