package com.mauricelam.Savier;

import java.util.*;

/**
 * User: mauricelam
 * Date: 20/11/13
 * Time: 6:16 PM
 */
public class WeakObservable extends Observable {

    private transient Set<Observer> references;

    public WeakObservable() {
        WeakHashMap<Observer, Boolean> map = new WeakHashMap<Observer, Boolean>();
        references = Collections.newSetFromMap(map);
    }

    public void addWeakObserver(Observer o) {
        references.add(o);
    }

    public void addObserver(Observer o) {
        super.addObserver(o);
    }

    public int countObservers() {
        return references.size() + super.countObservers();
    }

    public void deleteObserver(Observer o) {
        super.deleteObserver(o);
        references.remove(o);
    }

    public void deleteObservers() {
        super.deleteObservers();
        references.clear();
    }

    public void	notifyObservers() {
        super.notifyObservers();
        for (Observer o : references) {
            o.update(this, null);
        }
    }

    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
        for (Observer o : references) {
            o.update(this, arg);
        }
    }

}
