package com.mauricelam.Savier;

import android.util.Log;

import java.io.Serializable;
import java.util.UUID;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:42 PM
 */
public abstract class Goal extends WeakObservable implements Serializable {

    // All money values should be in terms of cents
    private double target;
    private int saved = 0;
    private String name;
    private String url;
    private String description;

    private String id;

    protected Goal() {
        // no-arg constructor for GSON
        super();
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public Goal(String name, double target2, String url, String description) {
        super();
        Log.d("Savier goal", "construct");
        this.target = target2;
        this.name = name;
        this.url = url;
        this.id = UUID.randomUUID().toString();
        this.description = description;
    }

    public abstract String getImageURL();

    public void setSaved(int saved) {
        this.saved = saved;
        this.notifyObservers();
    }

    public int getSaved() {
        return saved;
    }

    public double getTarget() {
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
        this.setChanged();
        this.notifyObservers();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getDescription() {
		return description;
	}
	public void setDescription(String description){
		this.description = description;
		this.setChanged();
	    this.notifyObservers();
	}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Goal))
            return false;
        Goal other = (Goal) o;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
