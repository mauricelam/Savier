package com.mauricelam.Savier;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 2:16 AM
 */
public class GoalGridAdapter extends BaseAdapter {
    private Context mContext;

    public GoalGridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView goalView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            goalView = new GoalView(mContext);
            goalView.setLayoutParams(new GridView.LayoutParams(85, 85));
            goalView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            goalView.setPadding(8, 8, 8, 8);
        } else {
            goalView = (GoalView) convertView;
        }

        goalView.setImageResource(mThumbIds[position]);
        return goalView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.dummy_item, R.drawable.dummy_item,
            R.drawable.dummy_item, R.drawable.dummy_item,
            R.drawable.dummy_item, R.drawable.dummy_item
    };
}