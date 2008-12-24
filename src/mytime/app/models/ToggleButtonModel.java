package mytime.app.models;

import javax.swing.DefaultButtonModel;

@SuppressWarnings("serial")
public class ToggleButtonModel extends DefaultButtonModel {

    /** Initially the state is 'disabled'. */
    public ToggleButtonModel() {
	setEnabled(false);
    }

    public void toggle() {
	setEnabled(!isEnabled());
    }
}
