package com.mauricelam.Savier;

import android.util.Log;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.UUID;

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
    private String desc;

    private String id;

    protected Goal() {
        // no-arg constructor for GSON
        super();
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public Goal(String name, int target, String url, String desc) {
        super();
        Log.d("Savier goal", "construct");
        this.target = target;
        this.name = name;
        this.url = url;
        this.id = UUID.randomUUID().toString();
        this.desc = desc;
    }

    public Goal(String name, double target, String url, String desc) {
        this(name, (int) (target * 100), url, desc);
    }

    public abstract String getImageURL();

    public void setSaved(int saved) {
        this.saved = saved;
        this.notifyObservers();
    }

    public int getSaved() {
        return saved;
    }

    public String getSavedString() {
        return NumberFormat.getCurrencyInstance().format(this.getSaved() / 100.0);
    }

    public int getTarget() {
        return target;
    }

    public String getTargetString() {
        return NumberFormat.getCurrencyInstance().format(this.getTarget() / 100.0);
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

    public String getURL() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc){
		this.desc = desc;
		this.setChanged();
	    this.notifyObservers();
	}

    public String getID() {
        return this.id;
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
