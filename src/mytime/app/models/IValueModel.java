package mytime.app.models;

import java.beans.PropertyChangeListener;

public interface IValueModel<T> {
    T getValue();

    void setValue(T value);

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

}
