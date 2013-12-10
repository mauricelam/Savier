package com.mauricelam.Savier;



import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Serializable {
	String Line1;
	String Line2;
	String City;
	String State;
	String Zipcode;
	
	Address()
	{
		Line1 = "Street Address";
		Line2 = "Apt/Suite";
		City = "City";
		State = "State";
		Zipcode = "00000";
	}
	Address(String a, String b, String c, String d, String e)
	{
		Line1 = a;
		Line2 = b;
		City = c;
		State = d;
		Zipcode = e;
	}

	
}
