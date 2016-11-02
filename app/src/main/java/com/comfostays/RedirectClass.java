package com.comfostays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.comfostays.VOClass.CostAndChargesVO;
import com.comfostays.VOClass.MealScheduleVO;
import com.comfostays.VOClass.PropertyDetailsVO;
import com.comfostays.VOClass.PropertyLayoutDetailsVO;
import com.comfostays.VOClass.TenantDetailsVO;
import com.comfostays.VOClass.UserDetailsVO;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class RedirectClass {

    Context context;
    Activity activity;

    public RedirectClass(Context context, Activity activity){

        this.context=context;
        this.activity=activity;
    }


    public void getUserDetails(String emailAddress){

        OwnerServerDatabaseHandler handler = new OwnerServerDatabaseHandler(context, activity);
        handler.execute(Constants.ACTION_GET_USER_DETAILS, emailAddress);
    }

    public void getOwnerRegisteredPropertyDetails(UserDetailsVO userDetails){

        OwnerLocalDatabaseHandler databaseHandler=new OwnerLocalDatabaseHandler(context,activity);

        databaseHandler.setUserDetails(userDetails);

        SharedPreference sharedPreference=new SharedPreference(context);
        sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_USERNAME,userDetails.getUserName());
        sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_EMAIL_ADDRESS,userDetails.getEmailAddress());
        sharedPreference.setStringValueInSharedPreference(Constants.SHARED_PREFERENCE_ACCOUNT_TYPE,userDetails.getAccountType());

        if(userDetails.getAccountType().equalsIgnoreCase(Constants.OWNER)) {

            OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(context, activity);
            serverDatabaseHandler.execute(Constants.ACTION_GET_OWNER_REGISTERED_PROPERTIES_DETAILS, userDetails.getEmailAddress());
        }else{

            Intent intent=new Intent(context,WelcomeScreen.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public void getBuildingLayoutDetails(List<PropertyDetailsVO> propertyDetails){

        OwnerLocalDatabaseHandler databaseHandler=new OwnerLocalDatabaseHandler(context,activity);

        String propertyIds="";

        for(int i=0;i<propertyDetails.size();i++){

            databaseHandler.setPropertyDetails(propertyDetails.get(i));
            propertyIds=propertyIds+propertyDetails.get(i).getPropertyId()+":";
        }

        OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(context, activity);
        serverDatabaseHandler.execute(Constants.ACTION_GET_OWNER_REGISTERED_PROPERTY_LAYOUT_DETAILS, propertyIds.substring(0,propertyIds.length()-1));
    }


    public void setPropertyLayoutDetails(ArrayList<PropertyLayoutDetailsVO> listOfPropertyLayoutDetailsVO){

        OwnerLocalDatabaseHandler localDatabaseHandler=new OwnerLocalDatabaseHandler(context,activity);


        for(int i=0;i<listOfPropertyLayoutDetailsVO.size();i++){


            localDatabaseHandler.setPropertyLayoutDetails(listOfPropertyLayoutDetailsVO.get(i));
        }


        SharedPreference preference = new SharedPreference(context);
        preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_USER_LOGGED_IN, true);

        OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(context, activity);
        serverDatabaseHandler.execute(Constants.ACTION_GET_CURRENT_TENANT_DETAILS,"");
    }

    public void setListOfTenantDetails(ArrayList<TenantDetailsVO> listOfTenantDetails){

        OwnerLocalDatabaseHandler databaseHandler=new OwnerLocalDatabaseHandler(context,activity);

        for(int i=0;i<listOfTenantDetails.size();i++){

            databaseHandler.setCurrentTenantDetails(listOfTenantDetails.get(i));
        }

        OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(context, activity);
        serverDatabaseHandler.execute(Constants.ACTION_GET_COST_AND_CHARGES,"");
    }

    public void setCostAndCharges(CostAndChargesVO costAndChargesVO){

        OwnerLocalDatabaseHandler databaseHandler=new OwnerLocalDatabaseHandler(context,activity);

        databaseHandler.setCostAndCharges(costAndChargesVO);

        OwnerServerDatabaseHandler serverDatabaseHandler = new OwnerServerDatabaseHandler(context, activity);
        serverDatabaseHandler.execute(Constants.ACTION_GET_MEAL_TIMING_AND_SCHEDULE,"");
    }

    public void setMealTimingAndSchedule(MealScheduleVO mealScheduleVO){

        OwnerLocalDatabaseHandler databaseHandler=new OwnerLocalDatabaseHandler(context,activity);

        databaseHandler.setMealSchedule(mealScheduleVO);

        Intent intent=new Intent(context,WelcomeScreen.class);
        activity.startActivity(intent);
        activity.finish();



    }
}
