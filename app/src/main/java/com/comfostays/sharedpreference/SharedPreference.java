package com.comfostays.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.comfostays.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPreference {

    Context context;

    public SharedPreference(Context context){

        this.context=context;

    }

    public void setStringValueInSharedPreference(String key,String value){

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences!=null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public String getStringValueFromSharedPreference(String key){
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences!=null) {
            return preferences.getString(key, Constants.SHARED_PREFERENCE_STRING);
        }else{
            return "";
        }
    }

    public void setBooleanValueInSharedPreference(String key,boolean value){

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences!=null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }
    }

    public boolean getBooleanValueFromSharedPreference(String key){
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences!=null) {
            return preferences.getBoolean(key, false);
        }else{
            return false;
        }
    }

    public void setSetValueInSharedPreference(String key,Set<String> value){

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences!=null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(key, value);
            editor.apply();
        }
    }

    public Set<String> getSetValueFromSharedPreference(String key){
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences!=null) {
            return preferences.getStringSet(key, new HashSet<String>());
        }else{
            return new HashSet<String>();
        }
    }



    public void removeValueFromSharedPreference(String valueToBeDeleted){
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(valueToBeDeleted);
        editor.apply();


    }
    public void clearSharedPreference(){
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public void clearAddProperty(){

        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Constants.SHARED_PREFERENCE_ADD_PROPERTY_BUILDING_NAME);
        editor.remove(Constants.SHARED_PREFERENCE_ADD_PROPERTY_ADDRESS_LINE_1);
        editor.remove(Constants.SHARED_PREFERENCE_ADD_PROPERTY_ADDRESS_LINE_2);
        editor.remove(Constants.SHARED_PREFERENCE_ADD_PROPERTY_POSTAL_CODE);
        editor.remove(Constants.SHARED_PREFERENCE_ADD_PROPERTY_STATE);
        editor.remove(Constants.SHARED_PREFERENCE_ADD_PROPERTY_GEO_LOCATION);
        editor.remove(Constants.SHARED_PREFERENCE_ADD_PROPERTY_PROPERTY_TYPE);
        editor.apply();
    }
}
