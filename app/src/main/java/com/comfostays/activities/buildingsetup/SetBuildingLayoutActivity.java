package com.comfostays.activities.buildingsetup;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.PropertyDetailsVO;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SetBuildingLayoutActivity extends AppCompatActivity {

    int count=0;
    int screenWidth;

    private CommonFunctionality commonFunctionality;
    private GridLayout layout;

    private ArrayList<String> listOfIds;
    ArrayList<String> floorNameList;

    private PropertyDetailsVO propertyDetailsVO;
    private FloorToRoomVO floorToRoomVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_set_building_layout);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);
            commonFunctionality.setTitleBar(R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_SET_BUILDING_LAYOUT);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            screenWidth = commonFunctionality.getScreenWidth();
            populateListOfFloors();

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=new FloorToRoomVO();
            layout=(GridLayout)findViewById(R.id.setBuildingLayout_gridLayout_floorsAndNumberOfRooms);
            listOfIds=new ArrayList<>();

        }catch(Exception e){
            commonFunctionality.generatePopupMessage(Constants.ALERT_EXCEPTION,Constants.EXCEPTION_SET_BUILDING_LAYOUT_ACTIVITY);
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(getApplicationContext(),AddPropertyActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.EXCEPTION_SET_BUILDING_LAYOUT_ACTIVITY);

        startActivity(intent);
        finish();
    }

    public void onClickBackButton(View view){

        Intent intent=new Intent(getApplicationContext(),AddPropertyActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.EXCEPTION_SET_BUILDING_LAYOUT_ACTIVITY);

        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onClickHomeButton();
    }

    public void onClickAddFloor(View view){

        try {

            String floorNameTextViewText = floorNameList.get(count);

            TextView floorNameTextView = new TextView(getApplicationContext());
            floorNameTextView.setTextColor(Color.BLACK);
            floorNameTextView.setId(View.generateViewId());
            floorNameTextView.setText(floorNameTextViewText);
            floorNameTextView.setGravity(Gravity.CENTER);
            floorNameTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(0);
            param.rowSpec = GridLayout.spec(count);
            param.setGravity(Gravity.CENTER);
            floorNameTextView.setLayoutParams(param);

            ImageView floorImageView = new ImageView(getApplicationContext());
            floorImageView.setId(View.generateViewId());
            floorImageView.setImageResource(R.drawable.floor_icon);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = (screenWidth) / 10;
            param.leftMargin = (screenWidth) / 80;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(1);
            param.rowSpec = GridLayout.spec(count);
            param.setGravity(Gravity.CENTER);
            floorImageView.setLayoutParams(param);

            EditText numberOfRoomsEditText = new EditText(getApplicationContext());
            numberOfRoomsEditText.setId(View.generateViewId());
            numberOfRoomsEditText.setTextColor(Color.BLACK);
            numberOfRoomsEditText.setBackgroundResource(R.drawable.rounded_edittext);
            numberOfRoomsEditText.setHint(Constants.HINT_NO_OF_ROOMS);
            numberOfRoomsEditText.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
            numberOfRoomsEditText.setGravity(Gravity.CENTER);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = (screenWidth * 3) / 10;
            param.leftMargin = (screenWidth) / 40;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(2);
            param.rowSpec = GridLayout.spec(count);
            param.setGravity(Gravity.CENTER);
            numberOfRoomsEditText.setLayoutParams(param);

            final ImageView imageViewRemove = new ImageView(getApplicationContext());
            imageViewRemove.setId(View.generateViewId());
            imageViewRemove.setImageResource(R.drawable.ic_cancel_black_24dp);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = (screenWidth) / 10;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(3);
            param.rowSpec = GridLayout.spec(count);
            param.setGravity(Gravity.CENTER);
            imageViewRemove.setLayoutParams(param);

            imageViewRemove.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    boolean isRemoved = false;

                    for (int i = 0; i < listOfIds.size(); i++) {

                        String[] ids = listOfIds.get(i).split(Constants.COLON);

                        if (ids[3].equalsIgnoreCase(String.valueOf(v.getId())) && i == (listOfIds.size() - 1)) {

                            layout.removeView(findViewById(Integer.parseInt(ids[0])));
                            layout.removeView(findViewById(Integer.parseInt(ids[1])));
                            layout.removeView(findViewById(Integer.parseInt(ids[2])));
                            layout.removeView(findViewById(Integer.parseInt(ids[3])));

                            --count;
                            isRemoved = true;
                            listOfIds.remove(i);

                            String message = Constants.FLOOR + (count) + Constants.POPUP_MESSAGE_FLOOR_REMOVED;
                            Snackbar snackBar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
                            snackBar.show();
                        }
                    }

                    if (!isRemoved) {
                        commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_FLOOR_NOT_REMOVED);
                    }
                }
            });

            if (layout != null) {
                layout.addView(floorNameTextView);
                layout.addView(floorImageView);
                layout.addView(numberOfRoomsEditText);
                layout.addView(imageViewRemove);
            }

            listOfIds.add(floorNameTextView.getId() + Constants.COLON +  floorImageView.getId() + Constants.COLON
                            + numberOfRoomsEditText.getId() + Constants.COLON + imageViewRemove.getId());
            count++;
        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    public void onClickContinueButton(View view){

        try {


            boolean allValidated = true;

            if (listOfIds.size() == 0) {
                commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_ENTER_ROOM_NUMBERS);
            } else {

                LinkedHashMap<String, Integer> mapFloorToNumberOfRooms = new LinkedHashMap<>();

                for (int i = 0; i < listOfIds.size(); i++) {

                    TextView textView = (TextView) findViewById(Integer.valueOf(listOfIds.get(i).split(Constants.COLON)[0]));
                    EditText editText = (EditText) findViewById(Integer.valueOf(listOfIds.get(i).split(Constants.COLON)[2]));

                    if (textView != null && editText != null) {

                        String floorName = textView.getText().toString();
                        String numberOfFloors = (editText).getText().toString();

                        if (numberOfFloors.equalsIgnoreCase("") || Integer.valueOf(numberOfFloors) < 0) {
                            commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_ENTER_ROOM_NUMBERS);
                            allValidated = false;
                            break;
                        } else {
                            mapFloorToNumberOfRooms.put(floorName, Integer.valueOf(numberOfFloors));
                        }
                    }
                }

                if (allValidated) {

                    floorToRoomVO.setFloorToNumberOfRoomsMap(mapFloorToNumberOfRooms);

                    Intent intent = new Intent(getApplicationContext(), CaptureOtherDetailsActivity.class);
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

    private void populateListOfFloors(){

        floorNameList=new ArrayList<>();

        floorNameList.add("GROUND FLOOR ");
        floorNameList.add("1st FLOOR ");
        floorNameList.add("2nd FLOOR ");
        floorNameList.add("3rd FLOOR ");
        floorNameList.add("4th FLOOR ");
        floorNameList.add("5th FLOOR ");
        floorNameList.add("6th FLOOR ");
        floorNameList.add("7th FLOOR ");
        floorNameList.add("8th FLOOR ");
        floorNameList.add("9th FLOOR ");
        floorNameList.add("10th FLOOR ");
        floorNameList.add("11th FLOOR ");
        floorNameList.add("12th FLOOR ");
        floorNameList.add("13th FLOOR ");
        floorNameList.add("14th FLOOR ");
        floorNameList.add("15th FLOOR ");
        floorNameList.add("16th FLOOR ");
        floorNameList.add("17th FLOOR ");
        floorNameList.add("18th FLOOR ");
        floorNameList.add("19th FLOOR ");
        floorNameList.add("20th FLOOR ");
        floorNameList.add("21st FLOOR ");
        floorNameList.add("22nd FLOOR ");
        floorNameList.add("23rd FLOOR ");
        floorNameList.add("24th FLOOR ");
        floorNameList.add("25th FLOOR ");
        floorNameList.add("26th FLOOR ");
        floorNameList.add("27th FLOOR ");
        floorNameList.add("28th FLOOR ");
        floorNameList.add("29th FLOOR ");
        floorNameList.add("30th FLOOR ");


    }
}
