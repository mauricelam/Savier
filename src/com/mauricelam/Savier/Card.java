package com.mauricelam.Savier;



import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Serializable {
		String num;
		int ExpMonth;
		int ExpYear;
		String name;
		String cvv;
		
		Card()
		{
			num = "0";
			ExpMonth = 0;
			ExpYear = 0;
			this.name = " ";
			cvv = "0";
		}
		Card(String number,  String nameString, String ExpMonth, String ExpYear, String cvv)
		{
			num = number;
			this.ExpMonth = Integer.parseInt(ExpMonth);
			this.ExpYear = Integer.parseInt(ExpYear);
			this.name = nameString;
			this.cvv = cvv;
		}
		

}
