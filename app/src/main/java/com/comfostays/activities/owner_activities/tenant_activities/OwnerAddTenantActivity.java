package com.comfostays.activities.owner_activities.tenant_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.databasehandler.CustomerServerDatabaseHandler;

public class OwnerAddTenantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_owner_add_tenant);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_OWNER_ADD_TENANT);

        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,TenantsActivity.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickJoin(View view){

        CustomerServerDatabaseHandler serverDatabaseHandler = new CustomerServerDatabaseHandler(getApplicationContext(), OwnerAddTenantActivity.this);
        serverDatabaseHandler.execute(Constants.ACTION_CUSTOMER_JOIN_PROPERTY, "");
    }
}
