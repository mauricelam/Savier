package com.mauricelam.Savier;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class SaveActivity extends Activity {

    GoalGridAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_activity);

        adapter = GoalGridAdapter.restore(this);
        // Set up the grid
        GridView gridview = (GridView) findViewById(R.id.goalGrid);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                openGoalDetail(adapter.getItem(position));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.parseIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.parseIntent(intent);
    }

    private void parseIntent(Intent intent) {
        if (intent.hasExtra("add_goal")) {
            // TODO: make a real goal out of extra info
            Goal goal = AmazonGoal.fromId("blah");
            goal.setSaved((int) (Math.random() * 10000));
            adapter.add(goal);
            intent.removeExtra("add_goal");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_goal:
                Intent intent = new Intent(this, AddGoalActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openGoalDetail(Goal goal) {
        GoalDetailFragment detailFragment = GoalDetailFragment.newInstance(goal);
        FragmentManager fragmentManager = getFragmentManager();
        detailFragment.show(fragmentManager, "goal_detail_fragment");
    }

}


