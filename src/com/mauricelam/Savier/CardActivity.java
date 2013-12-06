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
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
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
		
		TextView tv = (TextView)findViewById(R.id.card_text);
		tv.setText(user1.fName);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.card, menu);
		return true;
	}

}
