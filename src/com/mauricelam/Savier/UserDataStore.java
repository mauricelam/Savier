package com.mauricelam.Savier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;

public class UserDataStore extends WeakObservable{

	private static final Type USER_DATA_TYPE = new TypeToken<UserData>(){}.getType();
    private static UserDataStore instance;
	
    private UserData user;
    private Context context;
    
    public void add(UserData uData)
    {
    	user = uData;       
    	this.onListChange();
             
    }
    
    private void onListChange() {
        SharedPreferences prefs = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        Storage storage = new Storage(prefs);
        storage.putObject("userdata", user, USER_DATA_TYPE);
        this.setChanged();
    }
}
