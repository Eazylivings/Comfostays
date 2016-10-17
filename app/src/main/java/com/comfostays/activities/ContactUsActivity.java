package com.comfostays.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.sharedpreference.SharedPreference;

public class ContactUsActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contact_us);

            commonFunctionality = new CommonFunctionality(getApplicationContext(), this);
            sharedPreference=new SharedPreference(getApplicationContext());

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            String userName=sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_USERNAME);

            commonFunctionality.setTitleBar(R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_CONTACT_US);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            EditText messageText=(EditText)findViewById(R.id.contactUs_editText_messageText);

            if(messageText!=null){
                messageText.setHint("Hey" + userName + "!" + "\n" +"Please write your message here. We will soon contact you. You can also leave you valuable feedback.");
            }
        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    @Override
    public void onBackPressed(){

        commonFunctionality.onBackPressed(WelcomeScreen.class);
    }

    public void onClickBackButton(View view){

        commonFunctionality.onBackPressed(WelcomeScreen.class);
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onClickHomeButton();
    }

    public void onClickSubmitButton(View view){

    }
}
