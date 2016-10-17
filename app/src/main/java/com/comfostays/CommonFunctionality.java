package com.comfostays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CommonFunctionality {

    Context context;
    Activity activity;

    public CommonFunctionality(Context context, Activity activity){

        this.activity=activity;
        this.context=context;
    }

    public void setTitleBar(int backButtonId,int titleBarId, int homeButtonId, String titleBarText){

        try{

            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            //Title Bar
            TextView titleBar = (TextView) activity.findViewById(titleBarId);
            if(titleBar!=null) {
                int textSize=(width*6)/100;
                titleBar.getLayoutParams().width = (width * 7) / 10;
                titleBar.setText(titleBarText);
                titleBar.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            }
            ImageView backButton = (ImageView) activity.findViewById(backButtonId);
            if(backButton!=null) {
                backButton.getLayoutParams().width = (width) / 10;
            }
            ImageView homeButton = (ImageView) activity.findViewById(homeButtonId);
            if(homeButton!=null) {
                homeButton.getLayoutParams().width = (width) / 10;
            }

        }catch(Exception e){

        }

    }

    public void onClickListenerForImage(int imageId){

        ImageView imageView = (ImageView) activity.findViewById(imageId);

        if (imageView != null) {

            imageView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ImageView view = (ImageView) v;
                            //overlay is black with transparency of 0x77 (119)
                            view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                            view.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL: {
                            ImageView view = (ImageView) v;
                            //clear the overlay
                            view.getDrawable().clearColorFilter();
                            view.invalidate();
                            break;
                        }
                    }

                    return false;
                }
            });
        }

    }

    public void onClickListenerForText(int textId){

        TextView textView = (TextView) activity.findViewById(textId);

        if (textView != null) {

            textView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            ((TextView)v).setTextColor(0x77000000);
                            v.invalidate();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            ((TextView)v).setTextColor(0x77000000); //black
                            v.invalidate();
                            break;
                    }
                    return false;

                }
            });
        }

    }

    public void onBackPressed(Class cls){

        Intent intent = new Intent(context,cls);
        activity.startActivity(intent);
        activity.finish();
    }

    public void onClickHomeButton(){

        Intent intent = new Intent(context, WelcomeScreen.class);
        activity.startActivity(intent);
        activity.finish();
    }


    public void generatePopupMessage(String alertTile,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(alertTile);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void generatePopUpMessageForExceptions(){

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        LayoutInflater factory = LayoutInflater.from(activity);
        final View view = factory.inflate(R.layout.alert_dialog_image, null);
        alertDialog.setView(view);
        alertDialog.setTitle("Bannnnnngggg !!!!!!!!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ALRIGHT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public void generateActivityRedirectPopupMessage(String alertTitle,String positiveHeading,String message,final Class cls){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        builder.setTitle(alertTitle);
        builder.setMessage(message);

        builder.setPositiveButton(positiveHeading, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent(context,cls);
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

    public int getScreenWidth(){

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public int getScreenHeight(){

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public ArrayList<String> getColorCodes(){

        ArrayList<String> listOfColorCodes=new ArrayList<>();

        listOfColorCodes.add("#7FFFD4");
        listOfColorCodes.add("#DC143C");
        listOfColorCodes.add("#6495ED");
        listOfColorCodes.add("#FF69B4");
        listOfColorCodes.add("#F08080");
        listOfColorCodes.add("#98FB98");
        listOfColorCodes.add("#DDA0DD");
        listOfColorCodes.add("#FA8072");
        listOfColorCodes.add("#00FF7F");
        listOfColorCodes.add("#F5DEB3");
        listOfColorCodes.add("#FF6347");
        listOfColorCodes.add("#1aff1a");
        listOfColorCodes.add("#7FFFD4");
        listOfColorCodes.add("#DC143C");
        listOfColorCodes.add("#6495ED");
        listOfColorCodes.add("#FF69B4");
        listOfColorCodes.add("#F08080");
        listOfColorCodes.add("#98FB98");
        listOfColorCodes.add("#DDA0DD");
        listOfColorCodes.add("#FA8072");
        listOfColorCodes.add("#00FF7F");
        listOfColorCodes.add("#F5DEB3");
        listOfColorCodes.add("#FF6347");
        listOfColorCodes.add("#FF6347");
        listOfColorCodes.add("#1aff1a");
        listOfColorCodes.add("#7FFFD4");
        listOfColorCodes.add("#DC143C");
        listOfColorCodes.add("#6495ED");
        listOfColorCodes.add("#FF69B4");


        return listOfColorCodes;

    }
}
