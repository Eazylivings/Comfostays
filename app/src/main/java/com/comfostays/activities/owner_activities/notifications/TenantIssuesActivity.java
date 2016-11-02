package com.comfostays.activities.owner_activities.notifications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.comfostays.Adaptors.TenantIssuesAdaptor;
import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.activities.owner_activities.notifications.OwnerNotificationsActivity;

import java.util.ArrayList;

public class TenantIssuesActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;
    ArrayList<String> listOfTenantIssues =new ArrayList<>();
    ArrayList<String> listOfStatuses=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tenant_issues);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_NOTIFICATIONS);

            Intent intent=getIntent();

            String issues=intent.getStringExtra(Constants.INTENT_TENANT_ISSUES);
            String status=intent.getStringExtra(Constants.INTENT_TENANT_ISSUES_STATUS);

            String[] array=issues.split(":");
            String[] issuesArray=status.split(":");

            for(int i=0;i<array.length;i++){

                listOfTenantIssues.add(array[i]);
                listOfStatuses.add(issuesArray[i]);
            }


            ListAdapter listAdapter = new TenantIssuesAdaptor(this, listOfTenantIssues,listOfStatuses);
            ListView listView = (ListView) findViewById(R.id.listView_notifications);
            if (listView != null) {
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(

                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                );
            }

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
}
