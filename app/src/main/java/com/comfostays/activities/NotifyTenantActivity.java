package com.comfostays.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;

import java.util.ArrayList;

public class NotifyTenantActivity extends AppCompatActivity {

    private CommonFunctionality commonFunctionality;

    ArrayList<TenantDetailsVO> listOfTenants;
    ArrayList<Integer> listOfCheckBoxIds;

    boolean isFilterApplied=false;
    boolean allTenantsSelected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notify_tenant);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);
            commonFunctionality.setTitleBar(R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_NOTIFY_TENANT);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

            Intent intent=getIntent();

            isFilterApplied=intent.getBooleanExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,false);

            if(isFilterApplied){

                String whereClause=intent.getStringExtra(Constants.INTENT_MAP_OF_TENANT_FILTER);

                listOfTenants=localDatabaseHandler.getFilteredTenantDetails(whereClause);

            }else{
                listOfTenants=localDatabaseHandler.getAllCurrentTenants();
            }

            populateTenantCheckBox();

        }catch(Exception e){
            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    @Override
    public void onBackPressed(){

        commonFunctionality.onBackPressed(NotificationsActivity.class);
    }

    public void onClickBackButton(View view){

        commonFunctionality.onBackPressed(NotificationsActivity.class);
    }

    public void onClickHomeButton(View view){

        commonFunctionality.onClickHomeButton();
    }

    public void onClickFilter(View view){

        Intent intent=new Intent(getApplicationContext(),FilterTenantsActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_NOTIFY_TENANT);
        startActivity(intent);
    }

    public void onClickNotify(View view){

        String tenantsId="";

        for(int i=0;i<listOfCheckBoxIds.size();i++){

            CheckBox selectAllCheckBox=(CheckBox)findViewById(listOfCheckBoxIds.get(i));

            if(selectAllCheckBox!=null && selectAllCheckBox.isChecked()){

                tenantsId=tenantsId+listOfTenants.get(i).getTenantUserId()+":";
            }
        }

        OwnerServerDatabaseHandler serverDatabaseHandler=new OwnerServerDatabaseHandler(getApplicationContext(),this);

        EditText editText=(EditText)findViewById(R.id.editText_message);

        if(editText!=null){

            String message=editText.getText().toString();

            if(message.equalsIgnoreCase("")){

                commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE,"");

            }else{

                serverDatabaseHandler.execute(Constants.ACTION_NOTIFY_TENANTS,message,tenantsId.substring(0,tenantsId.length()-1));
            }
        }
    }

    private void populateTenantCheckBox(){

        listOfCheckBoxIds=new ArrayList<>();

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearLayout_tenantCheckBox);

        if(linearLayout!=null) {

            for (int i = 0; i < listOfTenants.size(); i++) {

                String checkBoxText=listOfTenants.get(i).getTenantName() + " -  " + listOfTenants.get(i).getTenantRoomNumber();

                CheckBox checkBox = new CheckBox(linearLayout.getContext());
                checkBox.setTextColor(Color.BLACK);
                checkBox.setId(View.generateViewId());
                checkBox.setText(checkBoxText);

                linearLayout.addView(checkBox);
                listOfCheckBoxIds.add(checkBox.getId());
            }
        }
    }

    public void onClickSelectAll(View view){

        CheckBox selectAllCheckBox=(CheckBox)findViewById(R.id.checkBox_selectAll);

        if(selectAllCheckBox!=null && allTenantsSelected){

            selectAllCheckBox.setChecked(false);

        }else if(selectAllCheckBox!=null){

            selectAllCheckBox.setChecked(true);
        }

        if(allTenantsSelected){

            allTenantsSelected=false;

            for(int i=0;i<listOfCheckBoxIds.size();i++){

                CheckBox checkBox=(CheckBox)findViewById(listOfCheckBoxIds.get(i));

                if(checkBox!=null){

                    checkBox.setChecked(false);
                }
            }
        }else{

            allTenantsSelected=true;

            for(int i=0;i<listOfCheckBoxIds.size();i++){

                CheckBox checkBox=(CheckBox)findViewById(listOfCheckBoxIds.get(i));

                if(checkBox!=null){

                    checkBox.setChecked(true);
                }
            }
        }
    }
}
