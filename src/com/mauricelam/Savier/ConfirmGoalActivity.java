package com.mauricelam.Savier;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
                intent.putExtra("add_goal", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}