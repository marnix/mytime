package mytime.ui.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mytime.app.AppMainWindow;
import mytime.app.IUIMainWindow;

/**
 * The UI for the main window.
 */
@SuppressWarnings("serial")
public class SwingUIMainWindow extends JFrame implements IUIMainWindow {

    private AppMainWindow _appMainWindow;
    private JButton _jStartButton;
    private JButton _jPauseButton;

    /**
     * Create and show the main window.
     * 
     * @param appMainWindow the application facade for this main window
     */
    public SwingUIMainWindow(AppMainWindow appMainWindow) {
	super("MyTime");
	_appMainWindow = appMainWindow;

	createComponents();
	addEventListeners();
	connectModels();
	showWindow();
    }

    private void createComponents() {
	// TODO: Make sure that the icon image of the window is the same as the tray icon image, probably by moving knowledge about
	// the icon to SwingUIRoot.

	JPanel pane = new JPanel();
	getContentPane().add(pane);

	_jStartButton = new JButton(new ImageIcon(getClass().getResource("icons/aesthetica/play.png")));
	pane.add(_jStartButton);
	_jStartButton.setEnabled(false);

	_jPauseButton = new JButton(new ImageIcon(getClass().getResource("icons/aesthetica/pause.png")));
	pane.add(_jPauseButton);

	pack();
    }

    private void addEventListeners() {
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		_appMainWindow.userAttemptedClose();
	    }

	    @Override
	    public void windowIconified(WindowEvent e) {
		_appMainWindow.userPerformedMinimize();
	    }
	});
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	_jStartButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		_appMainWindow.userPerformsStartTimer();
	    }
	});
	_jPauseButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		_appMainWindow.userPerformsPauseTimer();
	    }
	});
    }

    private void connectModels() {
	_jStartButton.setModel(_appMainWindow.getStartButtonModel());
	_jPauseButton.setModel(_appMainWindow.getPauseButtonModel());
    }

    private void showWindow() {
	// TODO: Remember the window position and size from a previous run, and move the window to the same place it was.
	// This can be stored through the Preferences class.
	moveToLowerRightHandCorner();
    }

    private void moveToLowerRightHandCorner() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/*
	 * We subtract an arbitrary number of pixels, to make sure that we're clear of any toolbar and such.
	 */
	setLocation(screenSize.width - getWidth() - 64, screenSize.height - getHeight() - 64);
    }

    /**
     * Change the visibility for this main window
     * 
     * @param isVisible true or false
     * 
     */
    // TODO: Try to get rid of this method, and replace it by listening to a model.
    public void setVisibility(boolean isVisible) {
	setVisible(isVisible);
	if (isVisible) {
	    setState(NORMAL);
	    toFront();
	}
    }

    /**
     * Destroy this main window. After this, this object may not be used anymore
     * 
     */
    public void destroy() {
	dispose();
	_appMainWindow = null; // just to make sure we don't use it anymore
    }

}
