package com.comfostays.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;

public class NotificationsActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notifications);

            commonFunctionality = new CommonFunctionality(getApplicationContext(), this);
            commonFunctionality.setTitleBar(R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_NOTIFICATIONS);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

        }catch(Exception e){
            commonFunctionality.generatePopupMessage(Constants.ALERT_EXCEPTION,Constants.EXCEPTION_NOTIFICATIONS_ACTIVITY);

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

    public void onClickAcceptOrRejectTenant(View view){

        OwnerServerDatabaseHandler serverDatabaseHandler=new OwnerServerDatabaseHandler(getApplicationContext(),this);

        serverDatabaseHandler.execute(Constants.ACTION_GET_TENANTS_FOR_REGISTRATION);
    }

    public void onClickTenantNotification(View view){


    }

    public void onClickNotifyTenant(View view){

        Intent intent=new Intent(getApplicationContext(),NotifyTenantActivity.class);
        intent.putExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,false);
        startActivity(intent);
        finish();
    }
}