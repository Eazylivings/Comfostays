package com.comfostays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonFunctionality {


    public static void setTitleBar(Activity activity,int backButtonId,int titleBarId, int homeButtonId, String titleBarText){

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

            generatePopUpMessageForExceptions(activity);

        }

    }

    public static void onClickListenerForImage(Activity activity,int imageId){

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

    public static void setScreenForActivity(Activity activity,int backButtonId,int titleBarId, int homeButtonId, String titleBarText){

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setTitleBar(activity,backButtonId,titleBarId,homeButtonId,titleBarText);
        onClickListenerForImage(activity,backButtonId);
        onClickListenerForImage(activity,homeButtonId);
    }

    public static void onClickListenerForText(Activity activity,int textId){

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

    public static void onBackPressed(Activity activity,Class cls){

        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void onClickHomeButton(Activity activity){

        Intent intent = new Intent(activity, WelcomeScreen.class);
        activity.startActivity(intent);
        activity.finish();
    }


    public static void generatePopupMessage(Activity activity,String alertTile,String message){
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

    public static void  generatePopUpMessageForExceptions(Activity activity){

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

    public static void generateActivityRedirectPopupMessage(final Activity activity,String alertTitle,String positiveHeading,String message,final Class cls){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        builder.setTitle(alertTitle);
        builder.setMessage(message);

        builder.setPositiveButton(positiveHeading, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent(activity,cls);
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

    public static int getScreenWidth(Activity activity){

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public static int getScreenHeight(Activity activity){

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }

    public static String[] getColorCodes(){

        String[] colorCodes=new String[31];

        colorCodes[0]="#7FFFD4";
        colorCodes[1]="#FF69B4";
        colorCodes[2]="#F08080";
        colorCodes[3]="#98FB98";
        colorCodes[4]="#DDA0DD";
        colorCodes[5]="#FA8072";
        colorCodes[6]="#00FF7F";
        colorCodes[7]="#F5DEB3";
        colorCodes[8]="#FF6347";
        colorCodes[9]="#1aff1a";
        colorCodes[10]="#DC143C";
        colorCodes[11]="#6495ED";
        colorCodes[12]="#FF69B4";
        colorCodes[13]="#F08080";
        colorCodes[14]="#98FB98";
        colorCodes[15]="#DDA0DD";
        colorCodes[16]="#FA8072";
        colorCodes[17]="#00FF7F";
        colorCodes[18]="#F5DEB3";
        colorCodes[19]="#FF6347";
        colorCodes[20]="#7FFFD4";
        colorCodes[21]="#FF6347";
        colorCodes[22]="#7FFFD4";
        colorCodes[23]="#1aff1a";
        colorCodes[24]="#7FFFD4";
        colorCodes[25]="#7FFFD4";
        colorCodes[26]="#DC143C";
        colorCodes[27]="#6495ED";
        colorCodes[28]="#7FFFD4";
        colorCodes[29]="#FF69B4";
        colorCodes[30]="#DDA0DD";



        return colorCodes;

    }
}
