package com.comfostays.activities.owner_activities.tenant_activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.activities.PopUpIdProofs;

import java.util.ArrayList;

public class TenantsDetailsActivity extends AppCompatActivity {

    ArrayList<TenantDetailsVO> listOfTenantDetailsVO=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tenants_details);

            CommonFunctionality.setScreenForActivity(this,R.id.backButton,R.id.titleBar,R.id.homeButton, Constants.TITLE_TENANTS_DETAILS);

            Intent intent=getIntent();

            TenantDetailsVO tenantDetails=(TenantDetailsVO)intent.getSerializableExtra(Constants.INTENT_TENANT_SELECTION);

            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.tenantsDetails_linearLayout_idProofs);
            TextView tenantName=(TextView) findViewById(R.id.tenantsDetails_textView_tenantName);
            TextView tenantEmailAddress=(TextView) findViewById(R.id.tenantsDetails_textView_tenantEmailAddress);
            TextView tenantRoomNumber=(TextView) findViewById(R.id.tenantsDetails_textView_roomNumberHeading);
            TextView tenantDurationOfStay=(TextView) findViewById(R.id.tenantsDetails_textView_durationOfStayHeading);
            TextView tenantComplaintsLogged=(TextView) findViewById(R.id.tenantsDetails_textView_complaintsLoggedHeading);
            TextView tenantDescription=(TextView) findViewById(R.id.tenantsDetails_textView_aboutValue);
            TextView tenantContactNumber=(TextView) findViewById(R.id.tenantsDetails_textView_contactDetailsValue);
            TextView tenantResidingSince=(TextView) findViewById(R.id.tenantsDetails_textView_residingSinceValue);

            final ArrayList<Integer> listOfPicIds=new ArrayList<>();
            listOfPicIds.add(R.drawable.pancard);
            listOfPicIds.add(R.drawable.dl);

            if(linearLayout!=null && tenantName!=null && tenantEmailAddress!=null && tenantRoomNumber!=null &&  tenantDurationOfStay!=null && tenantComplaintsLogged!=null && tenantDescription!=null && tenantContactNumber!=null && tenantResidingSince!=null ) {

                String[] idProofs = tenantDetails.getTenantUploadedIdProofs().split(",");
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lparams.rightMargin = 5;
                lparams.topMargin = 5;

                for (int i = 0; i < idProofs.length; i++) {

                    Button button=new Button(getApplicationContext());
                    button.setLayoutParams(lparams);
                    button.setBackgroundColor(Color.parseColor("#008B8B"));
                    button.setText(idProofs[i]);
                    button.setTextColor(Color.BLACK);

                    final int count=i;

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=new Intent(getApplicationContext(),PopUpIdProofs.class);
                            intent.putExtra("IDs",listOfPicIds.get(count));
                            startActivity(intent);
                        }
                    });

                    linearLayout.addView(button);
                }

                String stayDurationString=tenantDetails.getTenanatStayDuration() + "\n Months";
                String loggedComplaintsString=tenantDetails.getTenantLoggedComplaints() + "\n Complaints";

                tenantName.setText(tenantDetails.getTenantName());
                tenantEmailAddress.setText(tenantDetails.getTenantEmailAddress());
                tenantRoomNumber.setText(tenantDetails.getTenantRoomNumber());
                tenantDurationOfStay.setText(stayDurationString);
                tenantComplaintsLogged.setText(loggedComplaintsString);
                tenantDescription.setText(tenantDetails.getTenantDescription());
                tenantContactNumber.setText(tenantDetails.getTenantContactNumber());
                tenantResidingSince.setText(tenantDetails.getTenantResidingSince());
            }

        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent=new Intent(getApplicationContext(),TenantsActivity.class);
        intent.putExtra(Constants.INTENT_LIST_OF_TENANT_DETAILS_VO, listOfTenantDetailsVO);
        startActivity(intent);
        finish();

    }

    public void onClickBackButton(View view){

        Intent intent=new Intent(getApplicationContext(),TenantsActivity.class);
        intent.putExtra(Constants.INTENT_LIST_OF_TENANT_DETAILS_VO, listOfTenantDetailsVO);
        startActivity(intent);
        finish();
    }

    public void onClickHomeButton(View view){

        CommonFunctionality.onClickHomeButton(this);
    }
}
