package com.comfostays.activities.buildingsetup;

import android.content.Intent;
import android.graphics.Color;
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
import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.FloorToRoomVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.WelcomeScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class CostAndChargesActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;

    PropertyDetailsVO propertyDetailsVO;
    FloorToRoomVO floorToRoomVO;
    MealScheduleVO mealScheduleVO;
    CostAndChargesVO costAndChargesVO=new CostAndChargesVO();

    private ArrayList<String> listOfIdsOfExtraCharges;
    private ArrayList<String> listOfIdsOfRoomCharges;

    private LinkedHashMap<String,LinkedHashMap<Integer,Integer>> finalMapOfFloorToRoomNoAndRoomType;

    private GridLayout gridLayoutExtraCharges;

    int widthOfScreen;
    int extraChargeRowCount=1;

    boolean isMealsScheduleAvailable=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cost_and_charges);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            commonFunctionality = new CommonFunctionality(getApplicationContext(), this);
            commonFunctionality.setTitleBar(R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_COST_AND_CHARGES);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            widthOfScreen = commonFunctionality.getScreenWidth();

            Intent intent=getIntent();
            propertyDetailsVO=(PropertyDetailsVO)intent.getSerializableExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO);
            floorToRoomVO=(FloorToRoomVO)intent.getSerializableExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO);

            if(propertyDetailsVO!=null){
                isMealsScheduleAvailable =propertyDetailsVO.isMealScheduleAvailable();
            }
            if(isMealsScheduleAvailable) {
                mealScheduleVO = (MealScheduleVO) intent.getSerializableExtra(Constants.INTENT_MEAL_SCHEDULE_VO);
            }

            listOfIdsOfExtraCharges=new ArrayList<>();

            gridLayoutExtraCharges=(GridLayout)findViewById(R.id.costAndCharges_gridLayout_extraCharges);

            if(propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.FLAT)){

                populateGridForFlatRoomCharges();

            }else{

                populateGridForPgAndHotelRoomCharges();

            }

            populateExtraChargesGridLayout();

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(getApplicationContext(),RoomConfigurationActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

        if (isMealsScheduleAvailable) {
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
        }

        startActivity(intent);
        finish();
    }

    public void onClickBackButton(View view){

        Intent intent=new Intent(getApplicationContext(),RoomConfigurationActivity.class);
        intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO,propertyDetailsVO);
        intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);

        if (isMealsScheduleAvailable) {
            intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
        }

        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onBackPressed(WelcomeScreen.class);
    }

    public void onClickNext(View view){

        try {

            if(propertyDetailsVO.getPropertyType().equalsIgnoreCase(Constants.FLAT)){

                populateMapOfRoomChargesForFlats();

            }else{

                populateMapOfRoomChargesForPGsAndHotels();
            }

            populateMapOfOtherCharges();

            if(costAndChargesVO.getListOfRoomTypesAndChargesWithDuration().size()==1 && costAndChargesVO.getListOfRoomTypesAndChargesWithDuration().get(0).equalsIgnoreCase("")){

                commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_CHARGES_FOR_ALL_ROOMS);

            }else{

                Intent intent = new Intent(getApplicationContext(), FinalPortfolioActivity.class);
                intent.putExtra(Constants.INTENT_ADD_PROPERTY_DETAILS_VO, propertyDetailsVO);
                intent.putExtra(Constants.INTENT_FLOOR_TO_NUMBER_OF_ROOMS_VO, floorToRoomVO);
                intent.putExtra(Constants.INTENT_COST_AND_CHARGES_VO, costAndChargesVO);

                if (isMealsScheduleAvailable) {
                    intent.putExtra(Constants.INTENT_MEAL_SCHEDULE_VO, mealScheduleVO);
                }
                startActivity(intent);
                finish();

            }


        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void populateGridForPgAndHotelRoomCharges(){

        try {

            listOfIdsOfRoomCharges=new ArrayList<>();

            ArrayList<String> durationList = new ArrayList<>();
            durationList.add("Per Day");
            durationList.add("Per Week");
            durationList.add("Per Month");

            int count = 0;

            finalMapOfFloorToRoomNoAndRoomType = new LinkedHashMap<>();

            GridLayout gridLayout = (GridLayout) findViewById(R.id.costAndCharges_gridLayout);

            ArrayList<String> listOfRoomTypes = propertyDetailsVO.getTypeOfRooms();

            GridLayout.LayoutParams param;

            for (int i = 1; i <= listOfRoomTypes.size(); i++) {

                LinkedHashMap<Integer, Integer> mapOfIdsOfChargeToFrequency = new LinkedHashMap<>();


                for (int j = 0; j < 3; j++) {

                    ++count;

                    EditText chargesEditText = new EditText(getApplicationContext());
                    chargesEditText.setId(View.generateViewId());
                    chargesEditText.setTextColor(Color.BLACK);
                    chargesEditText.setBackgroundResource(R.drawable.rounded_edittext);
                    chargesEditText.setHint("Charges (INR)");
                    chargesEditText.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
                    chargesEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                    param = new GridLayout.LayoutParams();
                    param.height = 100;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.rightMargin = 5;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(1);
                    param.rowSpec = GridLayout.spec(count);
                    param.setGravity(Gravity.CENTER);
                    if(j==2){
                        param.bottomMargin=20;
                    }

                    chargesEditText.setLayoutParams(param);
                    if (gridLayout != null) {
                        gridLayout.addView(chargesEditText);
                    }

                    TextView durationTextView = new TextView(getApplicationContext());
                    durationTextView.setTextColor(Color.BLACK);
                    durationTextView.setId(View.generateViewId());
                    durationTextView.setText(durationList.get(j));
                    durationTextView.setGravity(Gravity.CENTER);
                    durationTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                    param = new GridLayout.LayoutParams();
                    param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param.rightMargin = 5;
                    param.topMargin = 5;
                    param.columnSpec = GridLayout.spec(2);
                    param.rowSpec = GridLayout.spec(count);
                    param.setGravity(Gravity.CENTER);
                    if(j==2){
                        param.bottomMargin=20;
                    }

                    durationTextView.setLayoutParams(param);
                    if (gridLayout != null) {
                        gridLayout.addView(durationTextView);
                    }

                    mapOfIdsOfChargeToFrequency.put(chargesEditText.getId(), durationTextView.getId());
                }

                TextView roomType = new TextView(getApplicationContext());
                roomType.setText(listOfRoomTypes.get(i - 1));
                roomType.setTextColor(Color.BLACK);

                param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 5;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(0);
                param.rowSpec = GridLayout.spec(count - 1);
                param.setGravity(Gravity.CENTER);

                roomType.setLayoutParams(param);
                if (gridLayout != null) {
                    gridLayout.addView(roomType);
                }

                finalMapOfFloorToRoomNoAndRoomType.put(listOfRoomTypes.get(i - 1), mapOfIdsOfChargeToFrequency);
            }
        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }

    }

    private void populateGridForFlatRoomCharges(){

        try {

            String PER_MONTH="Per Month";

            listOfIdsOfRoomCharges = new ArrayList<>();

            GridLayout gridLayout = (GridLayout) findViewById(R.id.costAndCharges_gridLayout);

            ArrayList<String> listOfRoomTypes = propertyDetailsVO.getTypeOfRooms();

            GridLayout.LayoutParams param;

            for (int i = 1; i <= listOfRoomTypes.size(); i++) {

                TextView roomType = new TextView(getApplicationContext());
                roomType.setText(listOfRoomTypes.get(i - 1));
                roomType.setTextColor(Color.BLACK);

                param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 5;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(0);
                param.rowSpec = GridLayout.spec(i);
                param.setGravity(Gravity.CENTER);

                roomType.setLayoutParams(param);

                EditText chargesEditText = new EditText(getApplicationContext());
                chargesEditText.setId(View.generateViewId());
                chargesEditText.setTextColor(Color.BLACK);
                chargesEditText.setBackgroundResource(R.drawable.rounded_edittext);
                chargesEditText.setHint("Charges (INR)");
                chargesEditText.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
                chargesEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                param = new GridLayout.LayoutParams();
                param.height = 100;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 5;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(1);
                param.rowSpec = GridLayout.spec(i);
                param.setGravity(Gravity.CENTER);

                chargesEditText.setLayoutParams(param);

                TextView durationTextView = new TextView(getApplicationContext());
                durationTextView.setTextColor(Color.BLACK);
                durationTextView.setId(View.generateViewId());
                durationTextView.setText(PER_MONTH);
                durationTextView.setGravity(Gravity.CENTER);
                durationTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

                param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 5;
                param.topMargin = 5;
                param.columnSpec = GridLayout.spec(2);
                param.rowSpec = GridLayout.spec(i);
                param.setGravity(Gravity.CENTER);

                durationTextView.setLayoutParams(param);

                if (gridLayout != null) {
                    gridLayout.addView(roomType);
                    gridLayout.addView(chargesEditText);
                    gridLayout.addView(durationTextView);
                }

                listOfIdsOfRoomCharges.add(roomType.getText()+":"+chargesEditText.getId());
            }

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }


    }

    private void populateExtraChargesGridLayout(){

        try {

            GridLayout.LayoutParams param;

            EditText chargeType = new EditText(getApplicationContext());
            chargeType.setId(View.generateViewId());
            chargeType.setTextColor(Color.BLACK);
            chargeType.setHint("Charge Type");
            chargeType.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
            chargeType.setBackgroundResource(R.drawable.rounded_edittext);
            chargeType.setInputType(InputType.TYPE_CLASS_TEXT);
            chargeType.setGravity(Gravity.CENTER);
            param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = widthOfScreen*7/20;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(0);
            param.rowSpec = GridLayout.spec(extraChargeRowCount);
            param.setGravity(Gravity.CENTER);
            chargeType.setLayoutParams(param);


            EditText charges = new EditText(getApplicationContext());
            charges.setId(View.generateViewId());
            charges.setTextColor(Color.BLACK);
            charges.setHint("Amount");
            charges.setHintTextColor(Constants.COLOR_HINT_LIGHT_GREY);
            charges.setBackgroundResource(R.drawable.rounded_edittext);
            charges.setInputType(InputType.TYPE_CLASS_NUMBER);
            charges.setGravity(Gravity.CENTER);

            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = widthOfScreen*2/10;
            param.leftMargin = widthOfScreen / 20;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(1);
            param.rowSpec = GridLayout.spec(extraChargeRowCount);
            param.setGravity(Gravity.CENTER);
            charges.setLayoutParams(param);

            final ImageView imageViewRemove = new ImageView(getApplicationContext());
            imageViewRemove.setId(View.generateViewId());
            imageViewRemove.setImageResource(R.drawable.ic_cancel_black_24dp);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = widthOfScreen/10;
            param.leftMargin = widthOfScreen / 40;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(2);
            param.rowSpec = GridLayout.spec(extraChargeRowCount);
            param.setGravity(Gravity.CENTER);
            imageViewRemove.setLayoutParams(param);

            imageViewRemove.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    boolean isRemoved = false;

                    for (int i = 0; i < listOfIdsOfExtraCharges.size(); i++) {

                        String[] ids = listOfIdsOfExtraCharges.get(i).split(Constants.COLON);

                        if (ids[2].equalsIgnoreCase(String.valueOf(v.getId())) && i == (listOfIdsOfExtraCharges.size() - 1)) {

                            gridLayoutExtraCharges.removeView(findViewById(Integer.parseInt(ids[0])));
                            gridLayoutExtraCharges.removeView(findViewById(Integer.parseInt(ids[1])));
                            gridLayoutExtraCharges.removeView(findViewById(Integer.parseInt(ids[2])));
                            gridLayoutExtraCharges.removeView(findViewById(Integer.parseInt(ids[3])));

                            isRemoved = true;
                            listOfIdsOfExtraCharges.remove(i);
                            extraChargeRowCount--;

                            if (i != 0) {

                                String[] previousIds = listOfIdsOfExtraCharges.get(i - 1).split(Constants.COLON);
                                ImageView removeRow = (ImageView) findViewById(Integer.parseInt(previousIds[2]));
                                ImageView addRow = (ImageView) findViewById(Integer.parseInt(previousIds[3]));

                                if (removeRow != null && addRow != null) {

                                    removeRow.setVisibility(View.VISIBLE);
                                    addRow.setVisibility(View.VISIBLE);
                                } else if (addRow != null) {
                                    addRow.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }

                    if (!isRemoved) {
                        commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE, Constants.POPUP_MESSAGE_FLOOR_NOT_REMOVED);
                    }
                }
            });

            final ImageView imageViewAdd = new ImageView(getApplicationContext());
            imageViewAdd.setId(View.generateViewId());
            imageViewAdd.setImageResource(R.drawable.ic_add);
            param = new GridLayout.LayoutParams();
            param.height = 100;
            param.width = widthOfScreen/10;
            param.leftMargin = widthOfScreen / 40;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(3);
            param.rowSpec = GridLayout.spec(extraChargeRowCount);
            param.setGravity(Gravity.CENTER);
            imageViewAdd.setLayoutParams(param);

            imageViewAdd.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    imageViewAdd.setVisibility(View.INVISIBLE);
                    imageViewRemove.setVisibility(View.INVISIBLE);
                    populateExtraChargesGridLayout();


                }
            });


            if (gridLayoutExtraCharges != null) {
                gridLayoutExtraCharges.addView(chargeType);
                gridLayoutExtraCharges.addView(charges);
                if (extraChargeRowCount != 1) {
                    gridLayoutExtraCharges.addView(imageViewRemove);
                }
                gridLayoutExtraCharges.addView(imageViewAdd);

            }

            listOfIdsOfExtraCharges.add(chargeType.getId() + Constants.COLON + charges.getId() + Constants.COLON + imageViewRemove.getId() + Constants.COLON + imageViewAdd.getId());
            extraChargeRowCount++;
        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }

    }

    private void populateMapOfRoomChargesForPGsAndHotels(){

        ArrayList<String> listOfRoomTypesAndChargesWithDuration=new ArrayList<>();

        try{

            Set<String> set=finalMapOfFloorToRoomNoAndRoomType.keySet();
            Iterator<String> iterator=set.iterator();

            while(iterator.hasNext()){

                String roomType=iterator.next();

                LinkedHashMap<Integer,Integer> hashMap=finalMapOfFloorToRoomNoAndRoomType.get(roomType);

                Set<Integer> s=hashMap.keySet();

                Iterator<Integer> i=s.iterator();

                while(i.hasNext()){

                    int chargesEditTextId=i.next();

                    int durationTextViewId=hashMap.get(chargesEditTextId);

                    EditText chargesEditText=(EditText)findViewById(chargesEditTextId);

                    TextView durationTextView=(TextView)findViewById(durationTextViewId);

                    if(chargesEditText!=null && durationTextView!=null && !chargesEditText.getText().toString().equalsIgnoreCase("")){

                        listOfRoomTypesAndChargesWithDuration.add(roomType+Constants.COLON+chargesEditText.getText().toString()
                                    +Constants.COLON+durationTextView.getText().toString());
                    }
                }
            }

            costAndChargesVO.setList(listOfRoomTypesAndChargesWithDuration);

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void populateMapOfRoomChargesForFlats(){

        ArrayList<String> list=new ArrayList<>();

        try{

            for(int i=0;i<listOfIdsOfRoomCharges.size();i++){

                String[] ids=listOfIdsOfRoomCharges.get(i).split(":");

                String roomType=ids[0];

                EditText charges=(EditText)findViewById(Integer.parseInt(ids[1]));

                if(charges!=null){

                    list.add(roomType+":"+charges.getText().toString()+": Per Month");

                }
            }

            costAndChargesVO.setList(list);

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private void populateMapOfOtherCharges(){

        try {

            LinkedHashMap<String,String> otherChargesMap=new LinkedHashMap<>();

            for (int i = 0; i < listOfIdsOfExtraCharges.size(); i++) {

                String[] ids = listOfIdsOfExtraCharges.get(i).split(Constants.COLON);

                EditText chargeTypeEditText = (EditText) findViewById(Integer.parseInt(ids[0]));
                EditText chargesEditText = (EditText) findViewById(Integer.parseInt(ids[1]));

                if (chargeTypeEditText != null && chargesEditText != null ) {

                    otherChargesMap.put(chargeTypeEditText.getText().toString(), chargesEditText.getText().toString());
                }
            }

            costAndChargesVO.setOtherChargesMap(otherChargesMap);
        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }
}
