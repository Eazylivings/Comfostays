package com.comfostays.activities.buildingsetup;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;

import java.util.HashMap;
import java.util.List;

public class AddMealsScheduleActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    CommonFunctionality commonFunctionality;
    HashMap<String,List<String>> mapOfMealsMenu;
    private HashMap<String,String> mapFromAddPropertyActivity;
    private HashMap<String,String> mapFloorToNumberOfRooms;
    private HashMap<String,Object> mapOfFacilitiesProvided;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_meals_schedule);

            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);

            Intent intent=getIntent();
            mapFromAddPropertyActivity=(HashMap<String,String>)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            mapFloorToNumberOfRooms=(HashMap<String,String>)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);
            mapOfFacilitiesProvided=(HashMap<String,Object>)intent.getSerializableExtra(Constants.INTENT_CAPTURE_OTHER_DETAILS_FACILITIES_DETAILS);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setTitle("Add Meal Schedule");
            setSupportActionBar(toolbar);
            if(getSupportActionBar()!=null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            if(toolbar!=null) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commonFunctionality.onBackPressed(CaptureOtherDetailsActivity.class);
                    }
                });
            }

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            if(mViewPager!=null) {
                mViewPager.setAdapter(mSectionsPagerAdapter);
            }

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            if(tabLayout!=null){
                tabLayout.setupWithViewPager(mViewPager);
            }

        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();

        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){

                View rootView = inflater.inflate(R.layout.fragment_add_meals_schedule_breakfast, container, false);
                return rootView;

            }else{

                View rootView = inflater.inflate(R.layout.fragment_add_meals_schedule_lunch_dinner, container, false);
                return rootView;

            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Breakfast";
                case 1:
                    return "Lunch";
                case 2:
                    return "Dinner";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed(){

        commonFunctionality.onBackPressed(CaptureOtherDetailsActivity.class);
    }

    public void onClickNext(View view){

        Intent intent=new Intent(getApplicationContext(),RoomConfigurationActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,mapFromAddPropertyActivity);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO,mapFloorToNumberOfRooms);
        intent.putExtra(Constants.INTENT_CAPTURE_OTHER_DETAILS_FACILITIES_DETAILS,mapOfFacilitiesProvided);
        intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO,mapOfMealsMenu);

        startActivity(intent);
        finish();

    }
}
