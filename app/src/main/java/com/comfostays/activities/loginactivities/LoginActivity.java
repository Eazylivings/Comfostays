package com.comfostays.activities.loginactivities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.comfostays.CommonFunctionality;
import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.Validators;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            Intent intent = getIntent();
            String message=intent.getStringExtra(Constants.ACTION_FORGOT_PASSWORD);

            if(message!=null && message.equalsIgnoreCase(Constants.AUTHENTICATION_PASSWORD_RESET_SUCCESS)){

                coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);

                if(coordinatorLayout!=null){

                    final Snackbar snackBar = Snackbar.make(coordinatorLayout, Constants.AUTHENTICATION_PASSWORD_RESET_SUCCESS, Snackbar.LENGTH_LONG);

                    snackBar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();

                }
            }

            progressBar = (ProgressBar) findViewById(R.id.loginPage_progressBar_progress);
            if (progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }

        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {

        if (exit) {
            finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    public void onClickLoginButton(View view){

        try {

            coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);

            if (Validators.isInternetAvailable(getApplicationContext())) {

                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                EditText emailAddress = (EditText) findViewById(R.id.loginPage_editText_emailAddress);
                EditText password = (EditText) findViewById(R.id.loginPage_editText_password);

                boolean isEmailAddressNotNull = Validators.checkEmptyInput(emailAddress);
                boolean isPasswordNotNull = Validators.checkEmptyInput(password);

                if (isEmailAddressNotNull && isPasswordNotNull) {

                    OwnerServerDatabaseHandler handler = new OwnerServerDatabaseHandler(getApplicationContext(), this);
                    handler.execute(Constants.ACTION_LOGIN, emailAddress.getText().toString().trim(), password.getText().toString());

                } else if(!isEmailAddressNotNull){

                    if (progressBar != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_EMAIL_ADDRESS);
                }else if(!isPasswordNotNull){

                    if (progressBar != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    CommonFunctionality.generatePopupMessage(this,Constants.ALERT_EMPTY_TEXT,Constants.POPUP_MESSAGE_NO_PASSWORD);

                }
            } else if(coordinatorLayout!=null){
                Snackbar snackBar = Snackbar.make(coordinatorLayout, Constants.POPUP_MESSAGE_NO_INTERNET, Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        }catch(Exception e){
            CommonFunctionality.generatePopUpMessageForExceptions(this);
        }
    }

    public void onClickRegisterButton(View view){

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickForgotPassword(View view){

        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
