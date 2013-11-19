package com.mauricelam.Savier;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * User: mauricelam
 * Date: 18/11/13
 * Time: 8:48 PM
 */
public class GoalDetailFragment extends DialogFragment {

    private Goal goal;

    public GoalDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.goal = (Goal) getArguments().getSerializable("goal");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.goal_detail_dialog, container);

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        nameTextView.setText(goal.getName());

        GoalView goalView = (GoalView) view.findViewById(R.id.goalview);
        goalView.setGoal(goal);

        return view;
    }

    public static GoalDetailFragment newInstance(Goal goal) {
        GoalDetailFragment fragment = new GoalDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("goal", goal);
        fragment.setArguments(args);

        return fragment;
    }
}
