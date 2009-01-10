package mytime.app.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * A simple implementation of Document, wrapped around a given IValueModel.
 * 
 * @param <T> The type of the underlying IValueModel.
 */
@SuppressWarnings("serial")
public class DocumentModel<T> extends PlainDocument {

    /**
     * Create an instance, wrapped around the given value model.
     * 
     * @param valueModel the value model to use
     */
    public DocumentModel(final IValueModel<T> valueModel) {
	valueModel.addPropertyChangeListener(new PropertyChangeListener() {
	    public void propertyChange(PropertyChangeEvent event) {
		try {
		    replace(0, getLength(), valueModel.getValue().toString(), null);
		} catch (BadLocationException e) {
		    // cannot happen
		}
	    }
	});
    }
}
