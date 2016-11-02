package com.comfostays.activities.owner_activities.building_setup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.activities.owner_activities.building_setup.AddCostAndChargesActivity;
import com.comfostays.activities.owner_activities.building_setup.CaptureOtherDetailsActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoomConfigurationActivity extends AppCompatActivity {

    private String[] listOfTypesOfRooms;

    private LinkedHashMap<String,String[]> mapOfFloorToRoomNoAndRoomType=new LinkedHashMap<>();

    private boolean areAllEntriesCorrectlyMade=true;

    private PropertyDetailsVO propertyDetailsVO;
    private FloorToRoomVO floorToRoomVO;
    private MealScheduleVO mealScheduleVO;

    private boolean isMealsScheduleAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_room_configuration);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_ROOM_CONFIGURATION);

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=(FloorToRoomVO)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);

            if(propertyDetailsVO!=null){
                isMealsScheduleAvailable =propertyDetailsVO.isMealScheduleAvailable();
                listOfTypesOfRooms=propertyDetailsVO.getTypeOfRooms();
            }

            if(isMealsScheduleAvailable){
                mealScheduleVO=(MealScheduleVO)intent.getSerializableExtra(Constants.INTENT_MEAL_SCHEDULE_VO);
            }

            setRoomConfigurationScreen();


        }catch(Exception e){

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

        try {

            populateRoomNumberToRoomTypeMap();

            if(areAllEntriesCorrectlyMade){

                Intent intent = new Intent(getApplicationContext(), AddCostAndChargesActivity.class);
                intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO, propertyDetailsVO);
                intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

                if (isMealsScheduleAvailable) {
                    intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
                }

                startActivity(intent);
                finish();

            }else{
                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_ENTER_ROOM_NUMBERS);
            }


        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void setRoomConfigurationScreen(){

        LinkedHashMap<String,Integer> mapFloorToNumberOfRoom=new LinkedHashMap<>();

        String[] roomNumberToRoomType;

        final String ROOM_NUMBER_HEADING="Room no.";
        final String ROOM_TYPE="Room Type";
        final String ROOM_NUMBER_HINT="Room no.";
        final String OCCUPANCY="Occupancy";

        ArrayList<String> numberOfPersonList=new ArrayList<>();
        for(int i=1;i<11;i++){

            if(i==1){

                numberOfPersonList.add(i+" Person");
            }else{

                numberOfPersonList.add(i +" Persons");
            }
        }

        try {

            if(floorToRoomVO!=null){

                mapFloorToNumberOfRoom= floorToRoomVO.getFloorToNumberOfRoomsMap();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, listOfTypesOfRooms);

            ArrayAdapter<String> adapterForPersons = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, numberOfPersonList);

            LinearLayout layout = (LinearLayout) findViewById(R.id.roomConfiguration_linearLayout);

            if (layout != null && mapFloorToNumberOfRoom != null) {

                for(Map.Entry<String,Integer> entry : mapFloorToNumberOfRoom.entrySet()){

                    int numberOfRooms = entry.getValue();

                    roomNumberToRoomType=new String[numberOfRooms];

                    GridLayout gridLayout = new GridLayout(getApplicationContext());
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();

                    TextView floorName = new TextView(gridLayout.getContext());
                    floorName.setText( entry.getKey());
                    floorName.setTextAppearance(this, android.R.style.TextAppearance_Large);

                    TextView roomNumberHeading = new TextView(gridLayout.getContext());
                    roomNumberHeading.setText(ROOM_NUMBER_HEADING);
                    roomNumberHeading.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.rightMargin = 15;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(0);
                    param.rowSpec = GridLayout.spec(0);
                    param.setGravity(Gravity.CENTER_HORIZONTAL);

                    roomNumberHeading.setLayoutParams(param);
                    gridLayout.addView(roomNumberHeading);

                    TextView roomTypeHeading = new TextView(gridLayout.getContext());
                    roomTypeHeading.setText(ROOM_TYPE);
                    roomTypeHeading.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                    param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.rightMargin = 5;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(1);
                    param.rowSpec = GridLayout.spec(0);
                    param.setGravity(Gravity.START);

                    roomTypeHeading.setLayoutParams(param);
                    gridLayout.addView(roomTypeHeading);

                    TextView allowedPerson = new TextView(gridLayout.getContext());
                    allowedPerson.setText(OCCUPANCY);
                    allowedPerson.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                    param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(2);
                    param.rowSpec = GridLayout.spec(0);
                    param.setGravity(Gravity.START);

                    allowedPerson.setLayoutParams(param);
                    gridLayout.addView(allowedPerson);

                    for (int i = 1; i <= numberOfRooms; i++) {

                        int screenWidth=CommonFunctionality.getScreenWidth(this);

                        EditText roomNameOrNumber = new EditText(gridLayout.getContext());
                        roomNameOrNumber.setId(View.generateViewId());
                        roomNameOrNumber.setBackgroundResource(R.drawable.rounded_edittext);
                        roomNameOrNumber.setTextColor(Color.BLACK);
                        roomNameOrNumber.setHint(ROOM_NUMBER_HINT);
                        roomNameOrNumber.setHintTextColor(Color.LTGRAY);
                        roomNameOrNumber.setGravity(Gravity.CENTER);

                        param = new GridLayout.LayoutParams();
                        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                        param.width = (screenWidth*2)/10;
                        param.rightMargin = 5;
                        param.topMargin = 5;
                        param.columnSpec = GridLayout.spec(0);
                        param.rowSpec = GridLayout.spec(i);
                        param.setGravity(Gravity.CENTER_HORIZONTAL);

                        roomNameOrNumber.setLayoutParams(param);
                        gridLayout.addView(roomNameOrNumber);

                        Spinner roomTypes = new Spinner(gridLayout.getContext());
                        roomTypes.setId(View.generateViewId());
                        //roomTypes.setPopupBackgroundResource(R.drawable.spinner_background);
                        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        roomTypes.setAdapter(adapter);
                        roomTypes.setGravity(Gravity.CENTER);

                        param = new GridLayout.LayoutParams();
                        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                        param.width = (screenWidth*7)/20;
                        param.rightMargin = 5;
                        param.topMargin = 5;
                        param.columnSpec = GridLayout.spec(1);
                        param.rowSpec = GridLayout.spec(i);
                        param.setGravity(Gravity.CENTER_HORIZONTAL);

                        roomTypes.setLayoutParams(param);
                        gridLayout.addView(roomTypes);

                        Spinner numberOfPerson = new Spinner(gridLayout.getContext());
                        numberOfPerson.setId(View.generateViewId());
                        //adapterForPersons.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        numberOfPerson.setAdapter(adapterForPersons);
                        numberOfPerson.setGravity(Gravity.CENTER);

                        param = new GridLayout.LayoutParams();
                        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                        param.width = (screenWidth * 3) / 10;
                        param.topMargin = 5;
                        param.columnSpec = GridLayout.spec(2);
                        param.rowSpec = GridLayout.spec(i);
                        param.setGravity(Gravity.CENTER_HORIZONTAL);

                        numberOfPerson.setLayoutParams(param);

                        gridLayout.addView(numberOfPerson);

                        roomNumberToRoomType[i-1] = roomNameOrNumber.getId()+Constants.COLON+roomTypes.getId()+Constants.COLON+numberOfPerson.getId();
                    }

                    layout.addView(floorName);
                    layout.addView(gridLayout);
                    mapOfFloorToRoomNoAndRoomType.put(entry.getKey(),roomNumberToRoomType);
                }
            }
        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateRoomNumberToRoomTypeMap(){

        try {

            LinkedHashMap<String, LinkedHashMap<String, String>> finalMapOfFloorToRoomNoAndRoomType = new LinkedHashMap<>();

            LinkedHashMap<String, Integer> roomNumberToOccupancyMap = new LinkedHashMap<>();


            for (Map.Entry<String, String[]> entry : mapOfFloorToRoomNoAndRoomType.entrySet()) {

                LinkedHashMap<String, String> hashMapOfRoomNumberToRoomType = new LinkedHashMap<>();

                String floorName = entry.getKey();

                String[] arrayOfRoomNumberToRoomType = entry.getValue();

                for (String roomNumberToRoomType : arrayOfRoomNumberToRoomType) {

                    String[] array = roomNumberToRoomType.split(Constants.COLON);

                    int roomNumberTextViewId = Integer.valueOf(array[0]);

                    int roomTypeSpinnerId = Integer.valueOf(array[1]);

                    int roomOccupancyId = Integer.valueOf(array[2]);

                    TextView roomNameTextView = (TextView) findViewById(roomNumberTextViewId);
                    Spinner roomTypeSpinner = (Spinner) findViewById(roomTypeSpinnerId);
                    Spinner roomOccupancy = (Spinner) findViewById(roomOccupancyId);

                    if (roomNameTextView != null && roomNameTextView.getText().toString().equalsIgnoreCase("")) {

                        areAllEntriesCorrectlyMade = false;

                    } else if (roomNameTextView != null && roomTypeSpinner != null && roomOccupancy != null) {

                        hashMapOfRoomNumberToRoomType.put(roomNameTextView.getText().toString(), roomTypeSpinner.getSelectedItem().toString());
                        roomNumberToOccupancyMap.put(roomNameTextView.getText().toString(), Integer.valueOf(roomOccupancy.getSelectedItem().toString().split(" ")[0]));
                    }
                }

                finalMapOfFloorToRoomNoAndRoomType.put(floorName, hashMapOfRoomNumberToRoomType);

            }

            floorToRoomVO.setMapOfFloorToRoomNoAndRoomType(finalMapOfFloorToRoomNoAndRoomType);
            floorToRoomVO.setRoomToOccupancyMap(roomNumberToOccupancyMap);

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);


        }
    }
}
