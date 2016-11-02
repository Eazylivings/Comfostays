package com.comfostays.activities.owner_activities.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.comfostays.Adaptors.AcceptRejectTenantAdaptor;
import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.activities.owner_activities.notifications.OwnerNotificationsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AcceptRejectTenantActivity extends AppCompatActivity {


    ArrayList<String> listOfTenantIds=new ArrayList<>();
    List<HashMap<String,String>> listOfMapOfTenantsForRegistrationApproval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accept_reject_tenant);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton, R.id.titleBar, R.id.homeButton, Constants.TITLE_ACCEPT_REJECT_TENANTS);

            listOfMapOfTenantsForRegistrationApproval=new ArrayList<>();

            HashMap<String,String> map=new HashMap<>();
            map.put("name","Shwetang");
            map.put("roomNumber","G2");

            listOfMapOfTenantsForRegistrationApproval.add(map);

            listOfTenantIds.add("1");

            ListAdapter listAdapter =new AcceptRejectTenantAdaptor(this, listOfTenantIds,listOfMapOfTenantsForRegistrationApproval);
            ListView listView = (ListView) findViewById(R.id.listView_acceptRejectTenant);
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

        }catch (Exception e){

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
