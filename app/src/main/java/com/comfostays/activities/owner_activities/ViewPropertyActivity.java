package com.comfostays.activities.owner_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.owner_activities.edit_activities.EditBuildingDetailsActivity;
import com.comfostays.activities.owner_activities.edit_activities.EditCostAndChargesActivity;
import com.comfostays.activities.owner_activities.edit_activities.EditFacilitiesProvidedActivity;
import com.comfostays.activities.owner_activities.edit_activities.EditMealActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewPropertyActivity extends AppCompatActivity {

    LinearLayout layoutForBuildingDetails;
    LinearLayout layoutForFacilitiesProvided;
    LinearLayout layoutForCostAndCharges;
    LinearLayout layoutForMealTimingAndSchedule;

    GridLayout layoutForColorCodes;

    boolean isMealsScheduleAvailable=false;
    boolean isBuildingPropertiesExpanded=false;
    boolean isFacilitiesProvidedExpanded=false;
    boolean isCostAndChargesExpanded=false;
    boolean isMealTimingAndScheduleExpanded=false;

    PropertyDetailsVO propertyDetailsVO;
    FloorToRoomVO floorToRoomVO;
    MealScheduleVO mealScheduleVO;
    CostAndChargesVO costAndChargesVO;

    String[] typesOfRooms;
    private String[] days;

    HashMap<String,String> mapOfRoomTypeToColorCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_property);

            CommonFunctionality.setTitleBar(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_VIEW_PROPERTY);

            layoutForColorCodes=(GridLayout)findViewById(R.id.finalPortfolio_gridLayout_colorCodes);
            layoutForCostAndCharges=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout_costAndCharges);
            layoutForBuildingDetails=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout_buildingDetails);
            layoutForFacilitiesProvided=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout_facilitiesProvided);
            layoutForMealTimingAndSchedule=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout_mealSchedule);

            if(layoutForBuildingDetails!=null && layoutForFacilitiesProvided!=null && layoutForCostAndCharges!=null && layoutForMealTimingAndSchedule!=null){

                layoutForBuildingDetails.setVisibility(View.GONE);
                layoutForFacilitiesProvided.setVisibility(View.GONE);
                layoutForCostAndCharges.setVisibility(View.GONE);
                layoutForMealTimingAndSchedule.setVisibility(View.GONE);
            }

            TextView editCostAndCharges=(TextView)findViewById(R.id.finalPortfolio_textView_editCostAndCharges);

            if(editCostAndCharges!=null){

                editCostAndCharges.setVisibility(View.GONE);
            }

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);
            String propertyName="";

            Intent intent=getIntent();
            int propertyId=intent.getIntExtra(Constants.INTENT_SELECTED_PROPERTY_ID,0);

            propertyDetailsVO=localDatabaseHandler.getPropertyDetailsVO(propertyId);
            floorToRoomVO=localDatabaseHandler.getPropertyLayoutDetails(propertyId);
            costAndChargesVO=localDatabaseHandler.getCostAndCharges(propertyId);

            if(propertyDetailsVO!=null){
                isMealsScheduleAvailable =propertyDetailsVO.isMealScheduleAvailable();
                typesOfRooms=propertyDetailsVO.getTypeOfRooms();
                propertyName=propertyDetailsVO.getPropertyName();
            }

            if(isMealsScheduleAvailable) {
                mealScheduleVO = localDatabaseHandler.getMealScheduleVO(propertyId);
            }else{

                ImageView downArrow=(ImageView)findViewById(R.id.finalPortfolio_imageView_mealSchedule);
                TextView mealHeading=(TextView)findViewById(R.id.finalPortfolio_textView_mealSchedule);
                TextView editMeal=(TextView)findViewById(R.id.finalPortfolio_textView_editMealSchedule);


                if(downArrow!=null && mealHeading!=null && editMeal!=null && layoutForMealTimingAndSchedule!=null){

                    downArrow.setVisibility(View.GONE);
                    mealHeading.setVisibility(View.GONE);
                    editMeal.setVisibility(View.GONE);
                    layoutForMealTimingAndSchedule.setVisibility(View.GONE);
                }
            }

            TextView propertyNameTextView=(TextView)findViewById(R.id.finalPortfolio_textView_buildingName);
            if(propertyNameTextView!=null){
                propertyNameTextView.setText(propertyName);
            }

            populateColorCodes();
            populateListOfDays();

            setBuildingLayout();
            setBuildingPropertiesLayout();
            setFacilitiesProvidedLayout();
            setCostAndChargesLayout();
            setMealLayout();


        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(getApplicationContext(),MyPropertiesActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickBackButton(View view){

        Intent intent=new Intent(getApplicationContext(),MyPropertiesActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickBackToMyProperty(View view){

        Intent intent=new Intent(getApplicationContext(),MyPropertiesActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickDelete(View view){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(Constants.ALERT_MESSAGE);
        builder.setMessage(Constants.POPUP_MESSAGE_DELETE_PROPERTY);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),ViewPropertyActivity.this);

                localDatabaseHandler.deleteProperty(propertyDetailsVO.getPropertyId());

                Intent intent=new Intent(getApplicationContext(), WelcomeScreen.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(Constants.POPUP_BUTTON_TITLE_CANCEL, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClickBuildingDetails(View view){

        if(isBuildingPropertiesExpanded && layoutForBuildingDetails!=null){

            layoutForBuildingDetails.setVisibility(View.GONE);
            isBuildingPropertiesExpanded=false;

        }else if(layoutForBuildingDetails!=null){
            layoutForBuildingDetails.setVisibility(View.VISIBLE);
            isBuildingPropertiesExpanded=true;
        }
    }

    public void onClickFacilitiesProvided(View view){

        if(isFacilitiesProvidedExpanded && layoutForFacilitiesProvided!=null){

            layoutForFacilitiesProvided.setVisibility(View.GONE);
            isFacilitiesProvidedExpanded=false;

        }else if(layoutForFacilitiesProvided!=null){
            layoutForFacilitiesProvided.setVisibility(View.VISIBLE);
            isFacilitiesProvidedExpanded=true;
        }
    }

    public void onClickCostAndCharges(View view){

        if(isCostAndChargesExpanded && layoutForCostAndCharges!=null){

            layoutForCostAndCharges.setVisibility(View.GONE);
            isCostAndChargesExpanded=false;

        }else if(layoutForCostAndCharges!=null){
            layoutForCostAndCharges.setVisibility(View.VISIBLE);
            isCostAndChargesExpanded=true;
        }
    }

    public void onCLickMealTimingAndSchedule(View view){

        if(isMealTimingAndScheduleExpanded && layoutForMealTimingAndSchedule!=null){

            layoutForMealTimingAndSchedule.setVisibility(View.GONE);
            isMealTimingAndScheduleExpanded=false;
        }else if(layoutForMealTimingAndSchedule!=null){
            layoutForMealTimingAndSchedule.setVisibility(View.VISIBLE);
            isMealTimingAndScheduleExpanded=true;
        }

    }

    public void onClickEditBuildingProperties(View view){

        Intent intent=new Intent(getApplicationContext(),EditBuildingDetailsActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_VIEW_PROPERTY);
        intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,propertyDetailsVO.getPropertyId());

        startActivity(intent);
        finish();
    }

    public void onClickEditFacilitiesProvided(View view){

        Intent intent=new Intent(getApplicationContext(),EditFacilitiesProvidedActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_VIEW_PROPERTY);
        intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,propertyDetailsVO.getPropertyId());

        startActivity(intent);
        finish();

    }

    public void onClickEditCostAndCharges(View view){

        Intent intent=new Intent(getApplicationContext(),EditCostAndChargesActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_VIEW_PROPERTY);
        intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,propertyDetailsVO.getPropertyId());

        startActivity(intent);
        finish();
    }

    public void onClickEditMealTimingAndSchedule(View view){

        Intent intent=new Intent(getApplicationContext(),EditMealActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_VIEW_PROPERTY);
        intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,propertyDetailsVO.getPropertyId());

        startActivity(intent);
        finish();
    }

    private void setBuildingLayout(){

        try {

            int count=0;
            int column;

            TextView floorNameTextView ;

            int width = CommonFunctionality.getScreenWidth(this);
            int height = CommonFunctionality.getScreenHeight(this);

            GridLayout.LayoutParams gridLayoutParam;

            LinearLayout layout=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout);

            if(layout!=null){

                layout.getLayoutParams().width = (width * 6) / 10;
                layout.getLayoutParams().height = (height * 6) / 10;
            }

            if(floorToRoomVO!=null) {

                for (Map.Entry<String,LinkedHashMap<String,String>> entry : floorToRoomVO.getMapOfFloorToRoomNoAndRoomType().entrySet()) {

                    column=0;

                    LinearLayout.LayoutParams linearLayoutParamsForTextView = new LinearLayout.LayoutParams((width * 9) / 20, (ViewGroup.LayoutParams.WRAP_CONTENT)*3/2);
                    linearLayoutParamsForTextView.gravity = Gravity.CENTER_HORIZONTAL;

                    LinearLayout.LayoutParams linearLayoutParamsForGridLayout = new LinearLayout.LayoutParams((width * 9) / 20, (ViewGroup.LayoutParams.WRAP_CONTENT)*3/2);
                    linearLayoutParamsForGridLayout.gravity = Gravity.CENTER_HORIZONTAL;
                    linearLayoutParamsForGridLayout.setMargins(5,5,5,20);

                    if(count==0){
                        linearLayoutParamsForTextView.topMargin = (height * 3) / 10;
                        count=1;

                    }else{
                        linearLayoutParamsForTextView.topMargin = (height) / 100;
                    }

                    String floorName=entry.getKey();

                    GridLayout dynamicGridLayout = new GridLayout(getApplicationContext());
                    dynamicGridLayout.setId(View.generateViewId());
                    dynamicGridLayout.setBackgroundColor(Color.parseColor(Constants.COLOR_APPLICATION_BLUE_COLOR));
                    dynamicGridLayout.setLayoutParams(linearLayoutParamsForGridLayout);

                    floorNameTextView = new TextView(getApplicationContext());
                    floorNameTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_APPLICATION_BLUE_COLOR));
                    floorNameTextView.setText(floorName);
                    floorNameTextView.setGravity(Gravity.CENTER);
                    floorNameTextView.setId(View.generateViewId());
                    floorNameTextView.setLayoutParams(linearLayoutParamsForTextView);

                    for (Map.Entry<String,String> entryMap : entry.getValue().entrySet()) {

                        String roomNumber=entryMap.getKey();

                        String roomType=entryMap.getValue().trim();

                        String colorCode=mapOfRoomTypeToColorCode.get(roomType);

                        TextView roomTypeTextView = new TextView(getApplicationContext());
                        roomTypeTextView.setBackgroundColor(Color.parseColor(colorCode));
                        roomTypeTextView.setText(roomNumber);
                        roomTypeTextView.setGravity(Gravity.CENTER);
                        roomTypeTextView.setId(View.generateViewId());

                        gridLayoutParam = new GridLayout.LayoutParams();
                        gridLayoutParam.height = 75;
                        gridLayoutParam.width = 75;
                        gridLayoutParam.setMargins(10,10,10,10);
                        gridLayoutParam.columnSpec = GridLayout.spec(column);
                        gridLayoutParam.rowSpec = GridLayout.spec(0);
                        gridLayoutParam.setGravity(Gravity.CENTER_HORIZONTAL);
                        roomTypeTextView.setLayoutParams(gridLayoutParam);

                        column++;

                        dynamicGridLayout.addView(roomTypeTextView);
                    }

                    if(layout!=null){
                        layout.addView(floorNameTextView);
                        layout.addView(dynamicGridLayout);
                    }
                }
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }

    }

    private void setBuildingPropertiesLayout(){

        try {

            if (propertyDetailsVO != null) {

                String propertyDetailsText = "Property Name :" + propertyDetailsVO.getPropertyName() + "\n" + "Address : " + propertyDetailsVO.getAddressLineOne()
                        + " , " + propertyDetailsVO.getAddressLineTwo() + "\n" + "Property Type :" + propertyDetailsVO.getPropertyType();

                TextView textView = new TextView(getApplicationContext());
                textView.setTextColor(Color.BLACK);
                textView.setText(propertyDetailsText);
                layoutForBuildingDetails.addView(textView);
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void setFacilitiesProvidedLayout(){

        try {

            if (propertyDetailsVO != null) {

                if (propertyDetailsVO.getListOfFacilities().length == 1 && propertyDetailsVO.getListOfFacilities()[0].equalsIgnoreCase("")) {

                    String noFacilities = "No facilities provided";

                    TextView textView = new TextView(getApplicationContext());
                    textView.setText(noFacilities);
                    textView.setTextColor(Color.LTGRAY);
                    layoutForFacilitiesProvided.addView(textView);
                } else {

                    for (int i = 0; i < propertyDetailsVO.getListOfFacilities().length; i++) {

                        TextView textView = new TextView(getApplicationContext());
                        textView.setText(propertyDetailsVO.getListOfFacilities()[i]);
                        textView.setTextColor(Color.BLACK);
                        layoutForFacilitiesProvided.addView(textView);
                    }
                }
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void setCostAndChargesLayout(){

        try {

            ArrayList<String> listOfRoomTypesAndChargesWithDuration = costAndChargesVO.getListOfRoomTypesAndChargesWithDuration();

            if (costAndChargesVO != null && listOfRoomTypesAndChargesWithDuration != null) {

                if(listOfRoomTypesAndChargesWithDuration.size()==0){

                    String emptyText="No charges provided";

                    TextView textView = new TextView(layoutForCostAndCharges.getContext());
                    textView.setText(emptyText);
                    layoutForCostAndCharges.addView(textView);

                }else{

                    for (int i = 0; i < listOfRoomTypesAndChargesWithDuration.size(); i++) {

                        String[] array = listOfRoomTypesAndChargesWithDuration.get(i).split(":");

                        StringBuilder charge=new StringBuilder(array[0]);
                        charge.append(":  Rs. ".concat(array[1]).concat(" (").concat(array[2]).concat(")"));

                        TextView textView = new TextView(layoutForCostAndCharges.getContext());
                        textView.setText(charge);
                        layoutForCostAndCharges.addView(textView);
                    }

                    for (Map.Entry<String,String> entry : costAndChargesVO.getOtherChargesMap().entrySet()) {

                        String chargeType = entry.getKey();
                        String charges = entry.getValue();

                        if (!chargeType.equalsIgnoreCase("") || !charges.equalsIgnoreCase("")) {

                            String charge = chargeType + " : Rs. " + charges;

                            TextView textView = new TextView(layoutForCostAndCharges.getContext());
                            textView.setText(charge);
                            layoutForCostAndCharges.addView(textView);
                        }
                    }
                }
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void setMealLayout(){

        try {

            if (mealScheduleVO != null && layoutForMealTimingAndSchedule != null) {

                StringBuilder timings=new StringBuilder("Breakfast Timings : ");
                timings.append(mealScheduleVO.getBreakfastFromTime().concat(" To ").concat(mealScheduleVO.getBreakfastToTime()));

                TextView textView = new TextView(layoutForMealTimingAndSchedule.getContext());
                textView.setText(timings);
                layoutForMealTimingAndSchedule.addView(textView);

                timings=new StringBuilder("Lunch Timings : ");
                timings.append(mealScheduleVO.getLunchFromTime().concat(" To ").concat(mealScheduleVO.getLunchToTime()));

                textView = new TextView(layoutForMealTimingAndSchedule.getContext());
                textView.setText(timings);
                layoutForMealTimingAndSchedule.addView(textView);

                timings=new StringBuilder("Dinner Timings : ");
                timings.append(mealScheduleVO.getDinnerFromTime().concat(" To ").concat(mealScheduleVO.getDinnerToTime()));

                textView = new TextView(layoutForMealTimingAndSchedule.getContext());
                textView.setText(timings);
                layoutForMealTimingAndSchedule.addView(textView);


                List<String> listOfMeals = mealScheduleVO.getListOfMeals();

                for (int i = 0; i < listOfMeals.size(); i++) {

                    String[] mealArray = listOfMeals.get(i).split("~");

                    String day="       "+days[i] +" :- ";
                    String breakfastItem = "Breakfast : " + mealArray[0];
                    String lunchItem = "Lunch : " + mealArray[1];
                    String dinnerItem = "Dinner : " + mealArray[2];

                    textView = new TextView(layoutForMealTimingAndSchedule.getContext());
                    textView.setText(day);
                    layoutForMealTimingAndSchedule.addView(textView);

                    textView = new TextView(layoutForMealTimingAndSchedule.getContext());
                    textView.setText(breakfastItem);
                    layoutForMealTimingAndSchedule.addView(textView);

                    textView = new TextView(layoutForMealTimingAndSchedule.getContext());
                    textView.setText(lunchItem);
                    layoutForMealTimingAndSchedule.addView(textView);

                    textView = new TextView(layoutForMealTimingAndSchedule.getContext());
                    textView.setText(dinnerItem);
                    layoutForMealTimingAndSchedule.addView(textView);
                }
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateColorCodes() {

        String[] colorCodes=CommonFunctionality.getColorCodes();

        mapOfRoomTypeToColorCode = new HashMap<>();

        GridLayout.LayoutParams param;

        for (int i = 0; i < typesOfRooms.length; i++) {

            TextView roomType = new TextView(getApplicationContext());
            roomType.setText(typesOfRooms[i]);
            roomType.setId(View.generateViewId());
            roomType.setTextColor(Color.BLACK);
            roomType.setGravity(Gravity.CENTER);

            param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(0);
            param.rowSpec = GridLayout.spec(i);
            roomType.setLayoutParams(param);

            TextView roomTypeColor = new TextView(getApplicationContext());
            roomTypeColor.setBackgroundColor(Color.parseColor(colorCodes[i]));
            roomTypeColor.setId(View.generateViewId());

            param = new GridLayout.LayoutParams();
            param.height = 50;
            param.width = 50;
            param.topMargin = 5;
            param.leftMargin = 10;
            param.columnSpec = GridLayout.spec(1);
            param.rowSpec = GridLayout.spec(i+1);
            roomTypeColor.setLayoutParams(param);

            layoutForColorCodes.addView(roomType);
            layoutForColorCodes.addView(roomTypeColor);

            mapOfRoomTypeToColorCode.put(typesOfRooms[i].trim(), colorCodes[i]);
        }
    }

    private void populateListOfDays(){

        days=new String[7];

        days[0]=Constants.MONDAY;
        days[1]=Constants.TUESDAY;
        days[2]=Constants.WEDNESDAY;
        days[3]=Constants.THURSDAY;
        days[4]=Constants.FRIDAY;
        days[5]=Constants.SATURDAY;
        days[6]=Constants.SUNDAY;

    }
}
