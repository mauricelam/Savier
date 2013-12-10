package com.mauricelam.Savier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 2:35 PM
 */
public class AddGoalActivity extends Activity {
    private boolean enableAddGoal = false;

    // TODO: show a loading indicator while the web page is loading (instead of blank page)
    private WebView webView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.add_goal);

        // FIXME: Should be set to enabled only if we detect Amazon item ID
        
        //setAddGoalEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new AmazonWebViewClient());
        webView.loadUrl("https://www.amazon.com");
    }

    private class AmazonWebViewClient extends WebViewClient {
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView webview, String url) {
    		webview.loadUrl(url);
    		setProgressBarIndeterminateVisibility(true);
			Log.d("currURL", url);
    		return true;
    	}
    	
    	@Override
    	public void onPageFinished(WebView webview, String url) {
    		super.onPageFinished(webview, url);
    		setProgressBarIndeterminateVisibility(false);
    		if (url.indexOf("gp/aw/d/") != -1) {
    			Log.d("currURL", url);
    			setAddGoalEnabled(true);
    		} else {
    			setAddGoalEnabled(false);
    		}
    	}
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Savier add goal", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Savier add goal", "destroy");
    }

    private void setAddGoalEnabled(boolean enabled) {
        this.enableAddGoal = enabled;
        if (enabled) {
        	Toast.makeText(getBaseContext(), "You can add a goal now.", Toast.LENGTH_LONG).show();
        } else {
        	// ...
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_goal_menu, menu);
        menu.findItem(R.id.action_select_goal).setEnabled(this.enableAddGoal);

        return super.onCreateOptionsMenu(menu);
    }

    private String parseAmazonProductID(String url) {
    	int idStartIndex = url.indexOf("aw/d") + 5;
    	int idLength = 10;
    	return url.substring(idStartIndex, idStartIndex + idLength);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_select_goal:
                if (this.enableAddGoal) {
                	Intent intent = new Intent(this, ConfirmGoalActivity.class);
                	String currURL = webView.getUrl();
                	Log.d("ProductID", parseAmazonProductID(currURL));
                    intent.putExtra("ProductID", parseAmazonProductID(currURL));
                    startActivity(intent);
                    return true;
                } else {
                	Toast.makeText(getBaseContext(), "You have to go to a product page.", Toast.LENGTH_LONG).show();
                }
            	
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}