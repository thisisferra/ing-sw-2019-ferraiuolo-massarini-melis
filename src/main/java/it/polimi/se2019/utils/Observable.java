package it.polimi.se2019.utils;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<Observer> observers = new ArrayList<>();
    private boolean changed = false;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public int countObservers() {
        return observers.size();
    }

    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setChanged() {
        changed = !changed;
    }

    public boolean hasChanged() {
        return changed;
    }

    public void notifyObservers(Object obj) {

        if (hasChanged()) {
            for (Observer observer : observers) {
                observer.update(this, obj);
            }
            setChanged();
        }

    }

    public void notifyObserver(Observer observer, Object obj) {

        if (hasChanged()) {
            observer.update(this, obj);
            setChanged();
        }
    }

}