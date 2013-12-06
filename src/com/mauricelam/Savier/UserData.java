package com.mauricelam.Savier;



import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {
	String fName;
	String lName;
	Address ShippingAddress;
	Address BillingAddress;
	Card CheckingCard;
	
	UserData()
	{
		fName = "First Name";
		lName = "Last Name";
		ShippingAddress = new Address();
		BillingAddress = new Address();
		CheckingCard = new Card(fName, lName);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(fName);
		dest.writeString(lName);
		//dest.write
		
	}
	
	
}
