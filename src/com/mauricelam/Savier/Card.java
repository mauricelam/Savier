package com.mauricelam.Savier;



import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Serializable {
		int[] num;
		int[] ExpMonth;
		int[] ExpYear;
		String fName;
		String lName;
		int[] cvv;
		
		Card(String fn, String ln)
		{
			num = new int[16];
			ExpMonth = new int[2];
			ExpYear = new int[2];
			fName = fn;
			lName = ln;
			cvv = new int[3];
		}

}
