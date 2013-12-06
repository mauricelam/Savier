package com.mauricelam.Savier;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class GoalDetailsFragment extends Fragment {

    private Goal goal;

    public GoalDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        this.goal = (Goal) getArguments().getSerializable("goal");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        View view = inflater.inflate(R.layout.goal_detail_fragment, container, false);
    	
       TextView nameTextView = (TextView) view.findViewById(R.id.name);
       nameTextView.setText(goal.getName());
       

       TextView savingsTextView = (TextView) view.findViewById(R.id.savings);
       savingsTextView.setText("SAVED:"+" $"+goal.getSaved() +" of " +"$"+goal.getTarget());
       
       TextView amazonlinkTextView = (TextView) view.findViewById(R.id.amazonlink);
       amazonlinkTextView.setText(Html.fromHtml("<a href=\""+ goal.getUrl() + "\">" + "View on Amazon" + "</a>"));
       amazonlinkTextView.setClickable(true);
       amazonlinkTextView.setMovementMethod (LinkMovementMethod.getInstance());
//       amazonlinkTextView.setText(goal.getUrl());
       
        GoalView goalView = (GoalView) view.findViewById(R.id.goalview);
        
        goalView.setGoal(goal);
        
        Button undoBtn = (Button) view.findViewById(R.id.undo_button);
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                GoalList list = GoalList.instance(getActivity());
//                list.remove(goal);
                //TODO close the dialog box
            }
        });

        Button autopayBtn = (Button) view.findViewById(R.id.autopay_button);
        autopayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO close the dialog box                
            }
        });
       System.out.println("Goal screen created");
        return view;
    }

    public static GoalDetailsFragment newInstance(Goal goal) {
        GoalDetailsFragment fragment = new GoalDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("goal", goal);
        fragment.setArguments(args);

        return fragment;
    }
}
