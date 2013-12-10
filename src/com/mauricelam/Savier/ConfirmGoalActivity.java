package com.mauricelam.Savier;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 3:11 PM
 */
public class ConfirmGoalActivity extends Activity {
    
	private String itemTitle;
	private String itemPriceFormatted;
	private String itemImageURL;
	private String itemURL;
	private String itemDescription;
	
	TextView goalName;
	TextView goalPrice;
	TextView goalURL;
	TextView goalDescription;
	GoalView goalView;
	
	private AmazonGoal amazonGoal;
	private String customGoalName = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_add_goal);
        Bundle bundle = getIntent().getExtras();
		String productID = new String(bundle.getString("ProductID"));
		AmazonProductLookup amazonLookup = new AmazonProductLookup(productID);
		Map<String, String> productInfo = amazonLookup.lookup();
		this.itemTitle = productInfo.get("Title");
		this.itemPriceFormatted = productInfo.get("Price");
		this.itemImageURL = productInfo.get("ImageURL");
		this.itemURL = productInfo.get("URL");
		this.itemDescription = productInfo.get("Description");
		
		// TODO: Option for user to enter their desired goal name
		this.amazonGoal = customGoalName == null ? new AmazonGoal(productInfo) :
			new AmazonGoal(productInfo, customGoalName);
		
		goalName = (TextView)findViewById(R.id.name);
		goalPrice = (TextView)findViewById(R.id.savings);
		goalURL = (TextView)findViewById(R.id.amazonlink);
		goalDescription = (TextView)findViewById(R.id.description);
		goalView = (GoalView)findViewById(R.id.goalview);
				
		goalName.setText(itemTitle);
		goalPrice.setText(itemPriceFormatted);
		goalURL.setText(itemURL);
		goalDescription.setText(itemDescription);
		goalView.setGoal(amazonGoal);

		
		Log.d("New Goal Title", itemTitle);
		Log.d("New Goal Description", itemDescription);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.confirm_goal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_confirm_goal:
                Intent intent = new Intent(this, SaveActivity.class);
                intent.putExtra("newAmazonGoal", amazonGoal);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
