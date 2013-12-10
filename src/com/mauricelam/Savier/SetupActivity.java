package com.mauricelam.Savier;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SetupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_activity);
		
		openStartupPage();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup, menu);
		return true;
		
	}
	
	void openStartupPage()
	{
		Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
	}
	
	void openPersonalInfoPage()
	{
		
	}
	void openShippingAddressPage()
	{
		
	}
	void openCheckingCardPage()
	{
		
	}
	void openVerifyPage()
	{
		
	}

}
