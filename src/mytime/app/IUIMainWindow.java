package mytime.app;

/**
 * This interface represents the UI of the main window.
 */
public interface IUIMainWindow {

    /**
     * Instructs the UI to show/hide the main window.
     * 
     * @param isVisible whether the main window should be visible or not
     */
    void setVisibility(boolean isVisible);

    /**
     * Instructs the UI to destroy this main window.
     */
    void destroyMainWindow();

}
