package com.comfostays.activities.owner_activities.building_setup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;

import java.util.ArrayList;
import java.util.List;

public class AddMealScheduleActivity extends AppCompatActivity {

    PropertyDetailsVO propertyDetailsVO;
    FloorToRoomVO floorToRoomVO;
    MealScheduleVO mealScheduleVO;

    static final String concatString=" ~";

    boolean isTimeCorrectlyEntered=false;
    boolean isMealScheduleSuccessfullyCaptured=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_meal_schedule);

            mealScheduleVO=new MealScheduleVO();

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_SET_BUILDING_LAYOUT);
            CommonFunctionality.onClickListenerForImage(this,R.id.addProperty_imageButton_myLocation);

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=(FloorToRoomVO)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(getApplicationContext(),CaptureOtherDetailsActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

        startActivity(intent);
        finish();
    }

    public void onClickBackButton(View view){

        Intent intent=new Intent(getApplicationContext(),CaptureOtherDetailsActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickNext(View view){

        populateTimeInMealScheduleVO();

        if(isTimeCorrectlyEntered){

            populateListInMealScheduleVO();

        }

        if(isMealScheduleSuccessfullyCaptured){

            Intent intent = new Intent(getApplicationContext(), RoomConfigurationActivity.class);
            intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO, propertyDetailsVO);
            intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);

            startActivity(intent);
            finish();
        }
    }

    private void populateTimeInMealScheduleVO(){

        try {

            EditText breakfastFromTimeEditText = (EditText) findViewById(R.id.editText_fromBreakfast);
            EditText breakfastToTimeEditText = (EditText) findViewById(R.id.editText_toBreakfast);
            EditText lunchFromTimeEditText = (EditText) findViewById(R.id.editText_fromDinner);
            EditText lunchToTimeEditText = (EditText) findViewById(R.id.editText_toDinner);
            EditText dinnerFromTimeEditText = (EditText) findViewById(R.id.editText_fromLunch);
            EditText dinnerToTimeEditText = (EditText) findViewById(R.id.editText_toLunch);

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

        EditText mondayBreakfastEditText=(EditText)findViewById(R.id.editText_mondayBreakfast);
        EditText mondayLunchEditText=(EditText)findViewById(R.id.editText_mondayLunch);
        EditText mondayDinnerEditText=(EditText)findViewById(R.id.editText_mondayDinner);

        EditText tuesdayBreakfastEditText=(EditText)findViewById(R.id.editText_tuesdayBreakfast);
        EditText tuesdayLunchEditText=(EditText)findViewById(R.id.editText_tuesdayLunch);
        EditText tuesdayDinnerEditText=(EditText)findViewById(R.id.editText_tuesdayDinner);

        EditText wednesdayBreakfastEditText=(EditText)findViewById(R.id.editText_wednesdayBreakfast);
        EditText wednesdayLunchEditText=(EditText)findViewById(R.id.editText_wednesdayLunch);
        EditText wednesdayDinnerEditText=(EditText)findViewById(R.id.editText_wednesdayDinner);

        EditText thursdayBreakfastEditText=(EditText)findViewById(R.id.editText_thursdayBreakfast);
        EditText thursdayLunchEditText=(EditText)findViewById(R.id.editText_thursdayLunch);
        EditText thursdayDinnerEditText=(EditText)findViewById(R.id.editText_thursdayDinner);

        EditText fridayBreakfastEditText=(EditText)findViewById(R.id.editText_fridayBreakfast);
        EditText fridayLunchEditText=(EditText)findViewById(R.id.editText_fridayLunch);
        EditText fridayDinnerEditText=(EditText)findViewById(R.id.editText_fridayDinner);

        EditText saturdayBreakfastEditText=(EditText)findViewById(R.id.editText_saturdayBreakfast);
        EditText saturdayLunchEditText=(EditText)findViewById(R.id.editText_saturdayLunch);
        EditText saturdayDinnerEditText=(EditText)findViewById(R.id.editText_saturdayDinner);

        EditText sundayBreakfastEditText=(EditText)findViewById(R.id.editText_sundayBreakfast);
        EditText sundayLunchEditText=(EditText)findViewById(R.id.editText_sundayLunch);
        EditText sundayDinnerEditText=(EditText)findViewById(R.id.editText_sundayDinner);

        if(mondayBreakfastEditText!=null && mondayLunchEditText!=null && mondayDinnerEditText!=null
                && tuesdayBreakfastEditText!=null && tuesdayLunchEditText!=null && tuesdayDinnerEditText!=null
                && wednesdayBreakfastEditText!=null && wednesdayLunchEditText!=null && wednesdayDinnerEditText!=null
                && thursdayBreakfastEditText!=null && thursdayLunchEditText!=null && thursdayDinnerEditText!=null
                && fridayBreakfastEditText!=null && fridayLunchEditText!=null && fridayDinnerEditText!=null
                && saturdayBreakfastEditText!=null && saturdayLunchEditText!=null && saturdayDinnerEditText!=null
                && sundayBreakfastEditText!=null && sundayLunchEditText!=null && sundayDinnerEditText!=null ){

            List<String> listOfMeals=new ArrayList<>();

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

            isMealScheduleSuccessfullyCaptured=true;
        }
    }
}
