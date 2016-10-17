package com.comfostays.activities.buildingsetup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.MainActivity;
import com.comfostays.R;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class RoomConfigurationActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;

    List<String> listOfTypesOfRooms;

    LinkedHashMap<String,ArrayList<String>> mapOfFloorToRoomNoAndRoomType=new LinkedHashMap<>();

    boolean areAllEntriesCorrectlyMade=true;

    PropertyDetailsVO propertyDetailsVO;
    FloorToRoomVO floorToRoomVO;
    MealScheduleVO mealScheduleVO;

    boolean isMealsScheduleAvailable;

    String propertyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_room_configuration);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);
            commonFunctionality.setTitleBar(R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_ROOM_CONFIGURATION);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=(FloorToRoomVO)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);

            if(propertyDetailsVO!=null){
                isMealsScheduleAvailable =propertyDetailsVO.isMealScheduleAvailable();
                listOfTypesOfRooms=propertyDetailsVO.getTypeOfRooms();
                propertyType=propertyDetailsVO.getPropertyType();
            }else{
                listOfTypesOfRooms=new ArrayList<>();
            }

            if(isMealsScheduleAvailable){
                mealScheduleVO=(MealScheduleVO)intent.getSerializableExtra(Constants.INTENT_MEAL_SCHEDULE_VO);
            }

            setRoomConfigurationScreen();


        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
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

        commonFunctionality.onClickHomeButton();
    }

    public void onClickNext(View view){

        try {

            populateRoomNumberToRoomTypeMap();

            if(areAllEntriesCorrectlyMade){

                Intent intent = new Intent(getApplicationContext(), CostAndChargesActivity.class);
                intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO, propertyDetailsVO);
                intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

                if (isMealsScheduleAvailable) {
                    intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
                }

                startActivity(intent);
                finish();

            }else{
                commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_ENTER_ROOM_NUMBERS);
            }


        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }



    private void setRoomConfigurationScreen(){

        LinkedHashMap<String,Integer> mapFloorToNumberOfRoom=new LinkedHashMap<>();
        String stringFloorName="";
        ArrayList<String> listOfRooNumberToRoomType;

        String ROOM_NUMBER_HEADING="Room no.";
        String ROOM_TYPE="Room Type";
        String ROOM_NUMBER_HINT="Room no.";
        String OCCUPANCY="Occupancy";

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

                Set<String> set = mapFloorToNumberOfRoom.keySet();

                Iterator<String> iterator = set.iterator();

                while (iterator.hasNext()) {

                    listOfRooNumberToRoomType=new ArrayList<>();

                    stringFloorName = iterator.next();

                    int numberOfRooms = mapFloorToNumberOfRoom.get(stringFloorName);

                    TextView floorName = new TextView(getApplicationContext());
                    floorName.setText(stringFloorName);
                    floorName.setTextColor(Color.BLACK);
                    floorName.setTextAppearance(this, android.R.style.TextAppearance_Large);

                    GridLayout gridLayout = new GridLayout(getApplicationContext());
                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();

                    TextView roomNumberHeading = new TextView(getApplicationContext());
                    roomNumberHeading.setText(ROOM_NUMBER_HEADING);
                    roomNumberHeading.setTextColor(Color.BLACK);
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

                    TextView roomTypeHeading = new TextView(getApplicationContext());
                    roomTypeHeading.setText(ROOM_TYPE);
                    roomTypeHeading.setTextColor(Color.BLACK);
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

                    TextView allowedPerson = new TextView(getApplicationContext());
                    allowedPerson.setText(OCCUPANCY);
                    allowedPerson.setTextColor(Color.BLACK);
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

                        int screenWidth=commonFunctionality.getScreenWidth();

                        EditText roomNameOrNumber = new EditText(getApplicationContext());
                        roomNameOrNumber.setId(View.generateViewId());
                        roomNameOrNumber.setTextColor(Color.BLACK);
                        roomNameOrNumber.setBackgroundResource(R.drawable.rounded_edittext);
                        roomNameOrNumber.setHint(ROOM_NUMBER_HINT);
                        roomNameOrNumber.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
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

                        Spinner roomTypes = new Spinner(getApplicationContext());
                        roomTypes.setId(View.generateViewId());
                        roomTypes.setPopupBackgroundResource(R.drawable.spinner_background);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

                        Spinner numberOfPerson = new Spinner(getApplicationContext());
                        numberOfPerson.setId(View.generateViewId());
                        numberOfPerson.setPopupBackgroundResource(R.drawable.spinner_background);
                        adapterForPersons.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

                        listOfRooNumberToRoomType.add(roomNameOrNumber.getId()+":"+roomTypes.getId()+":"+numberOfPerson.getId());
                    }

                    layout.addView(floorName);
                    layout.addView(gridLayout);
                    mapOfFloorToRoomNoAndRoomType.put(stringFloorName,listOfRooNumberToRoomType);
                }
            }
        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void populateRoomNumberToRoomTypeMap(){

        LinkedHashMap<String,LinkedHashMap<String,String>> finalMapOfFloorToRoomNoAndRoomType=new LinkedHashMap<>();

        LinkedHashMap<String,Integer> roomNumberToOccupancyMap=new LinkedHashMap<>();

        Set<String> set=mapOfFloorToRoomNoAndRoomType.keySet();

        Iterator<String> iterator=set.iterator();

        while(iterator.hasNext()){

            LinkedHashMap<String,String> hashMapOfRoomNumberToRoomType=new LinkedHashMap<>();

            String floorName=iterator.next();

            ArrayList<String> listOfRoomNumberToRoomType=mapOfFloorToRoomNoAndRoomType.get(floorName);

            for(int i=0;i<listOfRoomNumberToRoomType.size();i++){

                String[] array=listOfRoomNumberToRoomType.get(i).split(":");

                int roomNumberTextViewId=Integer.valueOf(array[0]);

                int roomTypeSpinnerId=Integer.valueOf(array[1]);

                int roomOccupancyId=Integer.valueOf(array[2]);

                TextView roomNameTextView=(TextView)findViewById(roomNumberTextViewId);
                Spinner roomTypeSpinner=(Spinner)findViewById(roomTypeSpinnerId);
                Spinner roomOccupancy=(Spinner)findViewById(roomOccupancyId);

                if(roomNameTextView!=null && roomNameTextView.getText().toString().equalsIgnoreCase("")){

                    areAllEntriesCorrectlyMade=false;

                } else if(roomNameTextView!=null && roomTypeSpinner!=null && roomOccupancy!=null){

                    hashMapOfRoomNumberToRoomType.put(roomNameTextView.getText().toString(),roomTypeSpinner.getSelectedItem().toString());
                    roomNumberToOccupancyMap.put(roomNameTextView.getText().toString(),Integer.valueOf(roomOccupancy.getSelectedItem().toString().split(" ")[0]));
                }
            }

            finalMapOfFloorToRoomNoAndRoomType.put(floorName,hashMapOfRoomNumberToRoomType);
        }
        floorToRoomVO.setMapOfFloorToRoomNoAndRoomType(finalMapOfFloorToRoomNoAndRoomType);
        floorToRoomVO.setRoomToOccupancyMap(roomNumberToOccupancyMap);
    }
}
