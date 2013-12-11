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
        String goalID = getArguments().getString("goal");
        this.goal = GoalList.instance(getActivity()).get(goalID);
    }

    public Goal getGoal(){
        return this.goal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_edit_fragment, container, false);

        final EditText nameTextView = (EditText) view.findViewById(R.id.editname);
        nameTextView.setText(goal.getName());


        TextView savingsTextView = (TextView) view.findViewById(R.id.editsavings);
        savingsTextView.setText("Saved: " + goal.getSavedString() + " / " + goal.getTargetString());

        TextView amazonlinkTextView = (TextView) view.findViewById(R.id.editamazonlink);
        String url = goal.getURL();
        if (url != null) {
            amazonlinkTextView.setText(Html.fromHtml("<a href=\""+ goal.getURL() + "\">" + "View on Amazon" + "</a>"));
        } else {
            amazonlinkTextView.setText("");
        }
        amazonlinkTextView.setClickable(true);
        amazonlinkTextView.setMovementMethod (LinkMovementMethod.getInstance());
        final TextView descTextView = (TextView) view.findViewById(R.id.editdescription);
        descTextView.setText(goal.getDesc());
        GoalView goalView = (GoalView) view.findViewById(R.id.goalview);

        goalView.setGoal(goal);

        Button saveButton = (Button) view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText)nameTextView.findViewById(R.id.editname);
                EditText desc = (EditText)descTextView.findViewById(R.id.editdescription);
                String name = text.getText().toString();
                String description = desc.getText().toString();
                goal.setName(name);
                goal.setDesc(description);
                GoalList.instance(getActivity()).commitChanges();
                Toast.makeText(getActivity(), "Goal Details Saved", Toast.LENGTH_SHORT).show();
            }
        });

        Button deleteBtn = (Button) view.findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalList list = GoalList.instance(getActivity());
                list.remove(goal);
                GoalEditFragment.this.getActivity().finish();
            }
        });

        return view;
    }
    public static GoalEditFragment newInstance(String goalID) {
        GoalEditFragment fragment = new GoalEditFragment();
        Bundle args = new Bundle();
        args.putString("goal", goalID);
        fragment.setArguments(args);

        return fragment;
    }


}
