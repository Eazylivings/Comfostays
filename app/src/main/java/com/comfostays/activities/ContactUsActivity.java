package com.comfostays.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.sharedpreference.SharedPreference;

public class ContactUsActivity extends AppCompatActivity {

    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contact_us);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_CONTACT_US);

            sharedPreference=new SharedPreference(getApplicationContext());

            String userName=sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_USERNAME);

            EditText messageText=(EditText)findViewById(R.id.contactUs_editText_messageText);

            if(messageText!=null){
                messageText.setHint("Hey" + userName + "!" + "\n" +"Please write your message here. We will soon contact you. You can also leave you valuable feedback.");
            }
        }catch(Exception e){

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

    public void onClickSubmitButton(View view){

    }
}
