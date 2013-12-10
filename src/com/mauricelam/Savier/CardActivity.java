package com.mauricelam.Savier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		ProgressBar pb1 = (ProgressBar)findViewById(R.id.progressBar3);
		pb1.setMax(100);
		pb1.setProgress(67);
		Intent intent = getIntent();
		byte[] passedData = intent.getByteArrayExtra("USER_DATA");
		final UserData user = deserializeData(passedData);
		UserData user1 = null;
		Bundle b = this.getIntent().getExtras();
		RelativeLayout cardLayout = (RelativeLayout)findViewById(R.id.card_view);
		if(b!=null)
		    user1 = (UserData) b.getSerializable("US");
		
		/*TextView myText = new TextView(null);
		try{
			
			myText.setText(user1.fName);
			
		}
		catch(NullPointerException e)
		{
			myText.setText("Problem");
		}
		cardLayout.addView(myText);*/
		
		TextView tv = (TextView)findViewById(R.id.edit_name);
		if(user1.fName.length()>1)
			tv.setText((user1.fName+" "+user1.lName).toUpperCase());
		
		final UserData user2=user1;
		final Button submit = (Button) findViewById(R.id.submit_card_button);
    
	submit.setOnClickListener(new OnClickListener() {

        public void onClick(View v) {
        		
        		final EditText cardNum = (EditText)findViewById(R.id.edit_card_number);
        		final EditText cardName = (EditText)findViewById(R.id.edit_name);
        		final EditText expMonth = (EditText)findViewById(R.id.edit_month);
        		final EditText expYear = (EditText)findViewById(R.id.edit_year);
        		final EditText cvv = (EditText)findViewById(R.id.edit_cvv);
        		final String name;
        		Card CardDetails = null;
        		
        			name = cardName.getText().toString();
        		
        		try{
        			//Log.e(ACTIVITY_SERVICE, name);
        			//Log.e(ACTIVITY_SERVICE, String.valueOf(cvv.getText().toString()));
        			CardDetails = new Card(cardNum.getText().toString(),name, expMonth.getText().toString(), expYear.getText().toString(), cvv.getText().toString() );
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        			CardDetails = new Card();
        		}
        		
				user2.addCard(CardDetails);
        		openConfirmActivity(user2);
        	
        }
        });
	}

	private byte[] serializeData(UserData user)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] yourBytes = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(user);
		  yourBytes = bos.toByteArray();
		  
		} finally {
		  try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return yourBytes;
		}
	}
	
	private UserData deserializeData(byte[] data)
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInput in = null;
		UserData ud=null;
		try {
		  in = new ObjectInputStream(bis);
		  ud = (UserData) in.readObject(); 
		  
		} finally {
		  try {
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return ud;
		}
	}
	private void openConfirmActivity(UserData user)
	{
		Intent intent = new Intent(this, SetupConfirmActivity.class);
		byte[] serialUserData = serializeData(user);
		Bundle b = new Bundle();
		intent.putExtra("USER_DATA", serialUserData);
		b.putSerializable("US", user);
		intent.putExtras(b);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.card, menu);
		return true;
	}

}
