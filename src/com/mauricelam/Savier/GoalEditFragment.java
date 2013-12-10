package com.mauricelam.Savier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GoalEditFragment extends Fragment {

    private Goal goal;

    public GoalEditFragment() {
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        this.goal = (Goal) getArguments().getSerializable("goal");
    }
    public Goal getGoal(){
    	return this.goal;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        View view = inflater.inflate(R.layout.goal_edit_fragment, container, false);
    	
       final EditText nameTextView = (EditText) view.findViewById(R.id.editname);
       nameTextView.setText(goal.getName());
       

       TextView savingsTextView = (TextView) view.findViewById(R.id.editsavings);
       savingsTextView.setText("SAVED:"+" $"+goal.getSaved() +" of " +"$"+goal.getTarget());
       
       TextView amazonlinkTextView = (TextView) view.findViewById(R.id.editamazonlink);
       amazonlinkTextView.setText(Html.fromHtml("<a href=\""+ goal.getUrl() + "\">" + "View on Amazon" + "</a>"));
       amazonlinkTextView.setClickable(true);
       amazonlinkTextView.setMovementMethod (LinkMovementMethod.getInstance());
//       amazonlinkTextView.setText(goal.getUrl());
       final TextView descTextView = (TextView) view.findViewById(R.id.editdescription);
       descTextView.setText(goal.getDescription());
        GoalView goalView = (GoalView) view.findViewById(R.id.goalview);
        
        goalView.setGoal(goal);
        
        Button saveButton = (Button) view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	EditText text = (EditText)nameTextView.findViewById(R.id.editname);
            	EditText desc = (EditText)descTextView.findViewById(R.id.editdescription);
            	System.out.println(desc+"descipt");
            	String name = text.getText().toString();
            	String description = desc.getText().toString();
            	goal.setName(name);
            	goal.setDescription(description);
            	Toast.makeText(getActivity(), "Goal Details Saved", Toast.LENGTH_LONG).show();
            	
            	
//            	
            }
        });

        Button deleteBtn = (Button) view.findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             GoalList list = GoalList.instance(getActivity());
            // System.out.println(list.size());
             list.remove(goal);
             list.commitChanges();
             /*System.out.println(list.size());
             System.out.println("Goal deleted");*/
             GoalEditFragment.this.getActivity().finish();
         }
     });
        
      // System.out.println("Goal screen created");
        return view;
    }
    public static GoalEditFragment newInstance(Goal goal) {
    	GoalEditFragment fragment = new GoalEditFragment();
        Bundle args = new Bundle();
        args.putSerializable("goal", goal);
        fragment.setArguments(args);

        return fragment;
    }
	

}
