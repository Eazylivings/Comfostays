package com.comfostays.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

import java.util.ArrayList;

public class FilterTenantsActivity extends Activity {

    private CommonFunctionality commonFunctionality;

    ArrayList<TenantDetailsVO> listOfTenantDetailsVO=new ArrayList<>();

    Spinner operatorSpinner;
    Spinner propertySpinner;
    Spinner roomTypesSpinner;

    String previousActivity="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_filter_tenants);

            setTitle(Constants.TITLE_FILTER_TENANTS);

            commonFunctionality = new CommonFunctionality(getApplicationContext(), this);

            Intent intent=getIntent();
            previousActivity=intent.getStringExtra(Constants.INTENT_PREVIOUS_ACTIVITY);

            populateSpinners();
        }catch (Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    public void onClickFilter(View view){

        String whereClause=" where ";
        String KEY_PROPERTY_NAME="propertyName";
        String KEY_COMPLAINTS_LOGGED="complaints_logged";
        String KEY_ROOM_TYPE="room_type";
        String KEY_RENT_PAID="rent_paid";
        String KEY_CUSTOMER_ACTIVITY="is_customer_active";
        String KEY_TENANT_RESIDING_SINCE="residing_since";

        CheckBox rentPaidCheckBox=(CheckBox)findViewById(R.id.checkBox_rentPaid);

        if(rentPaidCheckBox!=null && rentPaidCheckBox.isChecked()){

            whereClause=whereClause+KEY_RENT_PAID+" = "+"false"+" and ";
        }

        CheckBox inActiveCheckBox=(CheckBox)findViewById(R.id.checkBox_activeTenant);

        if(inActiveCheckBox!=null && inActiveCheckBox.isChecked()){

            whereClause=whereClause+KEY_CUSTOMER_ACTIVITY+" = "+"false"+" and ";
        }

        if(propertySpinner!=null){
            whereClause=whereClause+KEY_PROPERTY_NAME+" = \""+propertySpinner.getSelectedItem().toString()+"\" and ";
        }

        if(operatorSpinner!=null && !operatorSpinner.getSelectedItem().toString().equalsIgnoreCase("")){

            whereClause=whereClause+ " and " +KEY_COMPLAINTS_LOGGED+ " = "+operatorSpinner.getSelectedItem().toString()+" and ";
        }
        if(roomTypesSpinner!=null && !roomTypesSpinner.getSelectedItem().toString().equalsIgnoreCase("")){

            whereClause=whereClause+KEY_ROOM_TYPE+ " = \""+roomTypesSpinner.getSelectedItem().toString()+"\" and ";
        }

        EditText residingFrom=(EditText)findViewById(R.id.editText_residingFrom);

        if(residingFrom!=null && !residingFrom.getText().toString().equalsIgnoreCase("")){

            whereClause=whereClause+KEY_TENANT_RESIDING_SINCE+" = "+residingFrom.getText().toString()+" and ";
        }

        EditText residingTill=(EditText)findViewById(R.id.editText_residingTill);

        if(residingTill!=null && !residingTill.getText().toString().equalsIgnoreCase("")){

            whereClause=whereClause+KEY_TENANT_RESIDING_SINCE+" = "+residingTill.getText().toString()+" and ";
        }

        if(previousActivity.equalsIgnoreCase(Constants.ACTIVITY_TENANT_ACTIVITY)){

            Intent intent=new Intent(getApplicationContext(),TenantsActivity.class);
            intent.putExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,true);
            intent.putExtra(Constants.INTENT_MAP_OF_TENANT_FILTER,whereClause.substring(0,whereClause.length()-4));
            startActivity(intent);
            finish();

        }else{

            Intent intent=new Intent(getApplicationContext(),NotifyTenantActivity.class);
            intent.putExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED,true);
            intent.putExtra(Constants.INTENT_MAP_OF_TENANT_FILTER,whereClause.substring(0,whereClause.length()-4));
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed(){

        finish();
    }

    public void onClickCancel(View view){

        finish();
    }

    private void populateSpinners(){

        OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);

        ArrayList<String> listOfOperators=new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listOfOperators);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listOfOperators.add("");
        listOfOperators.add("Equal To");
        listOfOperators.add("Less Than");
        listOfOperators.add("Greater Than");

        operatorSpinner=(Spinner)findViewById(R.id.spinner_operator);
        if(operatorSpinner!=null){
            operatorSpinner.setAdapter(adapter);
            operatorSpinner.setSelection(0);
        }

        propertySpinner=(Spinner)findViewById(R.id.spinner__property);
        ArrayList<String> listOfRegisteredPropertyNames=localDatabaseHandler.getAllRegisteredPropertyNames();

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listOfRegisteredPropertyNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(propertySpinner!=null && listOfRegisteredPropertyNames.size()>0){
            propertySpinner.setAdapter(adapter);
            propertySpinner.setSelection(0);
        }

        roomTypesSpinner=(Spinner)findViewById(R.id.spinner__roomType);
        ArrayList<String> listOfTypesOfRooms=new ArrayList<>(localDatabaseHandler.getTypesOfRooms());

        listOfTypesOfRooms.add(0,"");

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listOfTypesOfRooms);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(roomTypesSpinner!=null && listOfTypesOfRooms.size()>0) {
            roomTypesSpinner.setAdapter(adapter);
            roomTypesSpinner.setSelection(0);
        }
    }

    private void setScreen(){

        int screenWidth=commonFunctionality.getScreenWidth();

    }
}
