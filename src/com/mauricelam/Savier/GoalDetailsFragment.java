package com.mauricelam.Savier;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
        
        GoalView goalView = (GoalView) view.findViewById(R.id.goalview);
        
        goalView.setGoal(goal);
        
        Button deleteBtn = (Button) view.findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalList list = GoalList.instance(getActivity());
                list.remove(goal);
                //TODO close the dialog box
            }
        });

        Button closeBtn = (Button) view.findViewById(R.id.close_button);
        closeBtn.setOnClickListener(new View.OnClickListener() {
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
