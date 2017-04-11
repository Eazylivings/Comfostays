package com.comfostays.activities.owner_activities.building_setup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
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

    private PropertyDetailsVO propertyDetailsVO;
    private FloorToRoomVO floorToRoomVO;

    private ArrayList<String> listOfIds;

    private int[] arrayOfFacilitiesImages;
    private int[] arrayOfIdsOfFacilitiesCheckBox;

    private String[] arrayOfFacilitiesName;
    String[] typesOfRooms;

    private GridLayout typesOfRoomsGridLayout;
    private GridLayout captureOtherDetailsGridLayoutMealsOptions;
    private GridLayout itemsProvidedForFurnishedFlats;

    int count=1;

    private boolean isMealsCheckBoxChecked=false;
    private boolean isFurnishedFacilitiesGridPopulated=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_capture_other_details);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_CAPTURE_OTHER_DETAILS);

            listOfIds=new ArrayList<>();

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=(FloorToRoomVO)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);

            setupScreenAsPerPropertyType();
            setupTypesOfRooms();

        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
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

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickNext(View view){

        try {

            CheckBox weeklyMealScheduleAvailableCheckBox=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_isFixedMealScheduleAvailable);

            populateFacilitiesProvidedMap();

            if (typesOfRooms.length<1 || typesOfRooms[0].equalsIgnoreCase("")) {

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_NO_ROOM_TYPE_ENTERED);
            }else{

                if (weeklyMealScheduleAvailableCheckBox!=null && weeklyMealScheduleAvailableCheckBox.isChecked()) {

                    propertyDetailsVO.setMealScheduleAvailable(true);

                    Intent intent = new Intent(getApplicationContext(), AddMealScheduleActivity.class);
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
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void setupTypesOfRooms(){
        try {

            int screenWidth=CommonFunctionality.getScreenWidth(this);

            final String typeOfRoomsHint = "Single Sharing , Duplex etc....";

            GridLayout.LayoutParams param;

            EditText roomTypeEditText = new EditText(getApplicationContext());
            roomTypeEditText.setId(View.generateViewId());
            roomTypeEditText.setTextColor(Color.BLACK);
            roomTypeEditText.setHint(typeOfRoomsHint);
            roomTypeEditText.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
            roomTypeEditText.setBackgroundResource(R.drawable.rounded_edittext);
            roomTypeEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
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
                        CommonFunctionality.generatePopupMessage(CaptureOtherDetailsActivity.this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_FLOOR_NOT_REMOVED);
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
            CommonFunctionality.generatePopUpMessageForExceptions(this);
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

        String roomTypes=" ";
        typesOfRooms=new String[listOfIds.size()];

        try {

            captureCheckBoxFacilities();

            CheckBox isFlatFurnished=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_isFlatFurnished);

            if(propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.FLAT) && isFlatFurnished!=null && isFlatFurnished.isChecked()){

                captureFurnishedFlatsItemsProvided();
            }

            for (int i = 0; i < listOfIds.size(); i++) {

                String viewId = listOfIds.get(i).split(Constants.COLON)[0];

                TextView view = (TextView) findViewById(Integer.parseInt(viewId));

                if (view != null) {

                    roomTypes=roomTypes+view.getText().toString()+":";

                    typesOfRooms[i]=view.getText().toString();
                }
            }

            propertyDetailsVO.setRoomTypes(roomTypes.substring(0,roomTypes.length()-1));
            propertyDetailsVO.setTypeOfRooms(typesOfRooms);

        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    public void onClickMealsCheckBox(View view){

        CheckBox mealOption=(CheckBox)findViewById(R.id.checkBox_mealsOption);

        if(mealOption!=null && mealOption.isChecked()){

            isMealsCheckBoxChecked=true;
            captureOtherDetailsGridLayoutMealsOptions.setVisibility(View.VISIBLE);

            final ScrollView scrollView=(ScrollView)findViewById(R.id.captureOtherDetails_scrollView);
            if(scrollView!=null){

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }

        }else{

            isMealsCheckBoxChecked=false;
            captureOtherDetailsGridLayoutMealsOptions.setVisibility(View.GONE);
        }
    }

    public void onClickFlatFurnished(View view){

        if(!isFurnishedFacilitiesGridPopulated){

            populateListsForFacilities();

            populateGridForFurnishedFacilities();

            isFurnishedFacilitiesGridPopulated=true;
        }

        CheckBox flatFurnished=(CheckBox)findViewById(R.id.captureOtherDetails_checkBox_isFlatFurnished);

        if(flatFurnished!=null && flatFurnished.isChecked()){

            itemsProvidedForFurnishedFlats.setVisibility(View.VISIBLE);

            final ScrollView scrollView=(ScrollView)findViewById(R.id.captureOtherDetails_scrollView);
            if(scrollView!=null){

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }else{

            itemsProvidedForFurnishedFlats.setVisibility(View.GONE);
        }
    }

    private void captureCheckBoxFacilities(){

        StringBuilder facilitiesProvided=new StringBuilder(" ");

        final String fourWheelerParking = "Four Wheeler Parking";
        final String twoWheelerParking = "Two Wheeler Parking";
        final String allTimeElectricity = "24*7 Electricity";
        final String laundry = "Cleaning and Washing of Clothes";
        final String freeWifi = "Free Wifi";
        final String vegString = "Veg";
        final String nonVegString = "nonVeg";
        final String northIndianString = "northIndianFood";
        final String southIndianString = "southIndianFood";

        if (isMealsCheckBoxChecked) {

            propertyDetailsVO.setMealOffered(true);

            CheckBox veg = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_veg);
            CheckBox nonVeg = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_nonVeg);
            CheckBox northIndianFood = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_northIndianFood);
            CheckBox southIndianFood = (CheckBox) findViewById(R.id.captureOtherDetails_checkBox_southIndianFood);

            if (veg != null && veg.isChecked()) {

                facilitiesProvided.append(vegString.concat(Constants.COLON));

            }
            if (nonVeg != null && nonVeg.isChecked()) {
                facilitiesProvided.append(nonVegString.concat(Constants.COLON));

            }
            if (northIndianFood != null && northIndianFood.isChecked()) {
                facilitiesProvided.append(northIndianString.concat(Constants.COLON));

            }
            if (southIndianFood != null && southIndianFood.isChecked()) {
                facilitiesProvided.append(southIndianString.concat(Constants.COLON));
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
            facilitiesProvided.append(fourWheelerParking.concat(Constants.COLON));
        }
        if (twoWheelerParkingCheckBox != null && twoWheelerParkingCheckBox.isChecked()) {
            facilitiesProvided.append(twoWheelerParking.concat(Constants.COLON));
        }
        if (allTimeElectricityCheckBox != null && allTimeElectricityCheckBox.isChecked()) {
            facilitiesProvided.append(allTimeElectricity.concat(Constants.COLON));
        }
        if (laundryCheckBox != null && laundryCheckBox.isChecked()) {
            facilitiesProvided.append(laundry.concat(Constants.COLON));
        }
        if (wiFiCheckBox != null && wiFiCheckBox.isChecked()) {
            facilitiesProvided.append(freeWifi.concat(Constants.COLON));
        }


        EditText otherFacilities = (EditText) findViewById(R.id.captureOtherDetails_editText_remainingFacilities);

        if (otherFacilities != null) {

            String otherFacility=otherFacilities.getText().toString().replace(",",":");

            facilitiesProvided.append(otherFacility);
        }

        String[] facilities=facilitiesProvided.toString().split(Constants.COLON);

        propertyDetailsVO.setFacilitiesProvided(facilitiesProvided.substring(0,facilitiesProvided.length()-1));
        propertyDetailsVO.setListOfFacilities(facilities);
    }

    private void captureFurnishedFlatsItemsProvided(){

        StringBuilder furnishedFlatFacilities=new StringBuilder("");

        CheckBox tvCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[0]);
        CheckBox fridgeCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[1]);
        CheckBox geyserCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[2]);
        CheckBox washingMachineCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[3]);
        CheckBox almirahCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[4]);
        CheckBox bedCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[5]);
        CheckBox coolerCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[6]);
        CheckBox fanCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[7]);
        CheckBox acCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[8]);
        CheckBox sofaCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[9]);
        CheckBox chairCheckBox=(CheckBox)findViewById(arrayOfIdsOfFacilitiesCheckBox[10]);

        if(tvCheckBox!=null && tvCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.TV.concat(Constants.COLON));
        }

        if(fridgeCheckBox!=null && fridgeCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.FRIDGE.concat(Constants.COLON));
        }

        if(geyserCheckBox!=null && geyserCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.GEYSER.concat(Constants.COLON));
        }

        if(washingMachineCheckBox!=null && washingMachineCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.WASHING_MACHINE.concat(Constants.COLON));
        }

        if(almirahCheckBox!=null && almirahCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.ALMIRAH.concat(Constants.COLON));
        }

        if(bedCheckBox!=null && bedCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.BED.concat(Constants.COLON));
        }

        if(coolerCheckBox!=null && coolerCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.COOLER.concat(Constants.COLON));
        }

        if(fanCheckBox!=null && fanCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.FAN.concat(Constants.COLON));
        }

        if(acCheckBox!=null && acCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.AC.concat(Constants.COLON));
        }

        if(sofaCheckBox!=null && sofaCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.SOFA.concat(Constants.COLON));
        }

        if(chairCheckBox!=null && chairCheckBox.isChecked()){

            furnishedFlatFacilities.append(Constants.CHAIR.concat(Constants.COLON));
        }

        propertyDetailsVO.setFurnishedFlatItems(furnishedFlatFacilities.substring(0,furnishedFlatFacilities.length()-1));
    }


    private void populateGridForFurnishedFacilities(){

        if(itemsProvidedForFurnishedFlats!=null){

            GridLayout.LayoutParams param ;

            for(int i=0;i<arrayOfFacilitiesName.length;i++){

                ImageView facilityImage=new ImageView(itemsProvidedForFurnishedFlats.getContext());
                facilityImage.setImageResource(arrayOfFacilitiesImages[i]);

                param = new GridLayout.LayoutParams();
                param.height = 75;
                param.width = 75;
                param.columnSpec = GridLayout.spec(0);
                param.rowSpec = GridLayout.spec(i);
                param.setGravity(Gravity.CENTER);

                facilityImage.setLayoutParams(param);

                CheckBox facilityCheckBox = new CheckBox(itemsProvidedForFurnishedFlats.getContext());
                facilityCheckBox.setText(arrayOfFacilitiesName[i]);
                facilityCheckBox.setId(View.generateViewId());
                facilityCheckBox.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                param = new GridLayout.LayoutParams();

                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.columnSpec = GridLayout.spec(1);
                param.rowSpec = GridLayout.spec(i);

                facilityCheckBox.setLayoutParams(param);

                itemsProvidedForFurnishedFlats.addView(facilityImage);
                itemsProvidedForFurnishedFlats.addView(facilityCheckBox);
                arrayOfIdsOfFacilitiesCheckBox[i]=facilityCheckBox.getId();
            }
        }
    }

    private void populateListsForFacilities(){

        arrayOfFacilitiesImages=new int[11];
        arrayOfIdsOfFacilitiesCheckBox=new int[11];
        arrayOfFacilitiesName=new String[11];

        arrayOfFacilitiesImages[0]=R.drawable.tv_icon;
        arrayOfFacilitiesImages[1]=R.drawable.fridge_icon;
        arrayOfFacilitiesImages[2]=R.drawable.gyser_icon;
        arrayOfFacilitiesImages[3]=R.drawable.washing_machine_icon;
        arrayOfFacilitiesImages[4]=R.drawable.almirah_icon;
        arrayOfFacilitiesImages[5]=R.drawable.bed_icon;
        arrayOfFacilitiesImages[6]=R.drawable.cooler_icon;
        arrayOfFacilitiesImages[7]=R.drawable.fan_icon;
        arrayOfFacilitiesImages[8]=R.drawable.ac_icon;
        arrayOfFacilitiesImages[9]=R.drawable.sofa_icon;
        arrayOfFacilitiesImages[10]=R.drawable.chair_icon;

        arrayOfFacilitiesName[0]=Constants.TV;
        arrayOfFacilitiesName[1]=Constants.FRIDGE;
        arrayOfFacilitiesName[2]=Constants.GEYSER;
        arrayOfFacilitiesName[3]=Constants.WASHING_MACHINE;
        arrayOfFacilitiesName[4]=Constants.ALMIRAH;
        arrayOfFacilitiesName[5]=Constants.BED;
        arrayOfFacilitiesName[6]=Constants.COOLER;
        arrayOfFacilitiesName[7]=Constants.FAN;
        arrayOfFacilitiesName[8]=Constants.AC;
        arrayOfFacilitiesName[9]=Constants.SOFA;
        arrayOfFacilitiesName[10]=Constants.CHAIR;

    }
}
