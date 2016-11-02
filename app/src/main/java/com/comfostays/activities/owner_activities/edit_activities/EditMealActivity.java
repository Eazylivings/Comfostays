package com.comfostays.activities.owner_activities.edit_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
import java.util.List;

public class EditMealActivity extends AppCompatActivity {

    private PropertyDetailsVO propertyDetailsVO;
    private FloorToRoomVO floorToRoomVO;
    private MealScheduleVO mealScheduleVO;
    private CostAndChargesVO costAndChargesVO;

    private String previousActivity="";

    private static final String concatString=" ~";

    private boolean isMealsScheduleAvailable=false;
    private boolean isTimeCorrectlyEntered=false;
    private boolean isMealScheduleSuccessfullyCaptured=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_meal);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_EDIT_MEALS_SCHEDULE);

            Intent intent=getIntent();

            previousActivity=intent.getStringExtra(Constants.INTENT_PREVIOUS_ACTIVITY);

            if(previousActivity.equalsIgnoreCase(Constants.ACTIVITY_VIEW_PROPERTY) || previousActivity.equalsIgnoreCase(Constants.ACTIVITY_WELCOME_SCREEN)){

                OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

                int propertyId=intent.getIntExtra(Constants.INTENT_SELECTED_PROPERTY_ID,0);

                mealScheduleVO=localDatabaseHandler.getMealScheduleVO(propertyId);
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

            populateTime();
            populateSchedule();

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
            intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,mealScheduleVO.getPropertyId());
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
            intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,mealScheduleVO.getPropertyId());
        }

        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickUpdate(View view){

        populateTimeInMealScheduleVO();

        if(isTimeCorrectlyEntered){

            populateListInMealScheduleVO();
        }

        if(isMealScheduleSuccessfullyCaptured){

            Intent intent;

            if(previousActivity.equalsIgnoreCase(Constants.ACTIVITY_FINAL_PORTFOLIO)){

                intent=new Intent(getApplicationContext(),FinalPortfolioActivity.class);
                intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
                intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
                intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);
                intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);

            }else{
                OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);
                localDatabaseHandler.updateMealsSchedule(mealScheduleVO);

                intent=new Intent(getApplicationContext(),ViewPropertyActivity.class);
                intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,mealScheduleVO.getPropertyId());
            }

            startActivity(intent);
            finish();
        }
    }

    private void populateTimeInMealScheduleVO(){

        try {

            EditText breakfastFromTimeEditText = (EditText) findViewById(R.id.editText_fromBreakfast);
            EditText breakfastToTimeEditText = (EditText) findViewById(R.id.editText_toBreakfast);
            EditText lunchFromTimeEditText = (EditText) findViewById(R.id.editText_fromLunch);
            EditText lunchToTimeEditText = (EditText) findViewById(R.id.editText_toLunch);
            EditText dinnerFromTimeEditText = (EditText) findViewById(R.id.editText_fromDinner);
            EditText dinnerToTimeEditText = (EditText) findViewById(R.id.editText_toDinner);

            if (breakfastFromTimeEditText != null && breakfastToTimeEditText != null && lunchFromTimeEditText != null && lunchToTimeEditText != null
                    && dinnerFromTimeEditText != null && dinnerToTimeEditText != null) {

                String breakfastFromTime = breakfastFromTimeEditText.getText().toString();
                String breakfastToTime = breakfastToTimeEditText.getText().toString();
                String lunchFromTime = lunchFromTimeEditText.getText().toString();
                String lunchToTime = lunchToTimeEditText.getText().toString();
                String dinnerFromTime = dinnerFromTimeEditText.getText().toString();
                String dinnerToTime = dinnerToTimeEditText.getText().toString();

                if (!breakfastFromTime.equalsIgnoreCase("") && !breakfastToTime.equalsIgnoreCase("") && !lunchFromTime.equalsIgnoreCase("")
                        && !lunchToTime.equalsIgnoreCase("") && !dinnerFromTime.equalsIgnoreCase("") && !dinnerToTime.equalsIgnoreCase("")) {

                    mealScheduleVO.setBreakfastFromTime(breakfastFromTime);
                    mealScheduleVO.setBreakfastToTime(breakfastToTime);
                    mealScheduleVO.setLunchFromTime(lunchFromTime);
                    mealScheduleVO.setLunchToTime(lunchToTime);
                    mealScheduleVO.setDinnerFromTime(dinnerFromTime);
                    mealScheduleVO.setDinnerToTime(dinnerToTime);

                    isTimeCorrectlyEntered = true;

                } else {

                    CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_ENTER_ALL_TIMES);
                }
            }
        }catch (Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateListInMealScheduleVO(){

        try {

            EditText mondayBreakfastEditText = (EditText) findViewById(R.id.editText_mondayBreakfast);
            EditText mondayLunchEditText = (EditText) findViewById(R.id.editText_mondayLunch);
            EditText mondayDinnerEditText = (EditText) findViewById(R.id.editText_mondayDinner);

            EditText tuesdayBreakfastEditText = (EditText) findViewById(R.id.editText_tuesdayBreakfast);
            EditText tuesdayLunchEditText = (EditText) findViewById(R.id.editText_tuesdayLunch);
            EditText tuesdayDinnerEditText = (EditText) findViewById(R.id.editText_tuesdayDinner);

            EditText wednesdayBreakfastEditText = (EditText) findViewById(R.id.editText_wednesdayBreakfast);
            EditText wednesdayLunchEditText = (EditText) findViewById(R.id.editText_wednesdayLunch);
            EditText wednesdayDinnerEditText = (EditText) findViewById(R.id.editText_wednesdayDinner);

            EditText thursdayBreakfastEditText = (EditText) findViewById(R.id.editText_thursdayBreakfast);
            EditText thursdayLunchEditText = (EditText) findViewById(R.id.editText_thursdayLunch);
            EditText thursdayDinnerEditText = (EditText) findViewById(R.id.editText_thursdayDinner);

            EditText fridayBreakfastEditText = (EditText) findViewById(R.id.editText_fridayBreakfast);
            EditText fridayLunchEditText = (EditText) findViewById(R.id.editText_fridayLunch);
            EditText fridayDinnerEditText = (EditText) findViewById(R.id.editText_fridayDinner);

            EditText saturdayBreakfastEditText = (EditText) findViewById(R.id.editText_saturdayBreakfast);
            EditText saturdayLunchEditText = (EditText) findViewById(R.id.editText_saturdayLunch);
            EditText saturdayDinnerEditText = (EditText) findViewById(R.id.editText_saturdayDinner);

            EditText sundayBreakfastEditText = (EditText) findViewById(R.id.editText_sundayBreakfast);
            EditText sundayLunchEditText = (EditText) findViewById(R.id.editText_sundayLunch);
            EditText sundayDinnerEditText = (EditText) findViewById(R.id.editText_sundayDinner);

            if (mondayBreakfastEditText != null && mondayLunchEditText != null && mondayDinnerEditText != null
                    && tuesdayBreakfastEditText != null && tuesdayLunchEditText != null && tuesdayDinnerEditText != null
                    && wednesdayBreakfastEditText != null && wednesdayLunchEditText != null && wednesdayDinnerEditText != null
                    && thursdayBreakfastEditText != null && thursdayLunchEditText != null && thursdayDinnerEditText != null
                    && fridayBreakfastEditText != null && fridayLunchEditText != null && fridayDinnerEditText != null
                    && saturdayBreakfastEditText != null && saturdayLunchEditText != null && saturdayDinnerEditText != null
                    && sundayBreakfastEditText != null && sundayLunchEditText != null && sundayDinnerEditText != null) {

                List<String> listOfMeals = new ArrayList<>();

                StringBuilder mealBuffer = new StringBuilder();

                mealBuffer.append(mondayBreakfastEditText.getText().toString().concat(concatString).concat(mondayLunchEditText.getText().toString())
                        .concat(concatString).concat(mondayDinnerEditText.getText().toString().concat(" ")));

                listOfMeals.add(mealBuffer.toString());

                mealBuffer = new StringBuilder();

                mealBuffer.append(tuesdayBreakfastEditText.getText().toString().concat(concatString).concat(tuesdayLunchEditText.getText().toString())
                        .concat(concatString).concat(tuesdayDinnerEditText.getText().toString().concat(" ")));

                listOfMeals.add(mealBuffer.toString());

                mealBuffer = new StringBuilder();

                mealBuffer.append(wednesdayBreakfastEditText.getText().toString().concat(concatString).concat(wednesdayLunchEditText.getText().toString())
                        .concat(concatString).concat(wednesdayDinnerEditText.getText().toString().concat(" ")));

                listOfMeals.add(mealBuffer.toString());

                mealBuffer = new StringBuilder();

                mealBuffer.append(thursdayBreakfastEditText.getText().toString().concat(concatString).concat(thursdayLunchEditText.getText().toString())
                        .concat(concatString).concat(thursdayDinnerEditText.getText().toString().concat(" ")));

                listOfMeals.add(mealBuffer.toString());

                mealBuffer = new StringBuilder();

                mealBuffer.append(fridayBreakfastEditText.getText().toString().concat(concatString).concat(fridayLunchEditText.getText().toString())
                        .concat(concatString).concat(fridayDinnerEditText.getText().toString().concat(" ")));

                listOfMeals.add(mealBuffer.toString());

                mealBuffer = new StringBuilder();

                mealBuffer.append(saturdayBreakfastEditText.getText().toString().concat(concatString).concat(saturdayLunchEditText.getText().toString())
                        .concat(concatString).concat(saturdayDinnerEditText.getText().toString().concat(" ")));

                listOfMeals.add(mealBuffer.toString());

                mealBuffer = new StringBuilder();

                mealBuffer.append(sundayBreakfastEditText.getText().toString().concat(concatString).concat(sundayLunchEditText.getText().toString())
                        .concat(concatString).concat(sundayDinnerEditText.getText().toString().concat(" ")));

                listOfMeals.add(mealBuffer.toString());

                mealScheduleVO.setListOfMeals(listOfMeals);

                isMealScheduleSuccessfullyCaptured = true;
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateTime(){

        try {

            EditText breakfastFromTimeEditText = (EditText) findViewById(R.id.editText_fromBreakfast);
            EditText breakfastToTimeEditText = (EditText) findViewById(R.id.editText_toBreakfast);
            EditText lunchFromTimeEditText = (EditText) findViewById(R.id.editText_fromDinner);
            EditText lunchToTimeEditText = (EditText) findViewById(R.id.editText_toDinner);
            EditText dinnerFromTimeEditText = (EditText) findViewById(R.id.editText_fromLunch);
            EditText dinnerToTimeEditText = (EditText) findViewById(R.id.editText_toLunch);

            if (breakfastFromTimeEditText != null && breakfastToTimeEditText != null && lunchFromTimeEditText != null && lunchToTimeEditText != null
                    && dinnerFromTimeEditText != null && dinnerToTimeEditText != null && mealScheduleVO != null) {

                breakfastFromTimeEditText.setText(mealScheduleVO.getBreakfastFromTime());
                breakfastToTimeEditText.setText(mealScheduleVO.getBreakfastToTime());
                lunchFromTimeEditText.setText(mealScheduleVO.getLunchFromTime());
                lunchToTimeEditText.setText(mealScheduleVO.getLunchToTime());
                dinnerFromTimeEditText.setText(mealScheduleVO.getDinnerFromTime());
                dinnerToTimeEditText.setText(mealScheduleVO.getDinnerToTime());

            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateSchedule() {

        try {

            EditText mondayBreakfastEditText = (EditText) findViewById(R.id.editText_mondayBreakfast);
            EditText mondayLunchEditText = (EditText) findViewById(R.id.editText_mondayLunch);
            EditText mondayDinnerEditText = (EditText) findViewById(R.id.editText_mondayDinner);

            EditText tuesdayBreakfastEditText = (EditText) findViewById(R.id.editText_tuesdayBreakfast);
            EditText tuesdayLunchEditText = (EditText) findViewById(R.id.editText_tuesdayLunch);
            EditText tuesdayDinnerEditText = (EditText) findViewById(R.id.editText_tuesdayDinner);

            EditText wednesdayBreakfastEditText = (EditText) findViewById(R.id.editText_wednesdayBreakfast);
            EditText wednesdayLunchEditText = (EditText) findViewById(R.id.editText_wednesdayLunch);
            EditText wednesdayDinnerEditText = (EditText) findViewById(R.id.editText_wednesdayDinner);

            EditText thursdayBreakfastEditText = (EditText) findViewById(R.id.editText_thursdayBreakfast);
            EditText thursdayLunchEditText = (EditText) findViewById(R.id.editText_thursdayLunch);
            EditText thursdayDinnerEditText = (EditText) findViewById(R.id.editText_thursdayDinner);

            EditText fridayBreakfastEditText = (EditText) findViewById(R.id.editText_fridayBreakfast);
            EditText fridayLunchEditText = (EditText) findViewById(R.id.editText_fridayLunch);
            EditText fridayDinnerEditText = (EditText) findViewById(R.id.editText_fridayDinner);

            EditText saturdayBreakfastEditText = (EditText) findViewById(R.id.editText_saturdayBreakfast);
            EditText saturdayLunchEditText = (EditText) findViewById(R.id.editText_saturdayLunch);
            EditText saturdayDinnerEditText = (EditText) findViewById(R.id.editText_saturdayDinner);

            EditText sundayBreakfastEditText = (EditText) findViewById(R.id.editText_sundayBreakfast);
            EditText sundayLunchEditText = (EditText) findViewById(R.id.editText_sundayLunch);
            EditText sundayDinnerEditText = (EditText) findViewById(R.id.editText_sundayDinner);

            if (mondayBreakfastEditText != null && mondayLunchEditText != null && mondayDinnerEditText != null
                    && tuesdayBreakfastEditText != null && tuesdayLunchEditText != null && tuesdayDinnerEditText != null
                    && wednesdayBreakfastEditText != null && wednesdayLunchEditText != null && wednesdayDinnerEditText != null
                    && thursdayBreakfastEditText != null && thursdayLunchEditText != null && thursdayDinnerEditText != null
                    && fridayBreakfastEditText != null && fridayLunchEditText != null && fridayDinnerEditText != null
                    && saturdayBreakfastEditText != null && saturdayLunchEditText != null && saturdayDinnerEditText != null
                    && sundayBreakfastEditText != null && sundayLunchEditText != null && sundayDinnerEditText != null && mealScheduleVO != null) {

                List<String> list = mealScheduleVO.getListOfMeals();

                String[] array = list.get(0).split("~");

                mondayBreakfastEditText.setText(array[0]);
                mondayLunchEditText.setText(array[1]);
                mondayDinnerEditText.setText(array[2]);

                array = list.get(1).split("~");

                tuesdayBreakfastEditText.setText(array[0]);
                tuesdayLunchEditText.setText(array[1]);
                tuesdayDinnerEditText.setText(array[2]);

                array = list.get(2).split("~");

                wednesdayBreakfastEditText.setText(array[0]);
                wednesdayLunchEditText.setText(array[1]);
                wednesdayDinnerEditText.setText(array[2]);

                array = list.get(3).split("~");

                thursdayBreakfastEditText.setText(array[0]);
                thursdayLunchEditText.setText(array[1]);
                thursdayDinnerEditText.setText(array[2]);

                array = list.get(4).split("~");

                fridayBreakfastEditText.setText(array[0]);
                fridayLunchEditText.setText(array[1]);
                fridayDinnerEditText.setText(array[2]);

                array = list.get(5).split("~");

                saturdayBreakfastEditText.setText(array[0]);
                saturdayLunchEditText.setText(array[1]);
                saturdayDinnerEditText.setText(array[2]);

                array = list.get(6).split("~");

                sundayBreakfastEditText.setText(array[0]);
                sundayLunchEditText.setText(array[1]);
                sundayDinnerEditText.setText(array[2]);
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }

    }
}
