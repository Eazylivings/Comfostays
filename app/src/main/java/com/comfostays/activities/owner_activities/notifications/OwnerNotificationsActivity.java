package com.comfostays.activities.owner_activities.notifications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;

public class OwnerNotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_owner_notifications);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_NOTIFICATIONS);

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

    public void onClickAcceptOrRejectTenant(View view){

        OwnerServerDatabaseHandler serverDatabaseHandler=new OwnerServerDatabaseHandler(getApplicationContext(),this);

        serverDatabaseHandler.execute(Constants.ACTION_GET_TENANTS_FOR_REGISTRATION,"");
    }

    public void onClickTenantIssues(View view){

        OwnerServerDatabaseHandler serverDatabaseHandler=new OwnerServerDatabaseHandler(getApplicationContext(),this);

        serverDatabaseHandler.execute(Constants.ACTION_GET_NOTIFICATIONS,"");
    }

    public void onClickNotifyTenant(View view){

        Intent intent=new Intent(getApplicationContext(),NotifyTenantActivity.class);
        intent.putExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,false);
        startActivity(intent);
        finish();
    }
}