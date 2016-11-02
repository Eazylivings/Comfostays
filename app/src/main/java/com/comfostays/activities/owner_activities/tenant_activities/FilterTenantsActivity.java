package com.comfostays.activities.owner_activities.tenant_activities;

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
import com.comfostays.activities.owner_activities.notifications.NotifyTenantActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;

import java.util.ArrayList;

public class FilterTenantsActivity extends Activity {

    private Spinner operatorSpinner;
    private Spinner propertySpinner;
    private Spinner roomTypesSpinner;

    private String previousActivity="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_filter_tenants);

            setTitle(Constants.TITLE_FILTER_TENANTS);

            Intent intent=getIntent();
            previousActivity=intent.getStringExtra(Constants.INTENT_PREVIOUS_ACTIVITY);

            populateSpinners();
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    public void onClickFilter(View view){

        try {

            StringBuilder whereClause=new StringBuilder(" where ");

            final String KEY_PROPERTY_NAME = "propertyName";
            final String KEY_COMPLAINTS_LOGGED = "complaints_logged";
            final String KEY_ROOM_TYPE = "room_type";
            final String KEY_RENT_PAID = "rent_paid";
            final String KEY_CUSTOMER_ACTIVITY = "is_customer_active";
            final String KEY_TENANT_RESIDING_SINCE = "residing_since";
            final String KEY_RESIDING_TILL="residing_till";

            CheckBox rentPaidCheckBox = (CheckBox) findViewById(R.id.checkBox_rentPaid);

            if (rentPaidCheckBox != null && rentPaidCheckBox.isChecked()) {

                whereClause.append(KEY_RENT_PAID.concat(" = ").concat("false").concat(" and "));
            }

            CheckBox inActiveCheckBox = (CheckBox) findViewById(R.id.checkBox_activeTenant);

            if (inActiveCheckBox != null && inActiveCheckBox.isChecked()) {

                whereClause.append(KEY_CUSTOMER_ACTIVITY.concat(" = ").concat("false").concat(" and "));
            }

            if (propertySpinner != null) {

                whereClause.append(KEY_PROPERTY_NAME.concat(" = \"").concat(propertySpinner.getSelectedItem().toString()).concat("\" and "));
            }

            if (operatorSpinner != null && !operatorSpinner.getSelectedItem().toString().equalsIgnoreCase("")) {

                whereClause.append(KEY_COMPLAINTS_LOGGED.concat(" = ").concat(operatorSpinner.getSelectedItem().toString()).concat(" and "));
            }
            if (roomTypesSpinner != null && !roomTypesSpinner.getSelectedItem().toString().equalsIgnoreCase("")) {

                whereClause.append(KEY_ROOM_TYPE.concat(" = \"").concat(roomTypesSpinner.getSelectedItem().toString()).concat("\" and "));
            }

            EditText residingFrom = (EditText) findViewById(R.id.editText_residingFrom);

            if (residingFrom != null && !residingFrom.getText().toString().equalsIgnoreCase("")) {

                whereClause.append(KEY_TENANT_RESIDING_SINCE.concat(" = \"").concat(residingFrom.getText().toString()).concat("\" and "));
            }

            EditText residingTill = (EditText) findViewById(R.id.editText_residingTill);

            if (residingTill != null && !residingTill.getText().toString().equalsIgnoreCase("")) {

                whereClause.append(KEY_RESIDING_TILL.concat(" = \"").concat(residingTill.getText().toString()).concat("\" and "));
            }

            if (previousActivity.equalsIgnoreCase(Constants.ACTIVITY_TENANT_ACTIVITY)) {

                Intent intent = new Intent(getApplicationContext(), TenantsActivity.class);
                intent.putExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED, true);
                intent.putExtra(Constants.INTENT_MAP_OF_TENANT_FILTER, whereClause.substring(0, whereClause.length() - 4));
                startActivity(intent);
                finish();

            } else {

                Intent intent = new Intent(getApplicationContext(), NotifyTenantActivity.class);
                intent.putExtra(Constants.INTENT_IS_TENANT_FILTER_APPLIED, true);
                intent.putExtra(Constants.INTENT_MAP_OF_TENANT_FILTER, whereClause.substring(0, whereClause.length() - 4));
                startActivity(intent);
                finish();
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
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

        try {

            OwnerLocalDatabaseHandler localDatabaseHandler = new OwnerLocalDatabaseHandler(getApplicationContext(), this);

            ArrayList<String> listOfOperators = new ArrayList<>();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, listOfOperators);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            listOfOperators.add("");
            listOfOperators.add("Equal To");
            listOfOperators.add("Less Than");
            listOfOperators.add("Greater Than");

            operatorSpinner = (Spinner) findViewById(R.id.spinner_operator);
            if (operatorSpinner != null) {
                operatorSpinner.setAdapter(adapter);
                operatorSpinner.setSelection(0);
            }

            propertySpinner = (Spinner) findViewById(R.id.spinner__property);
            ArrayList<String> listOfRegisteredPropertyNames = localDatabaseHandler.getAllRegisteredPropertyNames();

            adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, listOfRegisteredPropertyNames);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if (propertySpinner != null && listOfRegisteredPropertyNames.size() > 0) {
                propertySpinner.setAdapter(adapter);
                propertySpinner.setSelection(0);
            }

            roomTypesSpinner = (Spinner) findViewById(R.id.spinner__roomType);
            ArrayList<String> listOfTypesOfRooms = new ArrayList<>(localDatabaseHandler.getTypesOfRooms());

            listOfTypesOfRooms.add(0, "");

            adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, listOfTypesOfRooms);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if (roomTypesSpinner != null && listOfTypesOfRooms.size() > 0) {
                roomTypesSpinner.setAdapter(adapter);
                roomTypesSpinner.setSelection(0);
            }
        }catch (Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

}
