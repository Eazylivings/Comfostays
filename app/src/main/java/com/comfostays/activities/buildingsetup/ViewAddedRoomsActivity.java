package com.comfostays.activities.buildingsetup;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.sharedpreference.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class ViewAddedRoomsActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;
    SharedPreference sharedPreference;
    List<String> listOfRoomNumbers=new ArrayList<>();
    List<String> listOfColorCoding=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_added_rooms);
            sharedPreference=new SharedPreference(getApplicationContext());

            populateLists();
            setGridLayout();

            commonFunctionality = new CommonFunctionality(getApplicationContext(), this);
            commonFunctionality.setTitleBar(R.id.view_added_rooms_backButton, R.id.view_added_rooms_titleBar, R.id.view_added_rooms_homeButton, Constants.TITLE_VIEW_ADDED_ROOMS);
            commonFunctionality.onClickListenerForImage(R.id.view_added_rooms_backButton);
            commonFunctionality.onClickListenerForImage(R.id.view_added_rooms_homeButton);

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();

        }
    }

    @Override
    public void onBackPressed(){

        commonFunctionality.onBackPressed(FinalPortfolioActivity.class);
    }

    public void onClickBackButton(View view){

        commonFunctionality.onBackPressed(FinalPortfolioActivity.class);
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onBackPressed(WelcomeScreen.class);
    }

    private void setGridLayout(){

        GridLayout layout=(GridLayout)findViewById(R.id.viewAddedRooms_gridLayout_roomsLayout);
        int size=listOfRoomNumbers.size();
        int row=0;
        int column=0;
        int count=0;
        int halfSize=size/2;
        boolean hasEnteredInThisLoop=true;


        for(int i=0;i<listOfRoomNumbers.size();i++){

            String currentText=listOfRoomNumbers.get(i);

            final TextView textView=new TextView(getApplicationContext());
            textView.setText(currentText);
            textView.setBackgroundColor(Color.parseColor(listOfColorCoding.get(i)));
            textView.setGravity(Gravity.CENTER);

            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = 150;//GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = 150;//GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(column);
            param.rowSpec = GridLayout.spec(row);

            textView.setLayoutParams (param);
            if(layout!=null) {
                layout.addView(textView);
            }

            if(count<(halfSize-1)){
                count++;
            }else if(hasEnteredInThisLoop){
                column=1;
                row=-1;
                hasEnteredInThisLoop=false;
            }
            row++;

        }
    }

    private void populateLists(){

        listOfRoomNumbers.add("101");
        listOfRoomNumbers.add("102");
        listOfRoomNumbers.add("103");
        listOfRoomNumbers.add("104");
        listOfRoomNumbers.add("105");
        listOfRoomNumbers.add("106");

        listOfColorCoding.add("#87CEFA");
        listOfColorCoding.add("#90EE90");
        listOfColorCoding.add("#FF6347");
        listOfColorCoding.add("#CD853F");
        listOfColorCoding.add("#FF6347");
        listOfColorCoding.add("#87CEFA");

    }
}
