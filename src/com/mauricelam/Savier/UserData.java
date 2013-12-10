package com.mauricelam.Savier;



import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Serializable {
	String fName;
	String lName;
	String email;
	Address ShippingAddress;
	Address BillingAddress;
	Card CheckingCard;
	
	UserData()
	{
		fName = "First Name";
		lName = "Last Name";
		email = "abc@xyz.com";
		ShippingAddress = new Address();
		BillingAddress = new Address();
		CheckingCard = new Card();
	}
	
	public void setData(String a, String b, String c)
	{
		fName = a;
		lName = b;
		email = c;
	}

    public void setData(String a, String b, String c, Address d)
    {
        fName = a;
        lName = b;
        email = c;
        ShippingAddress = d;
    }

	public void setData(String a, String b, String c, Address d, Address e)
	{
		fName = a;
		lName = b;
		email = c;
		ShippingAddress = d;
		BillingAddress = e;
		
	}
	public void addCard(Card card)
	{
		CheckingCard = card;
	}
	
	public void setBillingAddr(Address a)
	{
		BillingAddress = a;
	}
	
	public void setCheckingCard(Card a)
	{
		CheckingCard = a;
	}
	public String getfName()
	{
		return fName;
	}
	public String getlName()
	{
		return lName;
	}
	public String getEmail()
	{
		return email;
	}
	
	
	
	
}
