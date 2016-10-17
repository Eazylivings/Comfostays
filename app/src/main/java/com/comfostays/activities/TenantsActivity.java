package com.comfostays.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.comfostays.Adaptors.TenantsAdaptor;
import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class TenantsActivity extends AppCompatActivity {

    private List<String> listOfResidingTenantsName = new ArrayList<>();
    private CommonFunctionality commonFunctionality;
    ArrayList<TenantDetailsVO> listOfTenantDetailsVO=new ArrayList<>();
    boolean isFilterApplied=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tenants);

            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);
            commonFunctionality.setTitleBar(R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_TENANTS);
            commonFunctionality.onClickListenerForImage(R.id.backButton);
            commonFunctionality.onClickListenerForImage(R.id.homeButton);

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

            Intent intent=getIntent();

            isFilterApplied=intent.getBooleanExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,false);

            if(isFilterApplied){

                String whereClause=intent.getStringExtra(Constants.INTENT_MAP_OF_TENANT_FILTER);



                listOfTenantDetailsVO=localDatabaseHandler.getFilteredTenantDetails(whereClause);

            }else{
                listOfTenantDetailsVO=localDatabaseHandler.getAllCurrentTenants();
            }

            populateTenantsList();

            TenantsAdaptor listAdapter = new TenantsAdaptor(this, listOfResidingTenantsName,listOfTenantDetailsVO);
            final ListView listView = (ListView) findViewById(R.id.tenants_listView);
            if (listView != null) {
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(

                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                listView.setEnabled(false);

                                Intent intent=new Intent(getApplicationContext(),TenantsDetailsActivity.class);
                                intent.putExtra(Constants.INTENT_TENANT_SELECTION,listOfTenantDetailsVO.get(position));
                                startActivity(intent);
                                finish();
                            }
                        }
                );
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

    public void onClickFilter(View view){

        Intent intent=new Intent(getApplicationContext(),FilterTenantsActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_TENANT_ACTIVITY);
        startActivity(intent);
    }

    public void onClickAddTenant(View view){

    }

    private void populateTenantsList(){

        for(int i=0;i<listOfTenantDetailsVO.size();i++){

            listOfResidingTenantsName.add(listOfTenantDetailsVO.get(i).getTenantName());
        }
    }
}
