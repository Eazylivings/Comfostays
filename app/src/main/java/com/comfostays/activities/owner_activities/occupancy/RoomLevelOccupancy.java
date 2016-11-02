package com.comfostays.activities.owner_activities.occupancy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.HashMap;
import java.util.Map;

public class RoomLevelOccupancy extends AppCompatActivity {

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

            chartType=sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_TYPE_OF_CHART);

            TextView chartHeading=(TextView)findViewById(R.id.textView_chartTypeHeading);

            if(chartType.equalsIgnoreCase(Constants.OCCUPIED) && chartHeading!=null){

                CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_OCCUPIED);
                chartHeading.setText(Constants.CURRENTLY_OCCUPIED_ROOMS);

            }else if(chartHeading!=null){

                CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_UNOCCUPIED);
                chartHeading.setText(Constants.CURRENTLY_UNOCCUPIED_ROOMS);
            }

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
            CommonFunctionality.generatePopUpMessageForExceptions(this);

        }
    }

    @Override
    public void onBackPressed(){

        CommonFunctionality.onBackPressed(this,OccupancyActivity.class);
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,OccupancyActivity.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
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

                int screenHeight=CommonFunctionality.getScreenHeight(this);
                pieChartLinearLayout.addView(mChartView, new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenHeight/2));
            }
        }
        else {
            mChartView.repaint();
        }
    }

    private void setPieChartProperties(){

        try {

            String[] colorCodes=CommonFunctionality.getColorCodes();

            mRenderer.setApplyBackgroundColor(true);
            mRenderer.setLabelsTextSize(40);
            mRenderer.setLabelsColor(Color.BLACK);
            mRenderer.setLegendTextSize(30);
            mRenderer.setStartAngle(180);

            for (int i = 0; i < typesOfRooms.length; i++) {
                mSeries.add(typesOfRooms[i] + " " + percentageOfEachRoomType[i] + "%", percentageOfEachRoomType[i]);
                SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
                renderer.setColor(Color.parseColor(colorCodes[i]));
                mRenderer.addSeriesRenderer(renderer);
            }

            if (mChartView != null) {
                mChartView.repaint();
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateGridForPg(){

        try {

            int count=0;

            final String partialFilledColor="#F9E79F";
            final String completelyFilledColor="#00FF00";
            final String completelyEmptyColor="#ff7f7f";

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);
            HashMap<String,Integer> mapOfRooms;

            HashMap<String,Integer> mapOfRoomNumberToOccupancy=localDatabaseHandler.getRoomNumberToOccupancyMap();

            if(chartType.equalsIgnoreCase(Constants.OCCUPIED)){

                mapOfRooms=localDatabaseHandler.getCurrentOccupiedRoomsForPg();

            }else{

                mapOfRooms=localDatabaseHandler.getCurrentUnOccupiedRoomsForPg();
            }

            for(Map.Entry<String,Integer> entry : mapOfRooms.entrySet()){

                String roomNumber=entry.getKey();

                int vacantOccupancy=entry.getValue();

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

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateGridForFlatAndHotels(){

        try {

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

            String[] arrayOfOccupiedRooms;
            String color;

            if(chartType.equalsIgnoreCase(Constants.OCCUPIED)){

                arrayOfOccupiedRooms=localDatabaseHandler.getCurrentOccupiedRoomsForFlatAndHotels();
                color="#00FF00";

            }else{
                arrayOfOccupiedRooms=localDatabaseHandler.getCurrentUnOccupiedRoomsForFlatAndHotels();
                color="#ff7f7f";
            }

            for(int i=0;i<arrayOfOccupiedRooms.length;i++){

                int screenWidth=CommonFunctionality.getScreenWidth(this);

                String roomNumber=arrayOfOccupiedRooms[i];

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

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }
}
