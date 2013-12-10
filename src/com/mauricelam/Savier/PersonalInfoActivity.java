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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class PersonalInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		ProgressBar pb1 = (ProgressBar)findViewById(R.id.progressBar1);
		pb1.setMax(100);
		pb1.setProgress(5);
		Intent intent = getIntent();
		final EditText fNameField = (EditText)findViewById(R.id.edit_first_name);
		fNameField.requestFocus();
		byte[] passedData = intent.getByteArrayExtra("USER_DATA");
		final UserData user = deserializeData(passedData);
		final Button submit = (Button) findViewById(R.id.submit_per_info_button);
        final CheckBox billingAddr = (CheckBox) findViewById(R.id.billing_checkbox);
		submit.setOnClickListener(new OnClickListener() {
			
            public void onClick(View v) {
            	
            	final EditText fName = (EditText)findViewById(R.id.edit_first_name);
        		final EditText lName = (EditText)findViewById(R.id.edit_last_name);
        		final EditText email = (EditText)findViewById(R.id.edit_email_address);
        		final EditText addr1 = (EditText)findViewById(R.id.edit_shipping_address1);
        		final EditText addr2 = (EditText)findViewById(R.id.edit_shipping_address2);
        		final EditText city = (EditText)findViewById(R.id.edit_city);
        		final EditText state = (EditText)findViewById(R.id.edit_state);
        		final EditText zip = (EditText)findViewById(R.id.edit_zip);
        		
        		System.out.println(fName);
        		Address shipAddr = null;
        		try{
        			Log.e(ACTIVITY_SERVICE, addr1.toString());
        			Log.e(ACTIVITY_SERVICE, addr1.getText().toString());
        		 shipAddr = new Address(addr1.getText().toString(),addr2.getText().toString(), city.getText().toString(), state.getText().toString(), zip.getText().toString() );
        		}
        		catch(Exception e)
        		{
        			shipAddr = new Address();
        			e.printStackTrace();
        		}
        		final CheckBox billingAddr = (CheckBox) findViewById(R.id.billing_checkbox);
        		if(billingAddr.isChecked())
        		{
        			user.setData(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), shipAddr, shipAddr);
        		}
        		else
        		{
        			user.setData(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), shipAddr);
        		}
            	if(billingAddr.isChecked())
            		openGetCardActivity(user);
            	else
            		openGetBillingAddrActivity(user);
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
	
	private void openGetCardActivity(UserData user)
	{
		Intent intent = new Intent(this, CardActivity.class);
		byte[] serialUserData = serializeData(user);
		Bundle b = new Bundle();
		intent.putExtra("USER_DATA", serialUserData);
		b.putSerializable("US", user);
		intent.putExtras(b);
		startActivity(intent);
        
	}
	private void openGetBillingAddrActivity(UserData user)
	{
		Intent intent = new Intent(this, BillingAddrActivity.class);
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
		getMenuInflater().inflate(R.menu.personal_info, menu);
		return true;
	}

}
