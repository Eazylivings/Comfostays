package com.comfostays.activities.loginactivities;

import android.content.Intent;
import android.graphics.Point;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.VOClass.UserDetailsVO;
import com.comfostays.Validators;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

public class RegisterActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private SharedPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        preference=new SharedPreference(getApplication());

        progressBar=(ProgressBar)findViewById(R.id.register_progressBar);

        if(progressBar!=null){
            progressBar.setVisibility(View.INVISIBLE);
        }

        setTitleBar(R.id.register_backButton,R.id.register_titleBar,Constants.TITLE_REGISTER);

        CommonFunctionality.onClickListenerForImage(this,R.id.register_backButton);
    }

    @Override
    public void onBackPressed(){

        CommonFunctionality.onBackPressed(this,LoginActivity.class);
    }

    public void onClickBackButton(View view){

        CommonFunctionality.onBackPressed(this,LoginActivity.class);
    }

    public void onClickRegister(View view){

        EditText userName=(EditText)findViewById(R.id.register_editText_Name);
        EditText emailAddress=(EditText)findViewById(R.id.register_editText_email);
        EditText contactNumber=(EditText)findViewById(R.id.register_editText_contactNumber);
        EditText password=(EditText)findViewById(R.id.register_editText_password);
        EditText dateOfBirth=(EditText)findViewById(R.id.editText_dateOfBirth);
        RadioButton customerRadio=(RadioButton)findViewById(R.id.register_radioButton_customer);
        RadioButton ownerRadio=(RadioButton)findViewById(R.id.register_radioButton_owner);

        boolean isUserNameNotNull= Validators.checkEmptyInput(userName);
        boolean isEmailAddressNotNull=Validators.checkEmptyInput(emailAddress);
        boolean isContactNumberNotNull=Validators.checkEmptyInput(contactNumber);
        boolean isPasswordNotNull=Validators.checkEmptyInput(password);
        boolean isDateOfBirthNotNull=Validators.checkEmptyInput(dateOfBirth);

        if(Validators.isInternetAvailable(getApplicationContext())) {

            if (isUserNameNotNull && isEmailAddressNotNull && isContactNumberNotNull && isPasswordNotNull && isDateOfBirthNotNull) {

                OwnerServerDatabaseHandler handler = new OwnerServerDatabaseHandler(getApplicationContext(), this);
                OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(getApplicationContext(),this);
                UserDetailsVO userDetailsVO=new UserDetailsVO();

                preference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_USERNAME,userName.getText().toString());
                preference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_EMAIL_ADDRESS,emailAddress.getText().toString());

                userDetailsVO.setUserName(userName.getText().toString());
                userDetailsVO.setEmailAddress(emailAddress.getText().toString());
                userDetailsVO.setContactNumber(contactNumber.getText().toString());
                userDetailsVO.setDateOfBirth(dateOfBirth.getText().toString());

                if (customerRadio!=null && customerRadio.isChecked()) {

                    preference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_ACCOUNT_TYPE,Constants.CUSTOMER);
                    userDetailsVO.setAccountType(Constants.CUSTOMER);

                    localDatabaseHandler.setUserDetails(userDetailsVO);

                    if (progressBar != null) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    handler.execute(Constants.ACTION_REGISTER, userName.getText().toString(), emailAddress.getText().toString(), contactNumber.getText().toString(), password.getText().toString(),dateOfBirth.getText().toString(), Constants.CUSTOMER);
                } else if (ownerRadio!=null && ownerRadio.isChecked()) {

                    preference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_ACCOUNT_TYPE,Constants.OWNER);
                    userDetailsVO.setAccountType(Constants.OWNER);

                    localDatabaseHandler.setUserDetails(userDetailsVO);

                    if (progressBar != null) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    handler.execute(Constants.ACTION_REGISTER, userName.getText().toString(), emailAddress.getText().toString(), contactNumber.getText().toString(), password.getText().toString(),dateOfBirth.getText().toString(), Constants.OWNER);

                } else {
                    CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_TYPE_SELECTION);
                }
            } else if (!isUserNameNotNull) {
                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_USERNAME);
            } else if (!isContactNumberNotNull) {
                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_CONTACT_NUMBER);
            } else if (!isEmailAddressNotNull) {
                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_EMAIL_ADDRESS);
            } else if (!isPasswordNotNull) {
                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_PASSWORD);
            }else if(!isDateOfBirthNotNull){

                CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_DATE_OF_BIRTH);
            }
        }else{

            CoordinatorLayout coordinatorLayout=(CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            if(coordinatorLayout!=null) {
                Snackbar snackBar = Snackbar.make(coordinatorLayout, Constants.POPUP_MESSAGE_NO_INTERNET, Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        }
    }

    public void onClickLogin(View view){

        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void setTitleBar(int backButtonId,int titleBarId, String titleBarText) {

        try {

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            //Title Bar
            TextView titleBar = (TextView) findViewById(titleBarId);
            if (titleBar != null) {
                int textSize = (width * 5) / 100;
                titleBar.getLayoutParams().width = (width * 7) / 10;
                titleBar.setText(titleBarText);
                titleBar.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            ImageView backButton = (ImageView) findViewById(backButtonId);
            if (backButton != null) {
                backButton.getLayoutParams().width = (width*2) / 10;
            }

        } catch (Exception e) {

            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }
}
