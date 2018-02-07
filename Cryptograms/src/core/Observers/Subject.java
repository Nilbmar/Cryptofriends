package core.Observers;

/**
 * Created by sysgeek on 2/07/18.
 *
 * Purpose: Interface for observers
 */

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}
