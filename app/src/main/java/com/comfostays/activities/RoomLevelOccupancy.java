package com.comfostays.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class RoomLevelOccupancy extends AppCompatActivity {

    CommonFunctionality commonFunctionality;
    SharedPreference sharedPreference;
    LinearLayout linearLayout;

    private CategorySeries mSeries = new CategorySeries("");

    private DefaultRenderer mRenderer = new DefaultRenderer();

    String[] typesOfRooms;
    double[] percentageOfEachRoomType;
    String chartType="";

    private GraphicalView mChartView;

    GridLayout gridLayoutCurrentOccupancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_room_level_occupancy);

            sharedPreference=new SharedPreference(getApplicationContext());
            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);

            TextView chartHeading=(TextView)findViewById(R.id.textView_chartTypeHeading);

            chartType=sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_TYPE_OF_CHART);

            if(chartType.equalsIgnoreCase(Constants.OCCUPIED) && chartHeading!=null){

                commonFunctionality.setTitleBar(R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_OCCUPIED);
                chartHeading.setText(Constants.CURRENTLY_OCCUPIED_ROOMS);

            }else if(chartHeading!=null){

                commonFunctionality.setTitleBar(R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_UNOCCUPIED);
                chartHeading.setText(Constants.CURRENTLY_UNOCCUPIED_ROOMS);
            }

            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            Intent intent=getIntent();
            typesOfRooms=intent.getStringArrayExtra(Constants.SHARED_PREFERENCE_LIST_TYPES_OF_ROOMS);
            percentageOfEachRoomType=intent.getDoubleArrayExtra(Constants.SHARED_PREFERENCE_LIST_PERCENTAGE_OF_EACH_ROOM);

            linearLayout=(LinearLayout) findViewById(R.id.chart);

            gridLayoutCurrentOccupancy=(GridLayout)findViewById(R.id.gridLayout_currentOccupancy);

            boolean isCurrentOccupancyCheckedBoxChecked=sharedPreference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED);

            if(isCurrentOccupancyCheckedBoxChecked && gridLayoutCurrentOccupancy!=null &&chartHeading!=null){

                String typeOfProperty=sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_PROPERTY_TYPE);

                if(typeOfProperty.equalsIgnoreCase(Constants.PGs)){

                    populateGridForPg();
                }else{
                    populateGridForFlatAndHotels();
                }

                gridLayoutCurrentOccupancy.setVisibility(View.VISIBLE);
                chartHeading.setVisibility(View.VISIBLE);

            }else if(gridLayoutCurrentOccupancy!=null && chartHeading!=null){

                gridLayoutCurrentOccupancy.setVisibility(View.INVISIBLE);

                chartHeading.setVisibility(View.INVISIBLE);
            }

            if(typesOfRooms!=null && percentageOfEachRoomType!=null){

                if(linearLayout!=null){

                    setPieChartProperties();

                    linearLayout.setVisibility(View.VISIBLE);
                }

            }else if(linearLayout!=null){
                linearLayout.setVisibility(View.INVISIBLE);
            }

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();

        }
    }

    @Override
    public void onBackPressed(){

        commonFunctionality.onBackPressed(OccupancyActivity.class);
    }

    public void onClickBackButton(View view){

        commonFunctionality.onBackPressed(OccupancyActivity.class);
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onBackPressed(WelcomeScreen.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mChartView == null) {
            LinearLayout pieChartLinearLayout = (LinearLayout) findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);

            if(pieChartLinearLayout!=null) {

                int screenHeight=commonFunctionality.getScreenHeight();
                pieChartLinearLayout.addView(mChartView, new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenHeight/2));
            }
        }
        else {
            mChartView.repaint();
        }
    }

    private void setPieChartProperties(){

        try {

            ArrayList<String> listOfColorCodes=commonFunctionality.getColorCodes();

            mRenderer.setApplyBackgroundColor(true);
            mRenderer.setLabelsTextSize(40);
            mRenderer.setLabelsColor(Color.BLACK);
            mRenderer.setLegendTextSize(30);
            mRenderer.setStartAngle(180);

            for (int i = 0; i < typesOfRooms.length; i++) {
                mSeries.add(typesOfRooms[i] + " " + percentageOfEachRoomType[i] + "%", percentageOfEachRoomType[i]);
                SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
                renderer.setColor(Color.parseColor(listOfColorCodes.get(i)));
                mRenderer.addSeriesRenderer(renderer);
            }

            if (mChartView != null) {
                mChartView.repaint();
            }
        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void populateGridForPg(){

        try {

            int count=0;

            String partialFilledColor="#F9E79F";
            String completelyFilledColor="#00FF00";
            String completelyEmptyColor="#ff7f7f";

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);
            HashMap<String,Integer> mapOfRooms;

            HashMap<String,Integer> mapOfRoomNumberToOccupancy=localDatabaseHandler.getRoomNumberToOccupancyMap();

            if(chartType.equalsIgnoreCase(Constants.OCCUPIED)){

                mapOfRooms=localDatabaseHandler.getCurrentOccupiedRoomsForPg();

            }else{

                mapOfRooms=localDatabaseHandler.getCurrentUnOccupiedRoomsForPg();
            }

            Set<String> set=mapOfRooms.keySet();

            Iterator<String> iterator=set.iterator();

            while(iterator.hasNext()){

                String roomNumber=iterator.next();

                int vacantOccupancy=mapOfRooms.get(roomNumber);

                int totalOccupancy=mapOfRoomNumberToOccupancy.get(roomNumber);

                TextView roomNumberTextView = new TextView(getApplicationContext());
                if(vacantOccupancy!=0){

                    roomNumberTextView.setBackgroundColor(Color.parseColor(partialFilledColor));

                }else if(vacantOccupancy==totalOccupancy){

                    roomNumberTextView.setBackgroundColor(Color.parseColor(completelyEmptyColor));
                }else{
                    roomNumberTextView.setBackgroundColor(Color.parseColor(completelyFilledColor));
                }
                roomNumberTextView.setTextColor(Color.BLACK);
                roomNumberTextView.setId(View.generateViewId());
                roomNumberTextView.setText(roomNumber);
                roomNumberTextView.setGravity(Gravity.CENTER);
                roomNumberTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(0);
                param.rowSpec = GridLayout.spec(count);
                param.setGravity(Gravity.CENTER_HORIZONTAL);
                roomNumberTextView.setLayoutParams(param);

                gridLayoutCurrentOccupancy.addView(roomNumberTextView);
                for(int i=0;i<totalOccupancy;i++){

                    TextView partialOccupancy=new TextView(getApplicationContext());
                    if(vacantOccupancy!=0 && vacantOccupancy>i) {
                        partialOccupancy.setBackgroundColor(Color.parseColor(completelyEmptyColor));
                    }else{
                        partialOccupancy.setBackgroundColor(Color.parseColor(completelyFilledColor));
                    }
                    partialOccupancy.setId(View.generateViewId());
                    partialOccupancy.setGravity(Gravity.CENTER);
                    partialOccupancy.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                    param = new GridLayout.LayoutParams();
                    param.height = 50;
                    param.width = 50;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(i+1);
                    param.rowSpec = GridLayout.spec(count);
                    param.setGravity(Gravity.CENTER_HORIZONTAL);
                    partialOccupancy.setLayoutParams(param);

                    gridLayoutCurrentOccupancy.addView(partialOccupancy);
                }
                count++;
            }

        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void populateGridForFlatAndHotels(){

        try {

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

            ArrayList<String> listOfOccupiedRooms;
            String color;

            if(chartType.equalsIgnoreCase(Constants.OCCUPIED)){

                listOfOccupiedRooms=localDatabaseHandler.getCurrentOccupiedRoomsForFlatAndHotels();
                color="#00FF00";

            }else{
                listOfOccupiedRooms=localDatabaseHandler.getCurrentUnOccupiedRoomsForFlatAndHotels();
                color="#ff7f7f";
            }

            for(int i=0;i<listOfOccupiedRooms.size();i++){

                int screenWidth=commonFunctionality.getScreenWidth();

                String roomNumber=listOfOccupiedRooms.get(i);

                TextView roomNumberTextView = new TextView(getApplicationContext());
                roomNumberTextView.setTextColor(Color.BLACK);
                roomNumberTextView.setBackgroundColor(Color.parseColor(color));
                roomNumberTextView.setId(View.generateViewId());
                roomNumberTextView.setText(roomNumber);
                roomNumberTextView.setGravity(Gravity.CENTER);
                roomNumberTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = (screenWidth)/2;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(0);
                param.rowSpec = GridLayout.spec(i);
                param.setGravity(Gravity.CENTER_HORIZONTAL);
                roomNumberTextView.setLayoutParams(param);

                gridLayoutCurrentOccupancy.addView(roomNumberTextView);
            }

        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }
}
