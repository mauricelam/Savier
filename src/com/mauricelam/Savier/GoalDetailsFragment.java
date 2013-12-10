package com.mauricelam.Savier;

import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GoalDetailsFragment extends Fragment implements Observer{

    private Goal goal;

    public GoalDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        this.goal = (Goal) getArguments().getSerializable("goal");
        goal.addObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        View view = inflater.inflate(R.layout.goal_detail_fragment, container, false);
    	
       TextView nameTextView = (TextView) view.findViewById(R.id.name);
       nameTextView.setText(goal.getName());
       

       TextView savingsTextView = (TextView) view.findViewById(R.id.savings);
       savingsTextView.setText("Saved: " + goal.getSavedString() + " / " + goal.getTargetString());
       
       TextView amazonlinkTextView = (TextView) view.findViewById(R.id.amazonlink);
       if (goal.getUrl() != null) {
           amazonlinkTextView.setText(Html.fromHtml("<a href=\""+ goal.getUrl() + "\">" + "View on Amazon" + "</a>"));
       } else {
           amazonlinkTextView.setText("");
       }
       amazonlinkTextView.setClickable(true);
       amazonlinkTextView.setMovementMethod (LinkMovementMethod.getInstance());
       TextView descTextView = (TextView) view.findViewById(R.id.description);
       descTextView.setText(goal.getDescription());
       
        GoalView goalView = (GoalView) view.findViewById(R.id.goalview);
        
        goalView.setGoal(goal);
        
        Button undoBtn = (Button) view.findViewById(R.id.undo_button);
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Toast.makeText(getActivity(), "Coming Up in next version", Toast.LENGTH_SHORT).show();
            }
        });

        Button autopayBtn = (Button) view.findViewById(R.id.autopay_button);
        autopayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Toast.makeText(getActivity(), "Coming Up in next version", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public static GoalDetailsFragment newInstance(Goal goal) {
        GoalDetailsFragment fragment = new GoalDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("goal", goal);
        fragment.setArguments(args);

        return fragment;
    }
    
    @Override
	public void update(Observable observable, Object data) {
        View root = this.getView();
        TextView nameView = (TextView) root.findViewById(R.id.name);
        nameView.setText(goal.getName());
        TextView descriptionView = (TextView) root.findViewById(R.id.description);
		descriptionView.setText(goal.getDescription());
	}
}
