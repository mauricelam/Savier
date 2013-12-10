package com.mauricelam.Savier;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SetupConfirmActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_confirm);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_confirm, menu);
		ProgressBar pBar = (ProgressBar)findViewById(R.id.progressBar4);
		pBar.setMax(100);
		pBar.setProgress(99);
		UserData user1 = null;
		Bundle b = this.getIntent().getExtras();
		if(b!=null)
		{
		    user1 = (UserData) b.getSerializable("US");
		}
		try{
		TextView name = (TextView)findViewById(R.id.confirm_name);
		name.setText(" "+ user1.fName + " " + user1.lName);
		TextView email = (TextView)findViewById(R.id.confirm_email);
		email.setText(" "+ user1.email);
		TextView shipping1 = (TextView)findViewById(R.id.confirm_shipping_1);
		shipping1.setText(user1.ShippingAddress.Line1);
		TextView shipping2 = (TextView)findViewById(R.id.confirm_shipping_2);
		shipping2.setText(user1.ShippingAddress.Line2);
		TextView shipping3 = (TextView)findViewById(R.id.confirm_shipping_3);
		shipping3.setText(user1.ShippingAddress.City + ", "+ user1.ShippingAddress.State);
		TextView shipping4 = (TextView)findViewById(R.id.confirm_shipping_4);
		shipping4.setText(user1.ShippingAddress.Zipcode);
		TextView billing1 = (TextView)findViewById(R.id.confirm_billing_1);
		billing1.setText(user1.BillingAddress.Line1);
		TextView billing2 = (TextView)findViewById(R.id.confirm_billing_2);
		billing2.setText(user1.BillingAddress.Line2);
		TextView billing3 = (TextView)findViewById(R.id.confirm_billing_3);
		billing3.setText(user1.BillingAddress.City + ", "+ user1.BillingAddress.State);
		TextView billing4 = (TextView)findViewById(R.id.confirm_billing_4);
		billing4.setText(user1.BillingAddress.Zipcode);
		TextView card1 = (TextView)findViewById(R.id.confirm_card_1);
		card1.setText(String.valueOf(user1.CheckingCard.num));
		TextView card2 = (TextView)findViewById(R.id.confirm_card_2);
		card2.setText(user1.CheckingCard.name);
		TextView card3 = (TextView)findViewById(R.id.confirm_card_3);
		card3.setText(user1.CheckingCard.ExpMonth+"/"+user1.CheckingCard.ExpYear);
		TextView card4 = (TextView)findViewById(R.id.confirm_card_4);
		card4.setText(user1.CheckingCard.cvv);
		Log.e(ACTIVITY_SERVICE, "CARD NAME: " + user1.CheckingCard.name);
		Log.e(ACTIVITY_SERVICE, "CARD CVV: " +user1.CheckingCard.cvv);
		Log.e(ACTIVITY_SERVICE, "BILLING LINE2: " +user1.BillingAddress.Line2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

}
