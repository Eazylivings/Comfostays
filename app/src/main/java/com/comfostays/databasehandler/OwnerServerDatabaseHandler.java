package com.comfostays.databasehandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.comfostays.CommonFunctionality;
import com.comfostays.R;
import com.comfostays.Constants;
import com.comfostays.RedirectClass;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.VOClass.PropertyLayoutDetailsVO;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.VOClass.UserDetailsVO;
import com.comfostays.WelcomeScreen;
import com.comfostays.activities.OccupancyActivity;
import com.comfostays.activities.RoomLevelOccupancy;
import com.comfostays.activities.loginactivities.LoginActivity;
import com.comfostays.activities.loginactivities.RegisterActivity;
import com.comfostays.sharedpreference.SharedPreference;
import com.comfostays.validateprogress.TestClass;

import java.util.ArrayList;
import java.util.List;

public class OwnerServerDatabaseHandler extends AsyncTask<String,Void,String> {

    Context applicationContext;
    static String currentAction="";
    String emailAddress="";
    Activity activity;
    String result="";
    List<PropertyDetailsVO> listOfPropertyDetails;
    UserDetailsVO userDetails;
    //TenantDetailsVO tenantDetails;
    ArrayList<TenantDetailsVO> listOfTenantsDetailsVO;
    String upperLevelRoomOccupancyCoordinates;
    String roomLevelOccupancy;

    ArrayList<PropertyLayoutDetailsVO> listOfPropertyLayoutDetailsVO;

    public OwnerServerDatabaseHandler(Context context, Activity baseActivity){
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


        if(currentAction.equalsIgnoreCase(Constants.ACTION_LOGIN)) {

            TestClass testClass = new TestClass();
            result= testClass.checkEmailAndPassword(params[1], params[2]);

            /*url = new URL(Constants.URL_LOGIN);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_USERNAME, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(emailAddress, Constants.SERVER_HANDLER_UTF) + "&"
                    + URLEncoder.encode(Constants.SERVER_HANDLER_PASSWORD,Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[2], Constants.SERVER_HANDLER_UTF);*/
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_REGISTER)){

            TestClass testClass = new TestClass();
            result= testClass.userRegistration(params[1],params[2],params[3],params[4],params[5],applicationContext);

                /*url = new URL(Constants.URL_CUSTOMER_REGISTER);
                userDetails=new UserDetails();
                post_data = URLEncoder.encode(Constants.SERVER_HANDLER_USERNAME,Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[1], Constants.SERVER_HANDLER_UTF) + "&"
                        + URLEncoder.encode(Constants.SERVER_HANDLER_PASSWORD, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[2], Constants.SERVER_HANDLER_UTF)+"&"
                        + URLEncoder.encode("name",Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(emailAddress, "UTF-8")+"&"
                        + URLEncoder.encode(Constants.SERVER_HANDLER_PHONE_NUMBER, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[4], Constants.SERVER_HANDLER_UTF);

                userDetails.setUserName(params[1]);
                userDetails.setPassword(params[2]);
                userDetails.setEmail_address(emailAddress);
                userDetails.setContact_number(params[4]);*/

        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_USER_DETAILS)) {

            TestClass testClass = new TestClass();
            userDetails=testClass.getUserDetails(applicationContext,activity);
            result=Constants.AUTHENTICATION_USER_DETAILS_FETCHED_SUCCESS;

            // Through queries we will check if user is existing or not. At server only we will check and return th result accordingly.

            /*url = new URL(Constants.URL_FORGOT_PASSWORD);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_EMAIL_ADDRESS, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[1], Constants.SERVER_HANDLER_UTF);*/
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_FORGOT_PASSWORD)) {

            TestClass testClass = new TestClass();
            result= testClass.passwordResetSuccessfully(params[1]);

            // Through queries we will check if user is existing or not. At server only we will check and return th result accordingly.

            /*url = new URL(Constants.URL_FORGOT_PASSWORD);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_EMAIL_ADDRESS, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[1], Constants.SERVER_HANDLER_UTF);*/
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_OWNER_REGISTERED_PROPERTIES_DETAILS)) {

            TestClass testClass = new TestClass();
            listOfPropertyDetails=testClass.getOwnerRegisteredProperties(params[1]);

            // Through queries we will check if user is existing or not. At server only we will check and return th result accordingly.

            /*url = new URL(Constants.URL_FORGOT_PASSWORD);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_EMAIL_ADDRESS, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[1], Constants.SERVER_HANDLER_UTF);*/
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_OWNER_REGISTERED_PROPERTY_LAYOUT_DETAILS)){

            TestClass testClass = new TestClass();
            listOfPropertyLayoutDetailsVO =testClass.getPropertyLayoutDetailsVO();

        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_CURRENT_TENANT_DETAILS)) {

            TestClass testClass = new TestClass();
            listOfTenantsDetailsVO =testClass.getListOfTenantDetailsVO();

            // Through queries we will check if user is existing or not. At server only we will check and return th result accordingly.

            /*url = new URL(Constants.URL_FORGOT_PASSWORD);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_EMAIL_ADDRESS, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[1], Constants.SERVER_HANDLER_UTF);*/
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_ROOM_OCCUPANCY_UPPER_LEVEL)) {

            SharedPreference sharedPreference=new SharedPreference(applicationContext);

            boolean isCurrentOccupancyCheckedBoxChecked=sharedPreference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED);

            if(isCurrentOccupancyCheckedBoxChecked){

                TestClass testClass = new TestClass();
                upperLevelRoomOccupancyCoordinates=testClass.getUpperLevelRoomOccupancy(2016,2016,8,8);

            }else{

                int fromYearInt=Integer.parseInt(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_FROM_YEAR));
                int toYearInt=Integer.parseInt(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_TO_YEAR));
                int fromMonthInt=Integer.parseInt(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_FROM_MONTH));
                int toMonthInt=Integer.parseInt(sharedPreference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_OCCUPANCY_TO_MONTH));

                TestClass testClass = new TestClass();
                upperLevelRoomOccupancyCoordinates=testClass.getUpperLevelRoomOccupancy(fromYearInt,toYearInt,fromMonthInt,toMonthInt);
            }


            // Through queries we will check if user is existing or not. At server only we will check and return th result accordingly.

            /*url = new URL(Constants.URL_FORGOT_PASSWORD);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_EMAIL_ADDRESS, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[1], Constants.SERVER_HANDLER_UTF);*/
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_ROOM_OCCUPANCY_ROOM_LEVEL)) {

            TestClass testClass = new TestClass();
            roomLevelOccupancy=testClass.getRoomLevelRoomOccupancy(params[1]);

            // Through queries we will check if user is existing or not. At server only we will check and return th result accordingly.

            /*url = new URL(Constants.URL_FORGOT_PASSWORD);
            post_data = URLEncoder.encode(Constants.SERVER_HANDLER_EMAIL_ADDRESS, Constants.SERVER_HANDLER_UTF) + "=" + URLEncoder.encode(params[1], Constants.SERVER_HANDLER_UTF);*/
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_NOTIFY_TENANTS)){

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

        CommonFunctionality commonFunctionality = new CommonFunctionality(applicationContext, activity);

        if (currentAction.equalsIgnoreCase(Constants.ACTION_LOGIN) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_LOGIN_SUCCESS)) {
            ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.loginPage_progressBar_progress);

            if (progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(applicationContext,activity);
            localDatabaseHandler.deleteDatabase(applicationContext);

            RedirectClass redirectClass = new RedirectClass(applicationContext, activity);
            redirectClass.getUserDetails(emailAddress);

        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_LOGIN) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_LOGIN_FAIL)) {

            SharedPreference preference = new SharedPreference(applicationContext);
            preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_USER_LOGGED_IN, false);

            ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.loginPage_progressBar_progress);
            if (progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
            commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_LOGIN_FAILED);

        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_REGISTER) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_REGISTER_SUCCESS)) {

            ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.register_progressBar);

            if (progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            SharedPreference preference = new SharedPreference(applicationContext);
            preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_USER_LOGGED_IN, true);

            Intent intent = new Intent(applicationContext, WelcomeScreen.class);
            activity.startActivity(intent);
            activity.finish();

        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_REGISTER) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_REGISTER_FAIL)) {

            commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_REGISTER_FAILED);

        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_FORGOT_PASSWORD) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_PASSWORD_RESET_SUCCESS)) {

            Intent intent = new Intent(applicationContext, LoginActivity.class);
            intent.putExtra(Constants.ACTION_FORGOT_PASSWORD, Constants.AUTHENTICATION_PASSWORD_RESET_SUCCESS);
            activity.startActivity(intent);
            activity.finish();

        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_FORGOT_PASSWORD) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_PASSWORD_RESET_FAIL)) {
            commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_PASSWORD_NOT_RESET);

        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_FORGOT_PASSWORD) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_EXISTING_USER_FAIL)) {
            generateSignUpPopupMessage(Constants.POPUP_MESSAGE_USER_NOT_PRESENT);

        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_GET_USER_DETAILS) && accountAuthenticationString.equalsIgnoreCase(Constants.AUTHENTICATION_USER_DETAILS_FETCHED_SUCCESS)) {

            RedirectClass redirectClass = new RedirectClass(applicationContext, activity);
            redirectClass.getOwnerRegisteredPropertyDetails(userDetails);


        } else if (currentAction.equalsIgnoreCase(Constants.ACTION_GET_OWNER_REGISTERED_PROPERTIES_DETAILS)) {

            SharedPreference sharedPreference=new SharedPreference(applicationContext);


            if(listOfPropertyDetails.size()==0){


                sharedPreference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_ANY_PROPERTY_REGISTERED,false);

                Intent intent = new Intent(applicationContext, WelcomeScreen.class);
                activity.startActivity(intent);
                activity.finish();
            }else{
                sharedPreference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_ANY_PROPERTY_REGISTERED,true);

                RedirectClass redirectClass = new RedirectClass(applicationContext, activity);
                redirectClass.getBuildingLayoutDetails(listOfPropertyDetails);
            }

        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_OWNER_REGISTERED_PROPERTY_LAYOUT_DETAILS)){

            RedirectClass redirectClass = new RedirectClass(applicationContext, activity);
            redirectClass.setPropertyLayoutDetails(listOfPropertyLayoutDetailsVO);

        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_ROOM_OCCUPANCY_UPPER_LEVEL)){

            Intent intent=new Intent(activity,OccupancyActivity.class);
            SharedPreference sharedPreference=new SharedPreference(applicationContext);
            sharedPreference.setStringValueInSharedPreference(Constants.INTENT_ROOM_OCCUPANCY_UPPER_LEVEL_COORDINATES,upperLevelRoomOccupancyCoordinates);
            activity.startActivity(intent);
            activity.finish();

        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_ROOM_OCCUPANCY_ROOM_LEVEL)){

            Intent intent=new Intent(activity,RoomLevelOccupancy.class);
            String[] result=roomLevelOccupancy.split(":");
            String[] typesOfRoom={"Single Sharing","Double Sharing","Triple Sharing"};
            double[] percentageSharesOfEachRoom={Integer.parseInt(result[0]),Integer.parseInt(result[1]),Integer.parseInt(result[2])};
            intent.putExtra(Constants.SHARED_PREFERENCE_LIST_TYPES_OF_ROOMS, typesOfRoom);
            intent.putExtra(Constants.SHARED_PREFERENCE_LIST_PERCENTAGE_OF_EACH_ROOM, percentageSharesOfEachRoom);
            activity.startActivity(intent);
            activity.finish();
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_GET_CURRENT_TENANT_DETAILS)){

            RedirectClass redirectClass = new RedirectClass(applicationContext, activity);
            redirectClass.setListOfTenantDetails(listOfTenantsDetailsVO);
        }else if(currentAction.equalsIgnoreCase(Constants.ACTION_NOTIFY_TENANTS)){

            generatePopupMessage(Constants.POPUP_MESSAGE_TENANTS_NOTIFIED,WelcomeScreen.class);
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
