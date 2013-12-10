package com.mauricelam.Savier;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GoalHistoryFragment extends ListFragment {
	private Goal goal;
	public GoalHistoryFragment() {
		// TODO Auto-generated constructor stub
	}
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    String[] dates = new String[] { "Jan 21", "Jan 22", "Jan 23",
	        "Jan 24", "Jan 25" };
	    String[] money = new String[] { "$1", "$2", "$3", "$4", "$5"};
	    
	    MoneyArrayAdapter adapter = new MoneyArrayAdapter(getActivity(), dates, money);
	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
		  String selectedValue = (String) getListAdapter().getItem(position);
			Toast.makeText(getActivity(), selectedValue, Toast.LENGTH_SHORT).show();
//		  Toast.makeText(getActivity(),
//			      "Click ListItem Number " + position, Toast.LENGTH_LONG)
//			      .show();

	  }

	  public static GoalHistoryFragment newInstance(Goal goal) {
		 	GoalHistoryFragment fragment = new GoalHistoryFragment();
	        Bundle args = new Bundle();
	        args.putSerializable("goal", goal);
	        fragment.setArguments(args);

	        return fragment;
	    }
	  public class MoneyArrayAdapter extends ArrayAdapter<String> {
			private final Context context;
			private final String[] dates;
			private final String[] money;
		 
			public MoneyArrayAdapter(Context context, String[] dates, String[] money) {
				super(context, R.layout.goal_history_fragment, dates);
				this.context = context;
				this.dates = dates;
				this.money = money;
			}
		 
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
				View rowView = inflater.inflate(R.layout.goal_history_fragment, parent, false);
				TextView textView = (TextView) rowView.findViewById(R.id.text1);
				TextView moneyView = (TextView) rowView.findViewById(R.id.text2);
				textView.setText(dates[position]);
				
				moneyView.setText(money[position]);
				// Change icon based on name
				
				return rowView;
			}
		}
}
