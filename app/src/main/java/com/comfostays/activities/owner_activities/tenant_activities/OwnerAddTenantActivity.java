package com.comfostays.activities.owner_activities.tenant_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.databasehandler.CustomerServerDatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class OwnerAddTenantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_owner_add_tenant);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_OWNER_ADD_TENANT);

            populateSpinner();

        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private void populateSpinner(){

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("PAN Card");
        spinnerArray.add("Voter Id");
        spinnerArray.add("Aadhar Card");
        spinnerArray.add("Driving Licence");
        spinnerArray.add("Passport");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner idProofOne = (Spinner) findViewById(R.id.spinner_idProofOne);
        Spinner idProofTwo = (Spinner) findViewById(R.id.spinner_idProofTwo);

        if(idProofOne!=null && idProofTwo!=null) {
            idProofOne.setAdapter(adapter);
            idProofTwo.setAdapter(adapter);
        }
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,TenantsActivity.class);
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }

    public void onClickBrowseIdOne(View view ){

    }

    public void onClickBrowseIdTwo(View view ){

    }

    public void onClickUploadOne(View view ){

    }

    public void onClickUploadTwo(View view ){

    }

    public void onClickJoin(View view){

        CustomerServerDatabaseHandler serverDatabaseHandler = new CustomerServerDatabaseHandler(getApplicationContext(), OwnerAddTenantActivity.this);
        serverDatabaseHandler.execute(Constants.ACTION_CUSTOMER_JOIN_PROPERTY, "");
    }

    //http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample

    private void selectImage() {



        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(OwnerAddTenantActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(OwnerAddTenantActivity.this);
                String userChoosenTask="";

                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, REQUEST_CAMERA);
        startActivityForResult(intent, 1);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),2);
        //startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

}
