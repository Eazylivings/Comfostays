package com.comfostays.activities.owner_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.comfostays.Adaptors.MyPropertiesAdaptor;
import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.owner_activities.building_setup.AddPropertyActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyPropertiesActivity extends AppCompatActivity {

    ArrayList<String> listOfNameOfRegisteredProperty=new ArrayList<>();
    LinkedHashMap<Integer,String> mapOfRegisteredProperties;

    ArrayList<Integer> listOfPropertyIds=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_properties);

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

            mapOfRegisteredProperties=localDatabaseHandler.getMapOfRegisteredProperties();

            populateListOfPropertyNames();

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_MY_PROPERTIES);

            CommonFunctionality.onClickListenerForImage(this,R.id.myProperty_imageButton_addProperty);
            CommonFunctionality.onClickListenerForText(this,R.id.myProperty_textView_addProperty);

            if(listOfNameOfRegisteredProperty!=null){

                final ArrayList<String> listOfPropertyName=new ArrayList<>(listOfNameOfRegisteredProperty);

                ListAdapter listAdapter = new MyPropertiesAdaptor(this, listOfPropertyName);
                ListView listView = (ListView) findViewById(R.id.myProperties_listView);
                if (listView != null) {
                    listView.setAdapter(listAdapter);

                    listView.setOnItemClickListener(

                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent intent=new Intent(getApplicationContext(),ViewPropertyActivity.class);
                                    intent.putExtra(Constants.INTENT_SELECTED_PROPERTY_ID,listOfPropertyIds.get(position));
                                    startActivity(intent);
                                    finish();
                                }
                            }
                    );
                }
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

    public void onClickAddProperty(View view){

        Intent intent=new Intent(getApplicationContext(),AddPropertyActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_MY_PROPERTY);
        startActivity(intent);
        finish();
    }

    private void populateListOfPropertyNames(){

        for(Map.Entry<Integer,String> entry : mapOfRegisteredProperties.entrySet()){

            int propertyId=entry.getKey();

            listOfPropertyIds.add(propertyId);

            listOfNameOfRegisteredProperty.add(entry.getValue());
        }
    }
}
