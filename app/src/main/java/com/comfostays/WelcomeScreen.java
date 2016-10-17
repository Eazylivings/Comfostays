package com.comfostays;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comfostays.activities.ContactUsActivity;
import com.comfostays.activities.MealsActivity;
import com.comfostays.activities.MyFinancesActivity;
import com.comfostays.activities.NotificationsActivity;
import com.comfostays.activities.OccupancyActivity;
import com.comfostays.activities.TenantsActivity;
import com.comfostays.activities.buildingsetup.AddPropertyActivity;
import com.comfostays.activities.loginactivities.LoginActivity;
import com.comfostays.activities.MyPropertiesActivity;
import com.comfostays.activities.TutorialActivity;
import com.comfostays.databasehandler.OwnerServerDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

public class WelcomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreference preference;
    boolean isAnyPropertyRegistered;
    boolean isMealOffered=true;
    CommonFunctionality commonFunctionality;
    String loggedUserEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome_screen);

            preference = new SharedPreference(getApplicationContext());
            commonFunctionality = new CommonFunctionality(getApplicationContext(), this);

            String userName = preference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_USERNAME);
            loggedUserEmailAddress = preference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_EMAIL_ADDRESS);
            //String loggedPersonalityType=preference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_ACCOUNT_TYPE);
            //String loggedPersonalityType=Constants.OWNER;
            setTitle(Constants.WELCOME + userName);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            if (fab != null) {

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

                drawer.setDrawerListener(toggle);
                toggle.syncState();
            }


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            if (navigationView != null) {

                Menu menu = navigationView.getMenu();

                if (!isMealOffered) {
                    menu.removeItem(R.id.owner_meals);
                }

                navigationView.setNavigationItemSelectedListener(this);

                View header = navigationView.getHeaderView(0);

                TextView drawerUserName = (TextView) header.findViewById(R.id.welcomeScreen_drawer_textViewView_userName);
                TextView drawerEmailAddress = (TextView) header.findViewById(R.id.welcomeScreen_drawer_textViewView_userEmailAddress);
                drawerUserName.setText(userName);
                drawerEmailAddress.setText(loggedUserEmailAddress);

                isAnyPropertyRegistered = preference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_ANY_PROPERTY_REGISTERED);

                if (!isAnyPropertyRegistered) {

                    ImageView view_0_0 = (ImageView) findViewById(R.id.welcomeScreen_imageView_0_0);
                    ImageView view_0_1 = (ImageView) findViewById(R.id.welcomeScreen_imageView_0_1);
                    ImageView view_0_2 = (ImageView) findViewById(R.id.welcomeScreen_imageView_0_2);
                    ImageView view_1_0 = (ImageView) findViewById(R.id.welcomeScreen_imageView_1_0);
                    ImageView view_1_1 = (ImageView) findViewById(R.id.welcomeScreen_imageView_1_1);
                    ImageView view_1_2 = (ImageView) findViewById(R.id.welcomeScreen_imageView_1_2);

                    if (view_0_0 != null && view_0_1 != null && view_0_2 != null && view_1_0 != null && view_1_1 != null && view_1_2 != null) {
                        view_0_0.setColorFilter(Color.argb(150, 200, 200, 200));
                        view_0_1.setColorFilter(Color.argb(150, 200, 200, 200));
                        view_0_2.setColorFilter(Color.argb(150, 200, 200, 200));
                        view_1_0.setColorFilter(Color.argb(150, 200, 200, 200));
                        view_1_1.setColorFilter(Color.argb(150, 200, 200, 200));
                        view_1_2.setColorFilter(Color.argb(150, 200, 200, 200));
                    }
                }
            }
        }catch(Exception e){

            commonFunctionality.generatePopUpMessageForExceptions();
        }
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer!=null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }

        if (exit) {
            finish();
        } else {
            Toast.makeText(this, Constants.TOAST_BACK_FOR_EXIT,
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.owner_my_properties) {

            if(isAnyPropertyRegistered) {

                Intent intent = new Intent(getApplicationContext(), MyPropertiesActivity.class);
                startActivity(intent);
                finish();
            }else{
                commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
            }

        } else if (id == R.id.owner_add_properties) {

            preference.clearAddProperty();

            Intent intent=new Intent(getApplicationContext(),AddPropertyActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.owner_occupancy) {

            if(isAnyPropertyRegistered){

                preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_OCCUPANCY_LOADED_FOR_FIRST_TIME,true);
                preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED,false);

                Intent intent=new Intent(getApplicationContext(), OccupancyActivity.class);
                startActivity(intent);
                finish();

            }else{
                commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
            }

        }else if (id == R.id.owner_meals) {

            if(isAnyPropertyRegistered){

                Intent intent=new Intent(getApplicationContext(), MealsActivity.class);
                startActivity(intent);
                finish();

            }else{
                commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
            }

        } else if (id == R.id.owner_contact_us) {

            Intent intent=new Intent(getApplicationContext(), ContactUsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.owner_logOut) {

            preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_USER_LOGGED_IN,false);

            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer!=null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void onClickMyProperty(View view){

        if(isAnyPropertyRegistered){

            Intent intent=new Intent(getApplicationContext(),MyPropertiesActivity.class);
            startActivity(intent);
            finish();

        }else{

            commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
        }

    }

    public void onClickOccupancy(View view){

        if(isAnyPropertyRegistered){
            preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_OCCUPANCY_LOADED_FOR_FIRST_TIME,true);
            preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED,false);

            Intent intent=new Intent(getApplicationContext(), OccupancyActivity.class);
            startActivity(intent);
            finish();

        }else{
            commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
        }
    }

    public void onClickTenant(View view){

        if(isAnyPropertyRegistered){

            Intent intent=new Intent(getApplicationContext(),TenantsActivity.class);
            startActivity(intent);
            finish();

        }else{
            commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
        }
    }

    public void onClickFinances(View view){

        commonFunctionality.generatePopupMessage(Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_BUY_PAID_VERSION);

       /* Intent intent=new Intent(getApplicationContext(), MyFinancesActivity.class);
        startActivity(intent);
        finish();*/

        /*if(isAnyPropertyRegistered  && isMealOffered){

            Intent intent=new Intent(getApplicationContext(), MealsOldActivity.class);
            startActivity(intent);
            finish();

        }else if(isAnyPropertyRegistered&& !isMealOffered){

            commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING_OFFERING_MEALS,Constants.POPUP_MESSAGE_NO_MEALS_OFFERED, MealsOldActivity.class);

        }else{
            commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
        }*/

    }

    public void onClickAddProperty(View view){

        preference.clearAddProperty();

        Intent intent=new Intent(getApplicationContext(),AddPropertyActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_WELCOME_SCREEN);
        startActivity(intent);
        finish();
    }

    public void onClickNotifications(View view){

        if(isAnyPropertyRegistered){

            Intent intent=new Intent(getApplicationContext(), NotificationsActivity.class);
            startActivity(intent);
            finish();

        }else{
            commonFunctionality.generateActivityRedirectPopupMessage(Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
        }

    }
}
