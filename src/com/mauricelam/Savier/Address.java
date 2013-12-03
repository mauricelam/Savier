package com.mauricelam.Savier;



import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
	String Line1;
	String Line2;
	String City;
	String State;
	int[] Zipcode;
	
	Address()
	{
		Line1 = "Street Address";
		Line2 = "Apt/Suite";
		City = "City";
		State = "State";
		Zipcode = new int[5];
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Line1);
		dest.writeString(Line2);
		dest.writeString(City);
		dest.writeString(State);
		dest.writeIntArray(Zipcode);
	}
}
