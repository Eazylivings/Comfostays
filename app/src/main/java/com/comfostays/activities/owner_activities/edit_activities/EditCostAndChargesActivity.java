package com.comfostays.activities.owner_activities.edit_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.owner_activities.building_setup.FinalPortfolioActivity;
import com.comfostays.activities.owner_activities.ViewPropertyActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

public class EditCostAndChargesActivity extends AppCompatActivity {

    String previousActivity="";

    PropertyDetailsVO propertyDetailsVO;
    FloorToRoomVO floorToRoomVO;
    MealScheduleVO mealScheduleVO;
    CostAndChargesVO costAndChargesVO;

    boolean isMealsScheduleAvailable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_cost_and_charges);


            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_EDIT_COST_AND_CHARGES);


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
        }catch (Exception e){

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

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }
}
