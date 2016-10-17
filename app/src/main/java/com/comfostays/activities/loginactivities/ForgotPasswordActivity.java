package com.comfostays.activities.loginactivities;

import android.content.Intent;
import android.graphics.Point;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.Validators;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;

public class ForgotPasswordActivity extends AppCompatActivity {

    CommonFunctionality commonFunctionality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgot_password);

            setTitleBar(R.id.forgot_password_backButton,R.id.forgot_password_titleBar,Constants.TITLE_FORGOT_PASSWORD);
            commonFunctionality=new CommonFunctionality(getApplicationContext(),this);
            commonFunctionality.onClickListenerForImage(R.id.register_backButton);
        }
        catch(Exception e){
            commonFunctionality.generatePopupMessage(Constants.ALERT_EXCEPTION,Constants.EXCEPTION_RESET_PASSWORD);
        }
    }

    @Override
    public void onBackPressed(){

        commonFunctionality.onBackPressed(LoginActivity.class);
    }

    public void onClickBackButton(View view){

        commonFunctionality.onBackPressed(LoginActivity.class);
    }

    public void onClickRetrievePassword(View view){

        try {

            if(Validators.isInternetAvailable(getApplicationContext())) {
                EditText emailAddress = (EditText) findViewById(R.id.forgotPassword_editText_emailAddress);

                boolean isEmailAddressNotNull = Validators.checkEmptyInput(emailAddress);

                if (isEmailAddressNotNull) {
                    OwnerServerDatabaseHandler handler = new OwnerServerDatabaseHandler(getApplicationContext(), this);
                    handler.execute(Constants.ACTION_FORGOT_PASSWORD, emailAddress.getText().toString());

                } else {
                    commonFunctionality.generatePopupMessage(Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_EMAIL_ADDRESS);
                }
            }else{
                Snackbar snackBar = Snackbar.make(findViewById(R.id.coordinatorLayout), Constants.POPUP_MESSAGE_NO_INTERNET, Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        }catch(Exception e){
            commonFunctionality.generatePopupMessage(Constants.ALERT_EMPTY_TEXT,Constants.EXCEPTION_RESET_PASSWORD);
        }

    }

    public void onClickRegisterButton(View view){

        Intent intent = new Intent(this, RegisterActivity.class);
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
                int textSize = (width * 6) / 100;
                titleBar.getLayoutParams().width = (width * 7) / 10;
                titleBar.setText(titleBarText);
                titleBar.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            ImageView backButton = (ImageView) findViewById(backButtonId);
            if (backButton != null) {
                backButton.getLayoutParams().width = (width*2) / 10;
            }

        } catch (Exception e) {

        }
    }
}
