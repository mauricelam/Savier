package com.mauricelam.Savier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class BillingAddrActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_billing_addr);
		ProgressBar pb1 = (ProgressBar)findViewById(R.id.progressBar2);
		pb1.setMax(100);
		pb1.setProgress(40);
		Intent intent = getIntent();
		byte[] passedData = intent.getByteArrayExtra("USER_DATA");
		final UserData user = deserializeData(passedData);
		final Button submit = (Button) findViewById(R.id.submit_billing_info_button);
        
		submit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
            		fetchData(user);
            		openGetCardActivity(user);
            	
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
		intent.putExtra("USER_DATA", serialUserData);
        startActivity(intent);
	}
	
	private void fetchData(UserData user)
	{
		final EditText addr1 = (EditText)findViewById(R.id.edit_shipping_address1);
		final EditText addr2 = (EditText)findViewById(R.id.edit_shipping_address2);
		final EditText city = (EditText)findViewById(R.id.edit_city1);
		final EditText state = (EditText)findViewById(R.id.edit_state1);
		final EditText zip = (EditText)findViewById(R.id.edit_zip1);
		Address billAddr = new Address(addr1.getText().toString(),addr2.getText().toString(), city.getText().toString(), state.getText().toString(), zip.getText().toString() );
		user.setBillingAddr(billAddr);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.billing_addr, menu);
		return true;
	}

}
