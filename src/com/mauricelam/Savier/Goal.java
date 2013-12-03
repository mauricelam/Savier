package com.mauricelam.Savier;

import android.util.Log;

import java.io.Serializable;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:42 PM
 */
public abstract class Goal extends WeakObservable implements Serializable {

    // All money values should be in terms of cents
    private int target;
    private int saved = 0;
    private String name;
    private String url;

    protected Goal() {
        // no-arg constructor for GSON
        super();
    }

    public Goal(String name, int target, String url) {
        super();
        Log.d("Savier goal", "construct");
        this.target = target;
        this.name = name;
        this.url = url;
    }

    public abstract String getImageURL();

    public void setSaved(int saved) {
        this.saved = saved;
        this.notifyObservers();
    }

    public int getSaved() {
        return saved;
    }

    public int getTarget() {
        return target;
    }

    public double getPercentage() {
        return saved / (double) target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
