package com.comfostays.activities.owner_activities.edit_activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.GPSTracker;
import com.comfostays.R;
import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.Validators;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.owner_activities.building_setup.FinalPortfolioActivity;
import com.comfostays.activities.owner_activities.ViewPropertyActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

public class EditBuildingDetailsActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;

    private PropertyDetailsVO propertyDetailsVO;
    private FloorToRoomVO floorToRoomVO;
    private MealScheduleVO mealScheduleVO;
    private CostAndChargesVO costAndChargesVO;

    private String locationOfBuilding="";
    private String previousActivity;

    private boolean isMealsScheduleAvailable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_building_details);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            sharedPreference=new SharedPreference(getApplicationContext());
            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_EDIT_BUILDING_DETAILS);

            Intent intent=getIntent();

            previousActivity=intent.getStringExtra(Constants.INTENT_PREVIOUS_ACTIVITY);

            if(previousActivity.equalsIgnoreCase(Constants.ACTIVITY_VIEW_PROPERTY)){

                OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

                int propertyId=intent.getIntExtra(Constants.INTENT_SELECTED_PROPERTY_ID,0);

                propertyDetailsVO=localDatabaseHandler.getPropertyDetailsVO(propertyId);
            }

            else {
                propertyDetailsVO = (PropertyDetailsVO) intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);

                floorToRoomVO = (FloorToRoomVO) intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);
                costAndChargesVO = (CostAndChargesVO) intent.getSerializableExtra(Constants.INTENT_COST_AND_CHARGES_VO);

                if (propertyDetailsVO != null) {

                    isMealsScheduleAvailable = propertyDetailsVO.isMealScheduleAvailable();
                }
                if (isMealsScheduleAvailable) {

                    mealScheduleVO = (MealScheduleVO) intent.getSerializableExtra(Constants.INTENT_MEAL_SCHEDULE_VO);
                }
            }

            populateInputFields();

        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent;

        if(previousActivity.equalsIgnoreCase(Constants.ACTIVITY_FINAL_PORTFOLIO)){

            intent=new Intent(getApplicationContext(),FinalPortfolioActivity.class);
            intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
            intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
            intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

            if (isMealsScheduleAvailable) {
                intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
            }

        }else{

            intent=new Intent(getApplicationContext(),ViewPropertyActivity.class);
            intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,propertyDetailsVO.getPropertyId());
        }

        startActivity(intent);
        finish();
    }

    public void onClickBackButton(View view){

        if(previousActivity.equalsIgnoreCase(Constants.ACTIVITY_FINAL_PORTFOLIO)){

            Intent intent=new Intent(getApplicationContext(),FinalPortfolioActivity.class);
            intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
            intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
            intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

            if (isMealsScheduleAvailable) {
                intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
            }

            startActivity(intent);
            finish();

        }else{

            Intent intent=new Intent(getApplicationContext(),ViewPropertyActivity.class);
            intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,propertyDetailsVO.getPropertyId());

            startActivity(intent);
            finish();
        }
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }


    public void onClickUpdate(View view){

        try {

            EditText buildingName = (EditText) findViewById(R.id.editText_buildingName);
            EditText addressLineOne = (EditText) findViewById(R.id.editText_addressLine1);
            EditText addressLineTwo = (EditText) findViewById(R.id.editText_addressLine2);
            EditText postalCode = (EditText) findViewById(R.id.editText_postalCode);
            EditText state = (EditText) findViewById(R.id.editText_state);

            boolean isBuildingNameNotNull = Validators.checkEmptyInput(buildingName);
            boolean isAddressLineOneNotNull = Validators.checkEmptyInput(addressLineOne);
            boolean isAddressLineTwoNotNull = Validators.checkEmptyInput(addressLineTwo);
            boolean isPostalCodeNotNull = Validators.checkEmptyInput(postalCode);
            boolean isStateNotNull = Validators.checkEmptyInput(state);


            if(isBuildingNameNotNull && isAddressLineOneNotNull && isAddressLineTwoNotNull && isPostalCodeNotNull && isStateNotNull) {

                sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_ADD_PROPERTY_BUILDING_NAME,buildingName.getText().toString());
                propertyDetailsVO.setPropertyName(buildingName.getText().toString());
                propertyDetailsVO.setAddressLineOne(addressLineOne.getText().toString());
                propertyDetailsVO.setAddressLineTwo(addressLineTwo.getText().toString());
                propertyDetailsVO.setPostalCode(Integer.parseInt(postalCode.getText().toString()));
                propertyDetailsVO.setState(state.getText().toString());
                propertyDetailsVO.setGeoLocation(locationOfBuilding);

                Intent intent;

                if(previousActivity.equalsIgnoreCase(Constants.ACTIVITY_FINAL_PORTFOLIO)){

                    intent=new Intent(getApplicationContext(),FinalPortfolioActivity.class);
                    intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
                    intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
                    intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

                    if (isMealsScheduleAvailable) {
                        intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
                    }

                }else{
                    OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);
                    localDatabaseHandler.updateBuildingDetails(propertyDetailsVO);

                    intent=new Intent(getApplicationContext(),ViewPropertyActivity.class);
                    intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,propertyDetailsVO.getPropertyId());
                }

                startActivity(intent);
                finish();
            }else if(!isBuildingNameNotNull){

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_BUILDING_NAME);
            }else if(!isAddressLineOneNotNull){

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_ADDRESS_LINE_ONE);
            }else if(!isAddressLineTwoNotNull){

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_ADDRESS_LINE_TWO);
            }else if(!isPostalCodeNotNull){

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_POSTAL_CODE);
            }else if(!isStateNotNull){

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_STATE);
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);

        }
    }

    private void populateInputFields(){

        try {

            EditText buildingName = (EditText) findViewById(R.id.editText_buildingName);
            EditText addressLineOne = (EditText) findViewById(R.id.editText_addressLine1);
            EditText addressLineTwo = (EditText) findViewById(R.id.editText_addressLine2);
            EditText postalCode = (EditText) findViewById(R.id.editText_postalCode);
            EditText state = (EditText) findViewById(R.id.editText_state);
            RadioButton pgRadioButton = (RadioButton) findViewById(R.id.radioButton_PGs);
            RadioButton flatRadioButton = (RadioButton) findViewById(R.id.radioButton_flat);
            RadioButton hotelRadioButton = (RadioButton) findViewById(R.id.radioButton_hotels);

            if (buildingName != null && addressLineOne != null && addressLineTwo != null && postalCode != null && state != null && propertyDetailsVO != null) {

                buildingName.setText(propertyDetailsVO.getPropertyName());
                addressLineOne.setText(propertyDetailsVO.getAddressLineOne());
                addressLineTwo.setText(propertyDetailsVO.getAddressLineTwo());
                postalCode.setText(String.valueOf(propertyDetailsVO.getPostalCode()));
                state.setText(propertyDetailsVO.getState());
            }

            if(pgRadioButton != null && flatRadioButton != null && hotelRadioButton != null && propertyDetailsVO!=null) {

                pgRadioButton.setEnabled(false);
                flatRadioButton.setEnabled(false);
                hotelRadioButton.setEnabled(false);


                if (propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.PGs)) {

                    pgRadioButton.setChecked(true);

                } else if (propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.FLAT)) {

                    flatRadioButton.setChecked(true);

                } else {

                    hotelRadioButton.setChecked(true);
                }
            }
        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    public void onClickGetLocation(View view){

        try {

            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            ImageButton button = (ImageButton) findViewById(R.id.imageButton_myLocation);

            GPSTracker mGPS = new GPSTracker(this);
            if (mGPS.canGetLocation) {
                mGPS.getLocation();
                mGPS.getLatitude();
                mGPS.getLongitude();
                locationOfBuilding = locationOfBuilding + mGPS.getLatitude() + ":" + mGPS.getLongitude();
                if (coordinatorLayout != null && button != null) {

                    button.setColorFilter(Color.BLACK);
                    Snackbar snackBar = Snackbar.make(coordinatorLayout, Constants.POPUP_MESSAGE_GPS_TRACK_SUCCESS, Snackbar.LENGTH_LONG);
                    snackBar.show();
                }
            } else {
                if (coordinatorLayout != null) {

                    Snackbar snackBar = Snackbar.make(coordinatorLayout, Constants.POPUP_MESSAGE_GPS_TRACK_FAIL, Snackbar.LENGTH_LONG);
                    snackBar.show();
                }
            }
        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

}
