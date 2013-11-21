package com.mauricelam.Savier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 2:16 AM
 */
public class GoalGridAdapter extends BaseAdapter implements Observer {
    private Context context;
    private GoalList list;

    public GoalGridAdapter(Context context) {
        this.context = context;
        this.list = GoalList.instance(context);
        this.list.addWeakObserver(this);
    }

    public GoalList getList() {
        return list;
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

    @Override
    public void update(Observable observable, Object list) {
        Log.e("Savier adapter", "Dataset changed");
        this.notifyDataSetChanged();
    }
}