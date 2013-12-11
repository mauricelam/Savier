package com.mauricelam.Savier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;

/**
 * User: mauricelam
 * Date: 19/11/13
 * Time: 1:39 PM
 */
public class GoalList extends WeakObservable {

    private static final Type GOAL_LIST_TYPE = new TypeToken<ArrayList<Goal>>() {}.getType();
    private static GoalList instance;

    private ArrayList<Goal> list;
    private Context context;

    public static GoalList instance(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            instance = newInstance(context);
        }
        return instance;
    }

    private static GoalList newInstance(Context context) {
        Storage storage = new Storage(context, "goals");
        GoalList goalList = new GoalList(context);
        try {
            goalList.list = (ArrayList<Goal>) storage.getObject("goals", GOAL_LIST_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (goalList.list == null) {
            goalList.list = new ArrayList<Goal>();
        }
        return goalList;
    }

    protected GoalList(Context context) {
        this.context = context;
    }

    public boolean add(Goal goal) {
        if (list.add(goal)) {
            this.onListChange();
            return true;
        }
        return false;
    }

    public boolean remove(Goal goal) {
        if (list.remove(goal)) {
            this.onListChange();
            return true;
        }
        return false;
    }

    public int size() {
        return list.size();
    }

    public Goal get(int index) {
        return list.get(index);
    }

    public Goal get(String id) {
        for (Goal goal : list) {
            if (id.equals(goal.getID())) {
                return goal;
            }
        }
        return null;
    }

    // Should be called after modifying any goals
    public void commitChanges() {
        this.onListChange();
    }

    private void onListChange() {
        SharedPreferences prefs = context.getSharedPreferences("goals", Context.MODE_PRIVATE);
        Storage storage = new Storage(prefs);
        storage.putObject("goals", list, GOAL_LIST_TYPE);
        Log.d("Savier list", "Dataset changed");
        this.setChanged();
        this.notifyObservers();
    }

}
