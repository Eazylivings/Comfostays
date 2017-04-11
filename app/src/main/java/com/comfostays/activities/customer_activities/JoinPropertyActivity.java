package com.comfostays.activities.customer_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.CustomerServerDatabaseHandler;

public class JoinPropertyActivity extends AppCompatActivity {


    EditText customerName;
    EditText customerRoomNumber;


    RadioButton rentPaid;
    RadioButton rentNotPaid;

    Button joinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_join_property);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_JOIN_PROPERTY);

            customerName=(EditText)findViewById(R.id.editText_customerName);
            customerRoomNumber=(EditText)findViewById(R.id.editText_customerRoom);
            rentPaid=(RadioButton)findViewById(R.id.radioButton_rentPaid);
            rentNotPaid=(RadioButton)findViewById(R.id.radioButton_rentNotPaid);
            joinButton=(Button)findViewById(R.id.button_join) ;

        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,WelcomeScreen.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickJoin(View view){

        CustomerServerDatabaseHandler serverDatabaseHandler = new CustomerServerDatabaseHandler(getApplicationContext(), JoinPropertyActivity.this);
        serverDatabaseHandler.execute(Constants.ACTION_CUSTOMER_JOIN_PROPERTY, "");
    }

}
