package com.mauricelam.Savier;



import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
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

		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeIntArray(num);
			dest.writeIntArray(ExpMonth);
			dest.writeIntArray(ExpYear);
			dest.writeString(fName);
			dest.writeString(lName);
			dest.writeIntArray(cvv);
			
		}
}
