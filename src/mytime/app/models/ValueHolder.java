package mytime.app.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ValueHolder<T> implements IValueModel<T> {
    private T _value;
    private PropertyChangeSupport propertyChangeSupport;

    public ValueHolder(T value) {
	_value = value;
    }

    /**
     * Add a PropertyChangeListener to the listener list.
     * 
     * @param listener The listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
	if (propertyChangeSupport == null) {
	    propertyChangeSupport = new PropertyChangeSupport(this);
	}
	propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     * 
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
	if (propertyChangeSupport != null) {
	    propertyChangeSupport.removePropertyChangeListener(listener);
	}
    }

    public T getValue() {
	return _value;
    }

    public void setValue(T value) {
	T oldValue = _value;
	_value = value;
	if (propertyChangeSupport != null) {
	    propertyChangeSupport.firePropertyChange("value", oldValue, value);
	}
    }
}
