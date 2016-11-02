package com.comfostays.activities.owner_activities.building_setup;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.GPSTracker;
import com.comfostays.R;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.Validators;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.owner_activities.MyPropertiesActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

public class AddPropertyActivity extends AppCompatActivity {

    String locationOfBuilding="";
    boolean isOneRadioButtonSelected=false;
    String previousActivity="";
    PropertyDetailsVO propertyDetailsVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_property);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_ADD_PROPERTY);

            Intent intent=getIntent();
            previousActivity=intent.getStringExtra(Constants.INTENT_PREVIOUS_ACTIVITY);
            propertyDetailsVO=new PropertyDetailsVO();



        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        if(previousActivity!=null && previousActivity.equalsIgnoreCase(Constants.ACTIVITY_WELCOME_SCREEN)){

            CommonFunctionality.onBackPressed(this,WelcomeScreen.class);

        }else if(previousActivity!=null && previousActivity.equalsIgnoreCase(Constants.ACTIVITY_MY_PROPERTY)){

            CommonFunctionality.onBackPressed(this,MyPropertiesActivity.class);
        }else{
            CommonFunctionality.onBackPressed(this,MyPropertiesActivity.class);
        }
    }

    public void onClickBackButton(View view){

        if(previousActivity!=null && previousActivity.equalsIgnoreCase(Constants.ACTIVITY_WELCOME_SCREEN)){

            CommonFunctionality.onBackPressed(this,WelcomeScreen.class);

        }else if(previousActivity!=null && previousActivity.equalsIgnoreCase(Constants.ACTIVITY_MY_PROPERTY)){

            CommonFunctionality.onBackPressed(this,MyPropertiesActivity.class);
        }else{
            CommonFunctionality.onBackPressed(this,MyPropertiesActivity.class);
        }
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickNext(View view){

        try {

            EditText buildingName = (EditText) findViewById(R.id.addProperty_editText_buildingName);
            EditText addressLineOne = (EditText) findViewById(R.id.addProperty_editText_addressLine1);
            EditText addressLineTwo = (EditText) findViewById(R.id.addProperty_editText_addressLine2);
            EditText postalCode = (EditText) findViewById(R.id.addProperty_editText_postalCode);
            EditText state = (EditText) findViewById(R.id.addProperty_editText_state);

            boolean isBuildingNameNotNull = Validators.checkEmptyInput(buildingName);
            boolean isAddressLineOneNotNull = Validators.checkEmptyInput(addressLineOne);
            boolean isAddressLineTwoNotNull = Validators.checkEmptyInput(addressLineTwo);
            boolean isPostalCodeNotNull = Validators.checkEmptyInput(postalCode);
            boolean isStateNotNull = Validators.checkEmptyInput(state);

            if(isBuildingNameNotNull && isAddressLineOneNotNull && isAddressLineTwoNotNull && isPostalCodeNotNull && isStateNotNull && isOneRadioButtonSelected) {

                OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

                int propertyId=localDatabaseHandler.getMaxPropertyId();


                propertyDetailsVO.setPropertyId(++propertyId);

                propertyDetailsVO.setPropertyName(buildingName.getText().toString());
                propertyDetailsVO.setAddressLineOne(addressLineOne.getText().toString());
                propertyDetailsVO.setAddressLineTwo(addressLineTwo.getText().toString());
                propertyDetailsVO.setPostalCode(Integer.parseInt(postalCode.getText().toString()));
                propertyDetailsVO.setState(state.getText().toString());
                propertyDetailsVO.setGeoLocation(locationOfBuilding);

                Intent intent = new Intent(getApplicationContext(), SetBuildingLayoutActivity.class);
                intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
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
        }else if(!isOneRadioButtonSelected){
                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_PROPERTY_TYPE);
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);

        }
    }

    public void onClickGetLocation(View view){

        try {

            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            ImageButton button = (ImageButton) findViewById(R.id.addProperty_imageButton_myLocation);

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

    public void onClickRadioButton(View view){

        try {

            isOneRadioButtonSelected = true;

            RadioButton pgRadioButton = (RadioButton) findViewById(R.id.addProperty_radioButton_PGs);
            RadioButton flatRadioButton = (RadioButton) findViewById(R.id.addProperty_radioButton_flat);
            RadioButton hotelRadioButton = (RadioButton) findViewById(R.id.addProperty_radioButton_hotels);

            if (pgRadioButton != null && pgRadioButton.isChecked()) {
                propertyDetailsVO.setPropertyType(Constants.PGs);

            } else if (flatRadioButton != null && flatRadioButton.isChecked()) {
                propertyDetailsVO.setPropertyType(Constants.FLAT);

            } else if (hotelRadioButton != null && hotelRadioButton.isChecked()) {
                propertyDetailsVO.setPropertyType(Constants.HOTEL);

            }
        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }
}
