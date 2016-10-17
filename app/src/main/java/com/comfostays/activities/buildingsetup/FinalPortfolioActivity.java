package com.comfostays.activities.buildingsetup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.VOClass.PropertyLayoutDetailsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.editactivities.EditBuildingDetailsActivity;
import com.comfostays.activities.editactivities.EditCostAndChargesActivity;
import com.comfostays.activities.editactivities.EditFacilitiesProvidedActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;

public class FinalPortfolioActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;
    SharedPreference sharedPreference;

    LinearLayout layoutForBuildingDetails;
    LinearLayout layoutForFacilitiesProvided;
    LinearLayout layoutForCostAndCharges;

    GridLayout layoutForColorCodes;

    boolean isMealsScheduleAvailable=false;
    boolean isBuildingPropertiesExpanded=false;
    boolean isFacilitiesProvidedExpanded=false;
    boolean isCostAndChargesExpanded=false;

    PropertyDetailsVO propertyDetailsVO;
    FloorToRoomVO floorToRoomVO;
    MealScheduleVO mealScheduleVO;
    CostAndChargesVO costAndChargesVO;

    private ArrayList<String> typesOfRooms;

    private LinkedHashMap<String,String> mapOfRoomTypeToColorCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_final_portfolio);

            commonFunctionality = new CommonFunctionality(getApplicationContext(), this);
            commonFunctionality.setTitleBar(R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_VIEW_PROPERTY);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            TextView editCostAndCharges=(TextView)findViewById(R.id.finalPortfolio_textView_editCostAndCharges);

            if(editCostAndCharges!=null){

                editCostAndCharges.setVisibility(View.GONE);
            }

            sharedPreference=new SharedPreference(getApplicationContext());
            String buildingName=sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_ADD_PROPERTY_BUILDING_NAME);

            TextView buildingNameTextView=(TextView)findViewById(R.id.finalPortfolio_textView_buildingName);
            if(buildingNameTextView!=null){
                buildingNameTextView.setText(buildingName);
            }

            Intent intent=getIntent();

            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=(FloorToRoomVO)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);
            costAndChargesVO=(CostAndChargesVO)intent.getSerializableExtra(Constants.INTENT_COST_AND_CHARGES_VO);

            if(propertyDetailsVO!=null){
                isMealsScheduleAvailable =propertyDetailsVO.isMealScheduleAvailable();
                typesOfRooms=propertyDetailsVO.getTypeOfRooms();
            }else{
                typesOfRooms=new ArrayList<>();
            }
            if(isMealsScheduleAvailable) {
                mealScheduleVO = (MealScheduleVO) intent.getSerializableExtra(Constants.INTENT_MEAL_SCHEDULE_VO);
            }

            layoutForColorCodes=(GridLayout)findViewById(R.id.finalPortfolio_gridLayout_colorCodes);
            layoutForCostAndCharges=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout_costAndCharges);
            layoutForBuildingDetails=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout_buildingDetails);
            layoutForFacilitiesProvided=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout_facilitiesProvided);

            if(layoutForBuildingDetails!=null && layoutForFacilitiesProvided!=null && layoutForCostAndCharges!=null){

                layoutForBuildingDetails.setVisibility(View.GONE);
                layoutForFacilitiesProvided.setVisibility(View.GONE);
                layoutForCostAndCharges.setVisibility(View.GONE);
            }
            populateColorCodes();

            setBuildingLayout();
            setBuildingPropertiesLayout();
            setFacilitiesProvidedLayout();
            setCostAndChargesLayout();

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(getApplicationContext(),CostAndChargesActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

        if (isMealsScheduleAvailable) {
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
        }

        startActivity(intent);
        finish();
    }

    public void onClickBackButton(View view){

        Intent intent=new Intent(getApplicationContext(),CostAndChargesActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

        if (isMealsScheduleAvailable) {
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
        }

        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onBackPressed(WelcomeScreen.class);
    }

    public void onClickFinishButton(View view){

        try {

            //OwnerServerDatabaseHandler serverDatabaseHandler=new OwnerServerDatabaseHandler(getApplicationContext(),this);

            OwnerLocalDatabaseHandler localDatabaseHandler = new OwnerLocalDatabaseHandler(getApplicationContext(), this);


            if (propertyDetailsVO != null) {

                localDatabaseHandler.setPropertyDetails(propertyDetailsVO);
            }

            if (floorToRoomVO != null) {

                LinkedHashMap<String, LinkedHashMap<String, String>> mapOfFloorToRoomNoAndRoomType = floorToRoomVO.getMapOfFloorToRoomNoAndRoomType();

                Set<String> setOfFloorName = mapOfFloorToRoomNoAndRoomType.keySet();
                Iterator<String> iteratorOfFloorNameSet = setOfFloorName.iterator();

                while (iteratorOfFloorNameSet.hasNext()) {

                    String floorName = iteratorOfFloorNameSet.next();

                    LinkedHashMap<String, String> mapOfRoomNumberToRoomType = mapOfFloorToRoomNoAndRoomType.get(floorName);
                    Set<String> setOfRoomNumberToRoomType = mapOfRoomNumberToRoomType.keySet();
                    Iterator<String> iteratorOfRoomNumberToRoomTypeSet = setOfRoomNumberToRoomType.iterator();

                    while (iteratorOfRoomNumberToRoomTypeSet.hasNext()) {

                        PropertyLayoutDetailsVO propertyLayoutDetailsVO = new PropertyLayoutDetailsVO();

                        String roomNumber = iteratorOfRoomNumberToRoomTypeSet.next();

                        String roomType = mapOfRoomNumberToRoomType.get(roomNumber);

                        propertyLayoutDetailsVO.setRoomNumber(roomNumber);
                        propertyLayoutDetailsVO.setRoomType(roomType);
                        propertyLayoutDetailsVO.setFloorNumber(floorName);
                        propertyLayoutDetailsVO.setPropertyId(propertyDetailsVO.getPropertyId());

                        localDatabaseHandler.setPropertyLayoutDetails(propertyLayoutDetailsVO);
                    }
                }
            }

            if (costAndChargesVO != null && propertyDetailsVO != null) {

                localDatabaseHandler.setTableCostAndCharges(costAndChargesVO, propertyDetailsVO.getPropertyId());
            }

            Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
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

    public void onClickEditBuildingProperties(View view){

        Intent intent=new Intent(getApplicationContext(),EditBuildingDetailsActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_FINAL_PORTFOLIO);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
        intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

        if (isMealsScheduleAvailable) {
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
        }
        startActivity(intent);
        finish();
    }

    public void onClickEditFacilitiesProvided(View view){

        Intent intent=new Intent(getApplicationContext(),EditFacilitiesProvidedActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_FINAL_PORTFOLIO);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
        intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

        if (isMealsScheduleAvailable) {
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
        }
        startActivity(intent);
        finish();

    }

    public void onClickEditCostAndCharges(View view){

        Intent intent=new Intent(getApplicationContext(),EditFacilitiesProvidedActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_FINAL_PORTFOLIO);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
        intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

        if (isMealsScheduleAvailable) {
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
        }
        startActivity(intent);
        finish();
    }

    private void setBuildingLayout(){

        try {

            int count=0;
            int column;

            TextView floorNameTextView ;

            int width = commonFunctionality.getScreenWidth();
            int height = commonFunctionality.getScreenHeight();

            GridLayout.LayoutParams gridLayoutParam;

            LinearLayout layout=(LinearLayout)findViewById(R.id.finalPortfolio_linearLayout);

            if(layout!=null){

                layout.getLayoutParams().width = (width * 6) / 10;
                layout.getLayoutParams().height = (height * 6) / 10;
            }

            if(floorToRoomVO!=null) {

                LinkedHashMap<String, LinkedHashMap<String, String>> mapOfFloorToRoomNoAndRoomType = floorToRoomVO.getMapOfFloorToRoomNoAndRoomType();

                Set<String> setOfFloorName=mapOfFloorToRoomNoAndRoomType.keySet();


                Iterator<String> iteratorOfFloorNameSet=setOfFloorName.iterator();

                while(iteratorOfFloorNameSet.hasNext()){

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

                    String floorName=iteratorOfFloorNameSet.next();

                    LinkedHashMap<String,String> mapOfRoomNumberToRoomType=mapOfFloorToRoomNoAndRoomType.get(floorName);
                    Set<String> setOfRoomNumberToRoomType=mapOfRoomNumberToRoomType.keySet();
                    Iterator<String> iteratorOfRoomNumberToRoomTypeSet=setOfRoomNumberToRoomType.iterator();

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

                    while(iteratorOfRoomNumberToRoomTypeSet.hasNext()){

                        String roomNumber=iteratorOfRoomNumberToRoomTypeSet.next();

                        String roomType=mapOfRoomNumberToRoomType.get(roomNumber);

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
            commonFunctionality.generatePopUpMessageForExceptions();
        }

    }

    private void setBuildingPropertiesLayout(){

        if(propertyDetailsVO!=null){

            String propertyDetailsText="Property Name :" + propertyDetailsVO.getPropertyName() +"\n" + "Address : " + propertyDetailsVO.getAddressLineOne()
                    +" , " + propertyDetailsVO.getAddressLineTwo() +"\n" + "Property Type :" + propertyDetailsVO.getPropertyType();

            TextView textView=new TextView(getApplicationContext());
            textView.setTextColor(Color.BLACK);
            textView.setText(propertyDetailsText);
            layoutForBuildingDetails.addView(textView);
        }
    }

    private void setFacilitiesProvidedLayout(){

        if(propertyDetailsVO!=null){

            if(propertyDetailsVO.getListOfFacilities().size()==1 && propertyDetailsVO.getListOfFacilities().get(0).equalsIgnoreCase("")){

                String noFacilities="No facilities provided";

                TextView textView=new TextView(getApplicationContext());
                textView.setText(noFacilities);
                textView.setTextColor(Color.LTGRAY);
                layoutForFacilitiesProvided.addView(textView);
            }else{

                for(int i=0;i<propertyDetailsVO.getListOfFacilities().size();i++){

                    TextView textView=new TextView(getApplicationContext());
                    textView.setText(propertyDetailsVO.getListOfFacilities().get(i));
                    textView.setTextColor(Color.BLACK);
                    layoutForFacilitiesProvided.addView(textView);
                }
            }
        }
    }

    private void setCostAndChargesLayout(){
        LinkedHashMap<String,String> otherChargesMap=costAndChargesVO.getOtherChargesMap();
        ArrayList<String> listOfRoomTypesAndChargesWithDuration=costAndChargesVO.getListOfRoomTypesAndChargesWithDuration();

        for(int i=0;i<listOfRoomTypesAndChargesWithDuration.size();i++){

            String[] array=listOfRoomTypesAndChargesWithDuration.get(i).split(":");

            String charge=array[0] +":  Rs. "+array[1]+" ("+array[2]+")";

            TextView textView=new TextView(getApplicationContext());
            textView.setText(charge);
            textView.setTextColor(Color.BLACK);
            layoutForCostAndCharges.addView(textView);
        }

        Set<String> set=otherChargesMap.keySet();
        Iterator<String> iterator=set.iterator();

        while(iterator.hasNext()){

            String chargeType=iterator.next();
            String charges=otherChargesMap.get(chargeType);

            if(!chargeType.equalsIgnoreCase("") || !charges.equalsIgnoreCase("")){

                String charge=chargeType+" : Rs. "+charges;

                TextView textView=new TextView(getApplicationContext());
                textView.setText(charge);
                textView.setTextColor(Color.BLACK);
                layoutForCostAndCharges.addView(textView);
            }


        }
    }

    private void populateColorCodes(){

        ArrayList<String> colorCodes=new ArrayList<>();
        colorCodes.add("#FF69B4");
        colorCodes.add("#778899");
        colorCodes.add("#FFC0CB");
        colorCodes.add("#00FF00");
        colorCodes.add("#EE82EE");

        mapOfRoomTypeToColorCode=new LinkedHashMap<>();

        GridLayout.LayoutParams param;

        for(int i=1;i<=typesOfRooms.size();i++){

            TextView roomType=new TextView(getApplicationContext());
            roomType.setText(typesOfRooms.get(i-1));
            roomType.setId(View.generateViewId());
            roomType.setTextColor(Color.BLACK);
            roomType.setGravity(Gravity.CENTER);

            param =new GridLayout.LayoutParams();
            param.height =  GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(0);
            param.rowSpec = GridLayout.spec(i);
            roomType.setLayoutParams (param);

            TextView roomTypeColor=new TextView(getApplicationContext());
            roomTypeColor.setBackgroundColor(Color.parseColor(colorCodes.get(i-1)));
            roomTypeColor.setId(View.generateViewId());

            param =new GridLayout.LayoutParams();
            param.height = 50;
            param.width = 50;
            param.topMargin = 5;
            param.leftMargin=10;
            param.columnSpec = GridLayout.spec(1);
            param.rowSpec = GridLayout.spec(i);
            roomTypeColor.setLayoutParams (param);

            layoutForColorCodes.addView(roomType);
            layoutForColorCodes.addView(roomTypeColor);

            mapOfRoomTypeToColorCode.put(typesOfRooms.get(i-1),colorCodes.get(i-1));
        }
    }
}
