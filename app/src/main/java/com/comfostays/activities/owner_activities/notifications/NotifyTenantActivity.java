package com.comfostays.activities.owner_activities.notifications;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.activities.owner_activities.tenant_activities.FilterTenantsActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;

import java.util.ArrayList;

public class NotifyTenantActivity extends AppCompatActivity {

    private ArrayList<TenantDetailsVO> listOfTenants;

    private int[] arrayOfCheckBoxIds;

    private boolean allTenantsSelected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notify_tenant);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_NOTIFY_TENANT);

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

            Intent intent=getIntent();

            boolean isFilterApplied=intent.getBooleanExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,false);

            if(isFilterApplied){

                String whereClause=intent.getStringExtra(Constants.INTENT_MAP_OF_TENANT_FILTER);

                listOfTenants=localDatabaseHandler.getFilteredTenantDetails(whereClause);

                ImageView removeFilter=(ImageView)findViewById(R.id.imageView_removeFilter);

                if(removeFilter!=null){

                    removeFilter.setVisibility(View.VISIBLE);
                }

            }else{
                listOfTenants=localDatabaseHandler.getAllCurrentTenants();

                ImageView removeFilter=(ImageView)findViewById(R.id.imageView_removeFilter);

                if(removeFilter!=null){

                    removeFilter.setVisibility(View.GONE);
                }
            }

            arrayOfCheckBoxIds=new int[listOfTenants.size()];

            populateTenantCheckBox();

        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        CommonFunctionality.onBackPressed(this,OwnerNotificationsActivity.class);
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,OwnerNotificationsActivity.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickFilter(View view){

        Intent intent=new Intent(getApplicationContext(),FilterTenantsActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_NOTIFY_TENANT);
        startActivity(intent);
    }

    public void onClickNotify(View view){

        String tenantsId="";

        for(int i=0;i<arrayOfCheckBoxIds.length;i++){

            CheckBox selectAllCheckBox=(CheckBox)findViewById(arrayOfCheckBoxIds[i]);

            if(selectAllCheckBox!=null && selectAllCheckBox.isChecked()){

                tenantsId=tenantsId+listOfTenants.get(i).getTenantUserId()+":";
            }
        }

        OwnerServerDatabaseHandler serverDatabaseHandler=new OwnerServerDatabaseHandler(getApplicationContext(),this);

        EditText editText=(EditText)findViewById(R.id.editText_message);

        if(editText!=null){

            String message=editText.getText().toString();

            if(message.equalsIgnoreCase("")){

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_MESSAGE,"");

            }else{

                serverDatabaseHandler.execute(Constants.ACTION_NOTIFY_TENANTS,message,tenantsId.substring(0,tenantsId.length()-1));
            }
        }
    }

    private void populateTenantCheckBox(){

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearLayout_tenantCheckBox);

        if(linearLayout!=null) {

            for (int i = 0; i < listOfTenants.size(); i++) {

                String checkBoxText=listOfTenants.get(i).getTenantName() + " -  " + listOfTenants.get(i).getTenantRoomNumber();

                CheckBox checkBox = new CheckBox(linearLayout.getContext());
                checkBox.setTextColor(Color.BLACK);
                checkBox.setId(View.generateViewId());
                checkBox.setText(checkBoxText);

                linearLayout.addView(checkBox);
                arrayOfCheckBoxIds[i]=checkBox.getId();
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

            for(int id : arrayOfCheckBoxIds){

                CheckBox checkBox=(CheckBox)findViewById(id);

                if(checkBox!=null){

                    checkBox.setChecked(true);
                }
            }
        }else{

            allTenantsSelected=true;

            for(int id : arrayOfCheckBoxIds){

                CheckBox checkBox=(CheckBox)findViewById(id);

                if(checkBox!=null){

                    checkBox.setChecked(true);
                }
            }
        }
    }

    public void onClickRemoveFilter(View view){

        ImageView removeFilter=(ImageView)findViewById(R.id.imageView_removeFilter);

        if(removeFilter!=null){

            removeFilter.setVisibility(View.GONE);
        }

        Intent intent=new Intent(getApplicationContext(),NotifyTenantActivity.class);
        intent.putExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,false);
        startActivity(intent);
        finish();
    }
}
