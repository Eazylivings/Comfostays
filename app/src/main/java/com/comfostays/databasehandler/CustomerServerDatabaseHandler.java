package com.comfostays.databasehandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.comfostays.Constants;
import com.comfostays.R;
import com.comfostays.OwnerRedirectClass;

import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.customer_activities.JoinPropertyActivity;
import com.comfostays.activities.loginactivities.RegisterActivity;
import com.comfostays.validateprogress.CustomerTestClass;

public class CustomerServerDatabaseHandler extends AsyncTask<String,Void,String> {

    Context applicationContext;
    static String currentAction="";
    String emailAddress="";
    Activity activity;
    String result="";

    PropertyDetailsVO propertyDetailsVO;


    public CustomerServerDatabaseHandler(Context context, Activity baseActivity){
        this.applicationContext=context;
        this.activity=baseActivity;
    }

    @Override
    protected String doInBackground(String... params) {


        currentAction = params[0];
        emailAddress=params[1];

        //String post_data="";
        try {

            //URL url=new URL(Constants.URL_LOGIN);


            if(currentAction.equalsIgnoreCase(Constants.ACTION_CUSTOMER_GET_PROPERTY_DETAILS)) {

                CustomerTestClass testClass = new CustomerTestClass();
                propertyDetailsVO=testClass.getPropertyDetails();
                //result= testClass.checkEmailAndPassword(params[1], params[2]);

            /*url = new URL(Constants.URL_LOGIN);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_USERNAME, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(emailAddress, Constants.SERVER_HANDLER_UTF) + "&"
                    + URLEncoder.encode(Constants.SERVER_HANDLER_PASSWORD,Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[2], Constants.SERVER_HANDLER_UTF);*/
            }else if(currentAction.equalsIgnoreCase(Constants.ACTION_CUSTOMER_JOIN_PROPERTY)) {

                CustomerTestClass testClass = new CustomerTestClass();
                propertyDetailsVO=testClass.getPropertyDetails();
                //result= testClass.checkEmailAndPassword(params[1], params[2]);

            /*url = new URL(Constants.URL_LOGIN);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_USERNAME, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(emailAddress, Constants.SERVER_HANDLER_UTF) + "&"
                    + URLEncoder.encode(Constants.SERVER_HANDLER_PASSWORD,Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[2], Constants.SERVER_HANDLER_UTF);*/
            }

            /*HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            OutputStream outputStream = httpUrlConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Constants.SERVER_HANDLER_UTF));

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();

            outputStream.close();

            InputStream inputStream = httpUrlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpUrlConnection.disconnect();
            if(currentAction.equalsIgnoreCase(Constants.USER_PROFILE_ACTION)){

                userDetails=new UserDetails();

                    JSONArray jsonarray = new JSONArray(result);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        userDetails.setUserId(jsonobject.getInt("userId"));
                        userDetails.setUserName(jsonobject.getString("name"));
                        userDetails.setEmail_address(jsonobject.getString("emailId"));
                        userDetails.setContact_number(jsonobject.getString("phoneNo"));
                        userDetails.setResidential_address(jsonobject.getString("address"));
                    }

                    if (userDetails!=null) {
                        DeviceSetup deviceSetup;
                        deviceSetup=new DeviceSetup(applicationContext);
                        deviceSetup.saveUserDetailsUsingSharedPreference(userDetails);
                    }else {
                        generatePopupMessage("Some error occurred. Please try again after sometime");
                    }
            }*/
            return result;
        }catch(Exception e){
            return "";
        }
    }

    @Override
    protected void onPreExecute() {

        if(currentAction.equalsIgnoreCase(Constants.ACTION_LOGIN)) {

            ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.loginPage_progressBar_progress);
            if(progressBar!=null) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onPostExecute(String accountAuthenticationString) {

        if (currentAction.equalsIgnoreCase(Constants.ACTION_CUSTOMER_GET_PROPERTY_DETAILS) ) {

            Intent intent=new Intent(activity, JoinPropertyActivity.class);

            intent.putExtra(Constants.INTENT_IS_ACTIVITY_LOADED_FOR_FIRST_TIME,false);
            intent.putExtra(Constants.INTENT_PROPERTY_ID,propertyDetailsVO.getPropertyId());
            intent.putExtra(Constants.INTENT_PROPERTY_DETAILS_VO,propertyDetailsVO);
            intent.putExtra(Constants.INTENT_DOES_PROPERTY_EXISTS,true);

            activity.startActivity(intent);
            activity.finish();
        }else if (currentAction.equalsIgnoreCase(Constants.ACTION_CUSTOMER_JOIN_PROPERTY) ) {

            generatePopupMessage("Your request for registration is submitted. Please inform your landlord to approve the request. Once request is approved you can access various features associated with the property",WelcomeScreen.class);
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private void generatePopupMessage(String message,final Class cls){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create(); //Use context
        alertDialog.setTitle(Constants.ALERT_SUCCESS);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(applicationContext,cls);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        applicationContext.startActivity(intent);
                        activity.finish();
                    }
                });
        alertDialog.show();
    }

    public void generateSignUpPopupMessage(String message){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        builder.setTitle(Constants.ALERT_TITLE);
        builder.setMessage(message);

        builder.setPositiveButton(Constants.POPUP_BUTTON_TITLE_SIGNUP, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent(applicationContext, RegisterActivity.class);
                activity.startActivity(intent);
                activity.finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(Constants.POPUP_BUTTON_TITLE_CANCEL, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
