package com.comfostays.activities.owner_activities.edit_activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.activities.owner_activities.building_setup.FinalPortfolioActivity;
import com.comfostays.activities.owner_activities.ViewPropertyActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

import java.util.ArrayList;

public class EditFacilitiesProvidedActivity extends AppCompatActivity {


    private PropertyDetailsVO propertyDetailsVO;
    private FloorToRoomVO floorToRoomVO;
    private MealScheduleVO mealScheduleVO;
    private CostAndChargesVO costAndChargesVO;

    private String previousActivity="";

    private boolean isMealsScheduleAvailable=false;

    private ArrayList<String> listOfIds=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_facilities_provided);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_VIEW_PROPERTY);

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


            populateEditFacilitiesProvided();

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

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickUpdate(View view){

        try {

            String facilitiesProvidedString = "";

            for (int i = 0; i < listOfIds.size(); i++) {

                TextView textView = (TextView) findViewById(Integer.parseInt(listOfIds.get(i).split(":")[0]));

                if (textView != null) {

                    facilitiesProvidedString = facilitiesProvidedString + textView.getText().toString() + ":";
                }
            }

            TextView multiLineTextView = (TextView) findViewById(R.id.editFacilitiesProvided_edittext_otherfacilitiesProvided);

            if (multiLineTextView != null) {

                String otherFacilities = multiLineTextView.getText().toString();
                otherFacilities = otherFacilities.replace(",", ":");
                facilitiesProvidedString = facilitiesProvidedString + otherFacilities;
            }

            Intent intent;

            if (previousActivity.equalsIgnoreCase(Constants.ACTIVITY_FINAL_PORTFOLIO)) {

                String[] facilities = facilitiesProvidedString.split(":");

                intent = new Intent(getApplicationContext(), FinalPortfolioActivity.class);

                propertyDetailsVO.setListOfFacilities(facilities);
                propertyDetailsVO.setFacilitiesProvided(facilitiesProvidedString);

                intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO, propertyDetailsVO);
                intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
                intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

                if (isMealsScheduleAvailable) {
                    intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
                }

            } else {
                OwnerLocalDatabaseHandler localDatabaseHandler = new OwnerLocalDatabaseHandler(getApplicationContext(), this);
                localDatabaseHandler.updateFacilitiesProvided(facilitiesProvidedString, String.valueOf(propertyDetailsVO.getPropertyId()));

                intent = new Intent(getApplicationContext(), ViewPropertyActivity.class);
                intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID, propertyDetailsVO.getPropertyId());
            }

            startActivity(intent);
            finish();
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateEditFacilitiesProvided(){

        try {

            int screenWidth=CommonFunctionality.getScreenWidth(this);

            String[] listOfFacilitiesProvided = propertyDetailsVO.getListOfFacilities();

            final GridLayout layout=(GridLayout)findViewById(R.id.editFacilitiesProvided_gridLayout);

            if(layout!=null){

                GridLayout.LayoutParams param;

                for(int i=0;i<listOfFacilitiesProvided.length;i++) {

                    EditText facilityNameEditText = new EditText(getApplicationContext());
                    facilityNameEditText.setTextColor(Color.BLACK);
                    facilityNameEditText.setId(View.generateViewId());
                    facilityNameEditText.setText(listOfFacilitiesProvided[i]);
                    facilityNameEditText.setBackgroundResource(R.drawable.rounded_edittext);
                    facilityNameEditText.setGravity(Gravity.CENTER);

                    param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = (screenWidth*6) / 10;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(0);
                    param.rowSpec = GridLayout.spec(i);
                    param.setGravity(Gravity.CENTER);
                    facilityNameEditText.setLayoutParams(param);
                    layout.addView(facilityNameEditText);

                    final ImageView imageViewRemove = new ImageView(getApplicationContext());
                    imageViewRemove.setId(View.generateViewId());
                    imageViewRemove.setImageResource(R.drawable.ic_cancel_black_24dp);
                    param = new GridLayout.LayoutParams();
                    param.height = 100;
                    param.width = (screenWidth) / 10;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(1);
                    param.rowSpec = GridLayout.spec(i);
                    param.setGravity(Gravity.CENTER);
                    imageViewRemove.setLayoutParams(param);
                    layout.addView(imageViewRemove);

                    imageViewRemove.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            for (int i = 0; i < listOfIds.size(); i++) {

                                String[] ids = listOfIds.get(i).split(Constants.COLON);

                                if (ids[1].equalsIgnoreCase(String.valueOf(v.getId()))) {

                                    layout.removeView(findViewById(Integer.parseInt(ids[0])));
                                    layout.removeView(findViewById(Integer.parseInt(ids[1])));

                                    listOfIds.remove(i);
                                }
                            }
                        }
                    });

                    listOfIds.add(facilityNameEditText.getId() + Constants.COLON + imageViewRemove.getId());
                }
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }
}
