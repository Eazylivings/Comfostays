package com.comfostays;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.EditText;

public class Validators {

    public static boolean checkEmptyInput(EditText inputText){

        if(inputText!=null && !inputText.getText().toString().equalsIgnoreCase("")){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isInternetAvailable(Context context) {

        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    /*public boolean isConnectedToWiFi(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return  activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }*/
}
