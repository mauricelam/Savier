package com.mauricelam.Savier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 2:16 AM
 */
public class GoalGridAdapter extends BaseAdapter {
    private static final Type GOAL_LIST_TYPE = new TypeToken<ArrayList<Goal>>() {}.getType();
    private Context context;
    private ArrayList<Goal> list;

    public GoalGridAdapter(Context context) {
        this.context = context;
        list = new ArrayList<Goal>();
    }

    public GoalGridAdapter(Context context, ArrayList<Goal> list) {
        this.context = context;
        this.list = list;
    }

    public void add(Goal goal) {
        list.add(goal);
        SharedPreferences prefs = context.getSharedPreferences("goals", Context.MODE_PRIVATE);
        Storage storage = new Storage(prefs);
        storage.putObject("goals", list, GOAL_LIST_TYPE);
    }

    public static GoalGridAdapter restore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("goals", Context.MODE_PRIVATE);
        Storage storage = new Storage(prefs);
        try {
            ArrayList<Goal> list = (ArrayList<Goal>) storage.getObject("goals", GOAL_LIST_TYPE);
            return new GoalGridAdapter(context, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new GoalGridAdapter(context);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Goal getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        GoalView goalView;
        Goal goal = list.get(position);
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            goalView = (GoalView) inflater.inflate(R.layout.goal_item, parent, false);
        } else {
            goalView = (GoalView) convertView;
        }

        goalView.setGoal(goal);
        return goalView;
    }

}