package com.mauricelam.Savier;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.mauricelam.Savier.GoalDetailActivity;
public class SaveActivity extends Activity {

    GoalGridAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_activity);

        adapter = new GoalGridAdapter(this);
        // Set up the grid
        GridView gridview = (GridView) findViewById(R.id.goalGrid);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GoalView goalView = (GoalView) v;
                if (goalView.isShowingDetail()) {
                    openGoalDetail(adapter.getItem(position), v, position);
                } else {
                    goalView.showDetail();
                }
            }
        });

        Button labeledAddBtn = (Button) findViewById(R.id.labeled_add_button);
        labeledAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddGoalActivity();
            }
        });

        gridview.setEmptyView(labeledAddBtn);
        gridview.setAdapter(adapter);
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
            adapter.getList().add(goal);
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
                startAddGoalActivity();
                return true;
            case R.id.open_setup_action:
                startSetupActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startAddGoalActivity() {
        Intent intent = new Intent(this, AddGoalActivity.class);
        startActivity(intent);
    }
    private void startSetupActivity() {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    private void openGoalDetail(Goal goal, View v, int position) {
    	Intent intent = new Intent(this, GoalDetailActivity.class);
       	intent.putExtra("goal", goal);
       	
        startActivity(intent);
    }

}


