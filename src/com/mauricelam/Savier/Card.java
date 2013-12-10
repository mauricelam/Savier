package com.mauricelam.Savier;



import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Serializable {
		long num;
		int ExpMonth;
		int ExpYear;
		String name;
		int cvv;
		
		Card()
		{
			num = 0;
			ExpMonth = 0;
			ExpYear = 0;
			this.name = " ";
			cvv = 0;
		}
		Card(String number,  String nameString, String ExpMonth, String ExpYear, String cvv)
		{
			num = Long.parseLong(number);
			this.ExpMonth = Integer.parseInt(ExpMonth);
			this.ExpYear = Integer.parseInt(ExpYear);
			this.name = name;
			this.cvv = Integer.parseInt(cvv);
		}
		

}
