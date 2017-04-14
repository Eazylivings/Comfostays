package com.comfostays.activities.owner_activities.occupancy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OccupancyActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    SharedPreference sharedPreference;

    private static int[] COLORS = new int[] {Color.parseColor("#ff7f7f"), Color.GREEN  };

    private double xCoordinate;
    private double yCoordinate;

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    private String[] list = new String[] { "Occupied", "Unoccupied"};

    private GraphicalView mChartView;

    private Spinner fromYearSpinner;
    private Spinner toYearSpinner ;
    private Spinner fromMonthSpinner;
    private Spinner toMonthSpinner ;

    private List<String> monthSpinnerArray;
    private List<String> yearSpinnerArray;

    RelativeLayout monthAndYearRangeRelativeLayout;

    private String ownerEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_occupancy);

            sharedPreference=new SharedPreference(getApplicationContext());

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_OCCUPANCY);

            populateYearSpinner();
            populateMonthSpinner();

            linearLayout=(LinearLayout) findViewById(R.id.chart);
            monthAndYearRangeRelativeLayout=(RelativeLayout)findViewById(R.id.occupancy_relativeLayout_range);

            ownerEmailAddress=sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_EMAIL_ADDRESS);

            boolean isOccupancyLoadedForFirstTime=sharedPreference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_OCCUPANCY_LOADED_FOR_FIRST_TIME);
            boolean isCurrentOccupancyCheckedBoxChecked=sharedPreference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED);

            CheckBox currentOccupancyCheckBox=(CheckBox)findViewById(R.id.checkBox_getCurrentOccupancy);


            if(isCurrentOccupancyCheckedBoxChecked && monthAndYearRangeRelativeLayout!=null && currentOccupancyCheckBox!=null){

                currentOccupancyCheckBox.setChecked(true);
                greyOutSpinners();
            }

            if(isOccupancyLoadedForFirstTime && linearLayout!=null){

                linearLayout.setVisibility(View.INVISIBLE);

            }else{

                String upperLevelRoomOccupancyCoordinates=sharedPreference.getStringValueFromSharedPreference(Constants.INTENT_ROOM_OCCUPANCY_UPPER_LEVEL_COORDINATES);
                String[] coordinates=upperLevelRoomOccupancyCoordinates.split(Constants.COLON);

                fromYearSpinner.setSelection(yearSpinnerArray.indexOf(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_FROM_YEAR)));
                toYearSpinner.setSelection(yearSpinnerArray.indexOf(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_TO_YEAR)));
                fromMonthSpinner.setSelection(Integer.parseInt(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_FROM_MONTH)));
                toMonthSpinner.setSelection(Integer.parseInt(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_TO_MONTH)));

                xCoordinate=Integer.parseInt(coordinates[0]);
                yCoordinate=Integer.parseInt(coordinates[1]);

                if(linearLayout!=null){

                    setPieChartProperties();

                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    @Override
    protected void onResume() {

        try {
            super.onResume();

            if (mChartView == null) {
                LinearLayout pieChartLinearLayout = (LinearLayout) findViewById(R.id.chart);
                mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
                mRenderer.setClickEnabled(true);
                mRenderer.setSelectableBuffer(10);

                mChartView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();

                        if (seriesSelection != null) {
                            OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(getApplicationContext(), OccupancyActivity.this);

                            if (seriesSelection.getPointIndex() == 0) {

                                sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_TYPE_OF_CHART, Constants.OCCUPIED);

                                serverDatabaseHandler.execute(Constants.ACTION_GET_ROOM_OCCUPANCY_ROOM_LEVEL, Constants.OCCUPIED);
                            } else {

                                sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_TYPE_OF_CHART, Constants.UN_OCCUPIED);

                                serverDatabaseHandler.execute(Constants.ACTION_GET_ROOM_OCCUPANCY_ROOM_LEVEL, Constants.UN_OCCUPIED);
                            }
                        }
                    }
                });

                if (pieChartLinearLayout != null) {

                    int screenHeight = CommonFunctionality.getScreenHeight(this);
                    pieChartLinearLayout.addView(mChartView, new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenHeight / 2));
                }
            } else {
                mChartView.repaint();
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void setPieChartProperties(){

        try {

            double[] values = new double[]{xCoordinate, yCoordinate};

            //mRenderer.removeAllRenderers();
            //mSeries.clear();

            mRenderer.setApplyBackgroundColor(true);
            mRenderer.setLabelsTextSize(40);
            mRenderer.setLabelsColor(Color.BLACK);
            mRenderer.setLegendTextSize(30);
            mRenderer.setStartAngle(180);

            for (int i = 0; i < list.length; i++) {
                mSeries.add(list[i] + " " + values[i] + "%", values[i]);
                SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
                renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
                mRenderer.addSeriesRenderer(renderer);
            }

            if (mChartView != null) {
                mChartView.repaint();
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    public void onClickGetOccupancy(View view){

        try {

            sharedPreference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_OCCUPANCY_LOADED_FOR_FIRST_TIME, false);

            boolean isCurrentOccupancyCheckedBoxChecked = sharedPreference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED);


            if (isCurrentOccupancyCheckedBoxChecked) {

                sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_CURRENT_YEAR, String.valueOf(Calendar.YEAR));
                sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_CURRENT_MONTH, monthSpinnerArray.get(Calendar.MONTH));

                OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(getApplicationContext(), this);
                serverDatabaseHandler.execute(Constants.ACTION_GET_ROOM_OCCUPANCY_UPPER_LEVEL, ownerEmailAddress);

            } else {

                String selectedFromYear = fromYearSpinner.getSelectedItem().toString();
                String selectedToYear = toYearSpinner.getSelectedItem().toString();

                String selectedFromMonth = fromMonthSpinner.getSelectedItem().toString();
                String selectedToMonth = toMonthSpinner.getSelectedItem().toString();

                int fromYear = Integer.parseInt(selectedFromYear);
                int toYear = Integer.parseInt(selectedToYear);

                if (fromYear <= toYear) {

                    sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_FROM_YEAR, selectedFromYear);
                    sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_TO_YEAR, selectedToYear);
                    sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_FROM_MONTH, String.valueOf(monthSpinnerArray.indexOf(selectedFromMonth)));
                    sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_TO_MONTH, String.valueOf(monthSpinnerArray.indexOf(selectedToMonth)));

                    OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(getApplicationContext(), this);
                    serverDatabaseHandler.execute(Constants.ACTION_GET_ROOM_OCCUPANCY_UPPER_LEVEL, ownerEmailAddress);

                } else {
                    CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_SELECT_CORRECT_YEAR);
                }
            }

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateYearSpinner(){

        yearSpinnerArray =  new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        for(int i=2000;i<2099;i++){
            yearSpinnerArray.add(String.valueOf(i));
        }

        int positionOfCurrentYear=yearSpinnerArray.indexOf(""+year);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, yearSpinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromYearSpinner = (Spinner) findViewById(R.id.occupancy_spinner_fromYear);
        toYearSpinner = (Spinner) findViewById(R.id.occupancy_spinner_toYear);

        if(fromYearSpinner!=null && toYearSpinner!=null ) {
            fromYearSpinner.setAdapter(adapter);
            toYearSpinner.setAdapter(adapter);

            fromYearSpinner.setSelection(positionOfCurrentYear);
            toYearSpinner.setSelection(positionOfCurrentYear);
        }
    }

    private void populateMonthSpinner(){

        monthSpinnerArray =  new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        monthSpinnerArray.add("January");
        monthSpinnerArray.add("February");
        monthSpinnerArray.add("March");
        monthSpinnerArray.add("April");
        monthSpinnerArray.add("May");
        monthSpinnerArray.add("June");
        monthSpinnerArray.add("July");
        monthSpinnerArray.add("August");
        monthSpinnerArray.add("September");
        monthSpinnerArray.add("October");
        monthSpinnerArray.add("November");
        monthSpinnerArray.add("December");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, monthSpinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromMonthSpinner = (Spinner) findViewById(R.id.occupancy_spinner_fromMonth);
        toMonthSpinner = (Spinner) findViewById(R.id.occupancy_spinner_toMonth);

        if(fromMonthSpinner!=null && toMonthSpinner!=null ) {
            fromMonthSpinner.setAdapter(adapter);
            toMonthSpinner.setAdapter(adapter);

            fromMonthSpinner.setSelection(month);
            toMonthSpinner.setSelection(month);
        }
    }

    public void onClickGetCurrentOccupancy(View view){

        try {

            monthAndYearRangeRelativeLayout = (RelativeLayout) findViewById(R.id.occupancy_relativeLayout_range);
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox_getCurrentOccupancy);

            if (checkBox != null && monthAndYearRangeRelativeLayout != null && checkBox.isChecked()) {

                sharedPreference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED, true);

                linearLayout.setVisibility(View.GONE);

                greyOutSpinners();

            } else if (monthAndYearRangeRelativeLayout != null) {

                sharedPreference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED, false);

                unGreyOutSpinners();
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void greyOutSpinners(){

        fromYearSpinner.setEnabled(false);
        toYearSpinner.setEnabled(false);
        fromMonthSpinner.setEnabled(false);
        toMonthSpinner.setEnabled(false);
    }

    private void unGreyOutSpinners(){

        fromYearSpinner.setEnabled(true);
        toYearSpinner.setEnabled(true);
        fromMonthSpinner.setEnabled(true);
        toMonthSpinner.setEnabled(true);
    }
}
