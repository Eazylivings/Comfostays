package com.comfostays.activities.owner_activities.building_setup;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
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
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.PropertyDetailsVO;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SetBuildingLayoutActivity extends AppCompatActivity {

    private int count=0;
    private int screenWidth;

    private GridLayout layout;

    private ArrayList<String> listOfIds;

    private String[] floorName;

    private PropertyDetailsVO propertyDetailsVO;
    private FloorToRoomVO floorToRoomVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_set_building_layout);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_SET_BUILDING_LAYOUT);

            screenWidth = CommonFunctionality.getScreenWidth(this);
            populateListOfFloors();

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=new FloorToRoomVO();
            layout=(GridLayout)findViewById(R.id.setBuildingLayout_gridLayout_floorsAndNumberOfRooms);
            listOfIds=new ArrayList<>();

        }catch(Exception e){
            CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EXCEPTION,Constants.EXCEPTION_SET_BUILDING_LAYOUT_ACTIVITY);
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

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickAddFloor(View view){

        try {

            String floorNameTextViewText = floorName[count];

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
                        CommonFunctionality.generatePopupMessage(SetBuildingLayoutActivity.this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_FLOOR_NOT_REMOVED);
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

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    public void onClickContinueButton(View view){

        try {


            boolean allValidated = true;

            if (listOfIds.size() == 0) {
                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_ENTER_ROOM_NUMBERS);
            } else {

                LinkedHashMap<String, Integer> mapFloorToNumberOfRooms = new LinkedHashMap<>();

                for (int i = 0; i < listOfIds.size(); i++) {

                    TextView textView = (TextView) findViewById(Integer.valueOf(listOfIds.get(i).split(Constants.COLON)[0]));
                    EditText editText = (EditText) findViewById(Integer.valueOf(listOfIds.get(i).split(Constants.COLON)[2]));

                    if (textView != null && editText != null) {

                        String floorName = textView.getText().toString();
                        String numberOfFloors = (editText).getText().toString();

                        if (numberOfFloors.equalsIgnoreCase("") || Integer.valueOf(numberOfFloors) < 0 ) {

                            CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_ENTER_ROOM_NUMBERS);
                            allValidated = false;
                            break;

                        } else if(Integer.valueOf(numberOfFloors) > 26){
                            CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_ENTER_ROOM_NUMBERS_LESS_THAN_30);
                            allValidated = false;
                            break;

                        }else{
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

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateListOfFloors(){

        floorName=new String[31];

        floorName[0]="GROUND FLOOR ";
        floorName[1]="1st FLOOR ";
        floorName[2]="2nd FLOOR ";
        floorName[3]="3rd FLOOR ";
        floorName[4]="4th FLOOR ";
        floorName[5]="5th FLOOR ";
        floorName[6]="6th FLOOR ";
        floorName[7]="7th FLOOR ";
        floorName[8]="8th FLOOR ";
        floorName[9]="9th FLOOR ";
        floorName[10]="10th FLOOR ";
        floorName[11]="11th FLOOR ";
        floorName[12]="12th FLOOR ";
        floorName[13]="13th FLOOR ";
        floorName[14]="14th FLOOR ";
        floorName[15]="15th FLOOR ";
        floorName[16]="16th FLOOR ";
        floorName[17]="17th FLOOR ";
        floorName[18]="18th FLOOR ";
        floorName[19]="19th FLOOR ";
        floorName[20]="20th FLOOR ";
        floorName[21]="21st FLOOR ";
        floorName[22]="22nd FLOOR ";
        floorName[23]="23rd FLOOR ";
        floorName[24]="24th FLOOR ";
        floorName[25]="25th FLOOR ";
        floorName[26]="26th FLOOR ";
        floorName[27]="27th FLOOR ";
        floorName[28]="28th FLOOR ";
        floorName[29]="29th FLOOR ";
        floorName[30]="30th FLOOR ";
    }
}
