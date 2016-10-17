package com.comfostays.activities.buildingsetup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.PropertyDetailsVO;

import java.util.ArrayList;

public class CaptureOtherDetailsActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;

    PropertyDetailsVO propertyDetailsVO;
    FloorToRoomVO floorToRoomVO;

    ArrayList<String> typesOfRoomsList;
    private ArrayList<String> listOfIds=new ArrayList<>();

    private GridLayout typesOfRoomsGridLayout;
    private GridLayout captureOtherDetailsGridLayoutMealsOptions;
    private GridLayout itemsProvidedForFurnishedFlats;

    int count=1;

    boolean isMealsCheckBoxChecked=false;
    boolean isFlatFurnished=false;

    String facilitiesProvided="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_capture_other_details);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);
            commonFunctionality.setTitleBar(R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_CAPTURE_OTHER_DETAILS);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=(FloorToRoomVO)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);

            setupScreenAsPerPropertyType();
            setupTypesOfRooms();

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(getApplicationContext(),SetBuildingLayoutActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);

        startActivity(intent);
        finish();
    }

    public void onClickBackButton(View view){

        Intent intent=new Intent(getApplicationContext(),SetBuildingLayoutActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);

        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onClickHomeButton();
    }

    public void onClickNext(View view){

        try {

            CheckBox weeklyMealScheduleAvailableCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_isFixedMealScheduleAvailable);

            populateFacilitiesProvidedMap();

            if (typesOfRoomsList.size()<1 || typesOfRoomsList.get(0).equalsIgnoreCase("")) {

                commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_NO_ROOM_TYPE_ENTERED);
            }else{

                if (weeklyMealScheduleAvailableCheckBox!=null && weeklyMealScheduleAvailableCheckBox.isChecked()) {

                    propertyDetailsVO.setMealScheduleAvailable(true);

                    Intent intent = new Intent(getApplicationContext(), AddMealsScheduleActivity.class);
                    intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO, propertyDetailsVO);
                    intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
                    startActivity(intent);
                    finish();

                } else {

                    propertyDetailsVO.setMealScheduleAvailable(false);

                    Intent intent = new Intent(getApplicationContext(), RoomConfigurationActivity.class);
                    intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO, propertyDetailsVO);
                    intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
                    startActivity(intent);
                    finish();
                }
            }
        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void setupTypesOfRooms(){
        try {

            int screenWidth=commonFunctionality.getScreenWidth();

            String typeOfRoomsHint = "Single Sharing , Duplex etc....";

            GridLayout.LayoutParams param;

            EditText roomTypeEditText = new EditText(getApplicationContext());
            roomTypeEditText.setId(View.generateViewId());
            roomTypeEditText.setTextColor(Color.BLACK);
            roomTypeEditText.setHint(typeOfRoomsHint);
            roomTypeEditText.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
            roomTypeEditText.setBackgroundResource(R.drawable.rounded_edittext);
            roomTypeEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            roomTypeEditText.setGravity(Gravity.CENTER);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = (screenWidth * 6) / 10;
            param.leftMargin = screenWidth / 20;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(0);
            param.rowSpec = GridLayout.spec(count);
            roomTypeEditText.setLayoutParams(param);

            final ImageView imageViewRemove = new ImageView(getApplicationContext());
            imageViewRemove.setId(View.generateViewId());
            imageViewRemove.setImageResource(R.drawable.ic_cancel_black_24dp);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = screenWidth / 10;
            param.leftMargin = screenWidth / 20;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(1);
            param.rowSpec = GridLayout.spec(count);
            param.setGravity(Gravity.CENTER);
            imageViewRemove.setLayoutParams(param);

            imageViewRemove.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    boolean isRemoved = false;

                    for (int i = 0; i < listOfIds.size(); i++) {

                        String[] ids = listOfIds.get(i).split(Constants.COLON);

                        if (ids[1].equalsIgnoreCase(String.valueOf(v.getId())) && i == (listOfIds.size() - 1)) {

                            typesOfRoomsGridLayout.removeView(findViewById(Integer.parseInt(ids[0])));
                            typesOfRoomsGridLayout.removeView(findViewById(Integer.parseInt(ids[1])));
                            typesOfRoomsGridLayout.removeView(findViewById(Integer.parseInt(ids[2])));

                            isRemoved = true;
                            listOfIds.remove(i);
                            count--;

                            if (i != 0) {

                                String[] previousIds = listOfIds.get(i - 1).split(Constants.COLON);
                                ImageView removeRow = (ImageView) findViewById(Integer.parseInt(previousIds[1]));
                                ImageView addRow = (ImageView) findViewById(Integer.parseInt(previousIds[2]));

                                if (removeRow != null && addRow != null) {

                                    removeRow.setVisibility(View.VISIBLE);
                                    addRow.setVisibility(View.VISIBLE);
                                } else if (addRow != null) {
                                    addRow.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                    if (!isRemoved) {
                        commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_FLOOR_NOT_REMOVED);
                    }
                }
            });

            final ImageView imageViewAdd = new ImageView(getApplicationContext());
            imageViewAdd.setId(View.generateViewId());
            imageViewAdd.setImageResource(R.drawable.ic_add);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = screenWidth / 10;
            param.leftMargin = screenWidth / 40;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(2);
            param.rowSpec = GridLayout.spec(count);
            param.setGravity(Gravity.CENTER);
            imageViewAdd.setLayoutParams(param);

            imageViewAdd.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    imageViewAdd.setVisibility(View.INVISIBLE);
                    imageViewRemove.setVisibility(View.INVISIBLE);
                    setupTypesOfRooms();
                }
            });


            if (typesOfRoomsGridLayout != null) {
                typesOfRoomsGridLayout.addView(roomTypeEditText);
                if (count != 1) {
                    typesOfRoomsGridLayout.addView(imageViewRemove);
                }
                typesOfRoomsGridLayout.addView(imageViewAdd);
            }

            listOfIds.add(roomTypeEditText.getId() + Constants.COLON + imageViewRemove.getId() + Constants.COLON + imageViewAdd.getId());
            count++;

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void setupScreenAsPerPropertyType(){

        typesOfRoomsGridLayout=(GridLayout)findViewById(R.id.captureOtherDetails_gridLayout_tyesOfRooms);
        captureOtherDetailsGridLayoutMealsOptions=(GridLayout)findViewById(R.id.captureOtherDetails_gridLayout_mealsOptions);
        itemsProvidedForFurnishedFlats=(GridLayout)findViewById(R.id.gridLayout_itemsProvided);

        CheckBox flatFurnishedCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_isFlatFurnished);
        CheckBox mealsOfferedCheckBox=(CheckBox)findViewById(R.id.checkBox_mealsOption);


        if(captureOtherDetailsGridLayoutMealsOptions!=null && itemsProvidedForFurnishedFlats!=null){
            captureOtherDetailsGridLayoutMealsOptions.setVisibility(View.GONE);
            itemsProvidedForFurnishedFlats.setVisibility(View.GONE);
        }

        if(propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.HOTEL) && flatFurnishedCheckBox!=null && mealsOfferedCheckBox!=null){

            mealsOfferedCheckBox.setVisibility(View.GONE);
            flatFurnishedCheckBox.setVisibility(View.GONE);

        }else if(propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.PGs)&& flatFurnishedCheckBox!=null){

            flatFurnishedCheckBox.setVisibility(View.GONE);

        }else if(flatFurnishedCheckBox!=null){

            flatFurnishedCheckBox.setVisibility(View.VISIBLE);
        }
    }

    private void populateFacilitiesProvidedMap(){

        String roomTypes="";

        try {

            captureCheckBoxFacilities();

            CheckBox isFlatFurnished=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_isFlatFurnished);

            if(propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.FLAT) && isFlatFurnished!=null && isFlatFurnished.isChecked()){

                captureFurnishedFlatsItemsProvided();
            }

            typesOfRoomsList=new ArrayList<>();


            for (int i = 0; i < listOfIds.size(); i++) {

                String viewId = listOfIds.get(i).split(Constants.COLON)[0];

                TextView view = (TextView) findViewById(Integer.parseInt(viewId));

                if (view != null) {

                    roomTypes=roomTypes+view.getText().toString()+":";

                    typesOfRoomsList.add(view.getText().toString());
                }
            }

            propertyDetailsVO.setRoomTypes(roomTypes.substring(0,roomTypes.length()-1));
            propertyDetailsVO.setTypeOfRooms(typesOfRoomsList);

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    public void onClickMealsCheckBox(View view){

        CheckBox mealOption=(CheckBox)findViewById(R.id.checkBox_mealsOption);

        if(mealOption!=null && mealOption.isChecked()){

            isMealsCheckBoxChecked=true;
            captureOtherDetailsGridLayoutMealsOptions.setVisibility(View.VISIBLE);
        }else{

            isMealsCheckBoxChecked=false;
            captureOtherDetailsGridLayoutMealsOptions.setVisibility(View.GONE);
        }
    }

    public void onClickFlatFurnished(View view){

        CheckBox flatFurnished=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_isFlatFurnished);

        if(flatFurnished!=null && flatFurnished.isChecked()){

            isFlatFurnished=true;

            itemsProvidedForFurnishedFlats.setVisibility(View.VISIBLE);

            ScrollView scrollView=(ScrollView)findViewById(R.id.captureOtherDetails_scrollView) ;

            if(scrollView!=null) {

                scrollView.scrollTo(0,scrollView.getMaxScrollAmount());
            }
        }else{

            isFlatFurnished=false;

            itemsProvidedForFurnishedFlats.setVisibility(View.GONE);
        }
    }

    private void captureCheckBoxFacilities(){

        ArrayList<String> listOfFacilities=new ArrayList<>();

        String fourWheelerParking = "Four Wheeler Parking";
        String twoWheelerParking = "Two Wheeler Parking";
        String allTimeElectricity = "24*7 Electricity";
        String laundry = "Cleaning and Washing of Clothes";
        String freeWifi = "Free Wifi";
        String vegString = "Veg";
        String nonVegString = "nonVeg";
        String northIndianString = "northIndianFood";
        String southIndianString = "southIndianFood";

        if (isMealsCheckBoxChecked) {

            propertyDetailsVO.setMealOffered(true);

            CheckBox veg = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_veg);
            CheckBox nonVeg = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_nonVeg);
            CheckBox northIndianFood = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_northIndianFood);
            CheckBox southIndianFood = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_southIndianFood);

            if (veg != null && veg.isChecked()) {
                facilitiesProvided=facilitiesProvided+vegString+":";
                listOfFacilities.add(vegString);
            }
            if (nonVeg != null && nonVeg.isChecked()) {
                facilitiesProvided=facilitiesProvided+nonVegString+":";
                listOfFacilities.add(nonVegString);
            }
            if (northIndianFood != null && northIndianFood.isChecked()) {
                facilitiesProvided=facilitiesProvided+northIndianString+":";
                listOfFacilities.add(northIndianString);
            }
            if (southIndianFood != null && southIndianFood.isChecked()) {
                facilitiesProvided=facilitiesProvided+southIndianString+":";
                listOfFacilities.add(southIndianString);
            }

        } else {
            propertyDetailsVO.setMealOffered(false);
        }

        CheckBox fourWheelerParkingCheckBox = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_fourWheelerParking);
        CheckBox twoWheelerParkingCheckBox = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_twoWheelerParking);
        CheckBox allTimeElectricityCheckBox = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_allTimeElectricity);
        CheckBox laundryCheckBox = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_laundry);
        CheckBox wiFiCheckBox = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_wiFi);

        if (fourWheelerParkingCheckBox != null && fourWheelerParkingCheckBox.isChecked()) {
            facilitiesProvided=facilitiesProvided+fourWheelerParking+":";
            listOfFacilities.add(fourWheelerParking);
        }
        if (twoWheelerParkingCheckBox != null && twoWheelerParkingCheckBox.isChecked()) {
            facilitiesProvided=facilitiesProvided+twoWheelerParking+":";
            listOfFacilities.add(twoWheelerParking);
        }
        if (allTimeElectricityCheckBox != null && allTimeElectricityCheckBox.isChecked()) {
            facilitiesProvided=facilitiesProvided+allTimeElectricity+":";
            listOfFacilities.add(allTimeElectricity);
        }
        if (laundryCheckBox != null && laundryCheckBox.isChecked()) {
            facilitiesProvided=facilitiesProvided+laundry+":";
            listOfFacilities.add(laundry);
        }
        if (wiFiCheckBox != null && wiFiCheckBox.isChecked()) {
            facilitiesProvided=facilitiesProvided+freeWifi+":";
            listOfFacilities.add(freeWifi);
        }

        EditText otherFacilities = (EditText) findViewById(R.id.captureOtherDetails_editText_remainingFacilities);

        if (otherFacilities != null) {

            String[] otherFact = otherFacilities.getText().toString().split(",");

            for (int i = 0; i < otherFact.length; i++) {
                facilitiesProvided=facilitiesProvided+otherFact[i]+":";

                listOfFacilities.add(otherFact[i]);
            }
        }

        propertyDetailsVO.setFacilitiesProvided(facilitiesProvided.substring(0,facilitiesProvided.length()-1));
        propertyDetailsVO.setListOfFacilities(listOfFacilities);
    }

    private void captureFurnishedFlatsItemsProvided(){

        String furnishedFlatFacilities="";

        CheckBox tvCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_tv);
        CheckBox fridgeCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_fridge);
        CheckBox gyserCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_geyser);
        CheckBox washingMachineCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_washingMachine);
        CheckBox almirahCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_almirah);
        CheckBox bedCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_bed);
        CheckBox coolerCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_cooler);
        CheckBox fanCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_fan);
        CheckBox acCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_ac);
        CheckBox sofaCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_sofa);
        CheckBox chairCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_chair);

        if(tvCheckBox!=null && tvCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.TV+":";
        }

        if(fridgeCheckBox!=null && fridgeCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.FRIDGE+":";
        }

        if(gyserCheckBox!=null && gyserCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.GEYSER+":";
        }

        if(washingMachineCheckBox!=null && washingMachineCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.WASHING_MACHINE+":";
        }

        if(almirahCheckBox!=null && almirahCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.ALMIRAH+":";
        }

        if(bedCheckBox!=null && bedCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.BED+":";
        }

        if(coolerCheckBox!=null && coolerCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.COOLER+":";
        }

        if(fanCheckBox!=null && fanCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.FAN+":";
        }

        if(acCheckBox!=null && acCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.AC+":";
        }

        if(sofaCheckBox!=null && sofaCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.SOFA+":";
        }

        if(chairCheckBox!=null && chairCheckBox.isChecked()){

            furnishedFlatFacilities=furnishedFlatFacilities+Constants.CHAIR+":";
        }

        propertyDetailsVO.setFurnishedFlatItems(furnishedFlatFacilities.substring(0,furnishedFlatFacilities.length()-1));
    }
}
