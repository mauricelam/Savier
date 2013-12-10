package com.mauricelam.Savier;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GoalDetailActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	//SectionsPagerAdapter mSectionsPagerAdapter;
	GoalPagerAdapter goalPageAdapter;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	private Goal goal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        this.goal = (Goal) getIntent().getExtras().getSerializable("goal");
        System.out.println("Goal Received :"+this.goal.getName());
		setContentView(R.layout.activity_goal_detail);
        
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		/*mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());*/
		goalPageAdapter = new GoalPagerAdapter(getSupportFragmentManager());
	
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		
		mViewPager.setAdapter(goalPageAdapter);
		
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < goalPageAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(goalPageAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.goal_detail, menu);
		return true;
	}*/

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	
	
	
	public class GoalPagerAdapter extends FragmentPagerAdapter{

		public GoalPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
		
		@Override
		public Fragment getItem(int page) {
			
			switch(page){
			case 0:
				/*Fragment fragment0 = new DummySectionFragment();
				Bundle args0 = new Bundle();
				args0.putInt(DummySectionFragment.ARG_SECTION_NUMBER, page + 1);
				fragment0.setArguments(args0);
				return fragment0;*/
				/*Fragment goalDetailsFragment = new GoalDetailsFragment();
				Bundle args = new Bundle();
				args.putSerializable("goal", goal);
				goalDetailsFragment.setArguments(args);
				return goalDetailsFragment;*/
				
				GoalDetailsFragment detailsFragment = GoalDetailsFragment.newInstance(goal);
				return detailsFragment;
		        //FragmentManager fragmentManager = getFragmentManager();
		        
				// return new TopRatedFragment();
			case 1:
				//TODO
				Fragment fragment = new DummySectionFragment();
				Bundle args1 = new Bundle();
				args1.putInt(DummySectionFragment.ARG_SECTION_NUMBER, page + 1);
				fragment.setArguments(args1);
				return fragment;
				//System.out.println("case1");
			case 2:
				//TODO
				Fragment fragment1 = new DummySectionFragment();
				Bundle args2 = new Bundle();
				args2.putInt(DummySectionFragment.ARG_SECTION_NUMBER, page + 1);
				fragment1.setArguments(args2);
				return fragment1;
			}
			
			return null;
		}

		@Override
		public int getCount() {
			
			return 3;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			
			switch (position) {
			case 0:
				return "Info";
			case 1:
				return "Edit";
			case 2:
				return "History";
			}
			return null;
		}

	}
	
	
	

	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 *//*
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

		     
		     
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}*/

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/*
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_goal_detail_dummy, container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
