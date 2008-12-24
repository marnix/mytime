package mytime.app.models;

import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
// TODO: !!! Add tests for class NegatedButtonModel
public class NegatedButtonModel extends DefaultButtonModel {

    private ButtonModel _inner = null;

    public NegatedButtonModel(ButtonModel inner) {
	_inner = inner;
	_inner.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
		// TODO: !! Is this code correct? What should I do with e???
		NegatedButtonModel.this.fireStateChanged();
	    }
	});
    }

    @Override
    public boolean isEnabled() {
	return !_inner.isEnabled();
    }

    @Override
    public void setEnabled(boolean b) {
	if (_inner != null) {
	    _inner.setEnabled(!b);
	}
    }
}
