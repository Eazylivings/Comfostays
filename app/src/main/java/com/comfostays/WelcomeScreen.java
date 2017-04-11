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
import com.comfostays.activities.MealActivity;
import com.comfostays.activities.customer_activities.JoinPropertyActivity;
import com.comfostays.activities.customer_activities.events.UpcomingOrPastEventsCustomerActivity;
import com.comfostays.activities.owner_activities.notifications.OwnerNotificationsActivity;
import com.comfostays.activities.owner_activities.occupancy.OccupancyActivity;
import com.comfostays.activities.owner_activities.tenant_activities.TenantsActivity;
import com.comfostays.activities.owner_activities.building_setup.AddPropertyActivity;
import com.comfostays.activities.loginactivities.LoginActivity;
import com.comfostays.activities.owner_activities.MyPropertiesActivity;
import com.comfostays.activities.TutorialActivity;
import com.comfostays.sharedpreference.SharedPreference;

public class WelcomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreference preference;

    boolean isAnyPropertyRegistered;
    boolean isMealOffered=true;
    boolean isCustomerPartOfAnyProperty;

    String loggedUserEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome_screen);

            preference = new SharedPreference(getApplicationContext());

            String userName = preference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_USERNAME);
            loggedUserEmailAddress = preference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_EMAIL_ADDRESS);
            isCustomerPartOfAnyProperty=preference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_CUSTOMER_PART_OF_ANY_PROPERTY);

            setTitle(Constants.WELCOME + userName);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            String accountType=preference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_ACCOUNT_TYPE);

            if(accountType.equalsIgnoreCase(Constants.OWNER)){

                setScreenForOwners();
            }else{

                setScreenForCustomers();
            }

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

                drawer.addDrawerListener(toggle);
                toggle.syncState();
            }

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            if (navigationView != null) {

                navigationView.setNavigationItemSelectedListener(this);

                View header = navigationView.getHeaderView(0);

                TextView drawerUserName = (TextView) header.findViewById(R.id.welcomeScreen_drawer_textViewView_userName);
                TextView drawerEmailAddress = (TextView) header.findViewById(R.id.welcomeScreen_drawer_textViewView_userEmailAddress);
                drawerUserName.setText(userName);
                drawerEmailAddress.setText(loggedUserEmailAddress);

            }
        }catch(Exception e){

            CommonFunctionality.generatePopUpMessageForExceptions(this);
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
                CommonFunctionality.generateActivityRedirectPopupMessage(this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
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
                CommonFunctionality.generateActivityRedirectPopupMessage(this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
            }

        }else if (id == R.id.owner_meals) {

            if(isAnyPropertyRegistered){

                Intent intent=new Intent(getApplicationContext(), MealActivity.class);
                startActivity(intent);
                finish();

            }else{
                CommonFunctionality.generateActivityRedirectPopupMessage(this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
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

        } else if (id == R.id.customer_settle_yourself) {

            Intent intent=new Intent(getApplicationContext(),JoinPropertyActivity.class);
            intent.putExtra(Constants.INTENT_IS_ACTIVITY_LOADED_FOR_FIRST_TIME,true);
            intent.putExtra(Constants.INTENT_IS_ACTIVITY_LOADED_FOR_FIRST_TIME,true);
            startActivity(intent);
            finish();

        }else if (id == R.id.customer_unsettle_yourself) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer!=null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void onClickAddProperty(View view){

        preference.clearAddProperty();

        Intent intent=new Intent(getApplicationContext(),AddPropertyActivity.class);
        intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_WELCOME_SCREEN);
        startActivity(intent);
        finish();
    }

    private void setScreenForCustomers(){

        ImageView view1=(ImageView)findViewById(R.id.welcomeScreen_imageView_0_0) ;

        String currentStayType=preference.getStringValueFromSharedPreference(Constants.SHARED_PREFERENCE_PROPERTY_TYPE);

        if(view1!=null){

            view1.setImageResource(R.drawable.current_stay_icon);

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isAnyPropertyRegistered){

                        Intent intent=new Intent(getApplicationContext(),MyPropertiesActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        CommonFunctionality.generateActivityRedirectPopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
                    }
                }
            });
        }

        ImageView view2=(ImageView)findViewById(R.id.welcomeScreen_imageView_0_1);

        if(view2!=null){

            view2.setImageResource(R.drawable.events_icon);

            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //if(isAnyPropertyRegistered){

                        Intent intent=new Intent(getApplicationContext(),UpcomingOrPastEventsCustomerActivity.class);
                        startActivity(intent);
                        finish();

                    //}else{

                       // CommonFunctionality.generateActivityRedirectPopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
                    //}
                }
            });
        }

        ImageView view3=(ImageView)findViewById(R.id.welcomeScreen_imageView_0_2) ;

        if(view3!=null){

            if(currentStayType.equalsIgnoreCase(Constants.PGs)){

                view3.setImageResource(R.drawable.coming_birthdays_icon);
            }else{

                view3.setImageResource(R.drawable.my_property_new);
            }
        }

        ImageView view4=(ImageView)findViewById(R.id.welcomeScreen_imageView_1_0) ;

        if(view4!=null){

            if(currentStayType.equalsIgnoreCase(Constants.PGs)){

                view4.setImageResource(R.drawable.group_chat_icon);

                view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CommonFunctionality.generatePopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_BUY_PAID_VERSION);
                    }
                });

            }else{

                view4.setImageResource(R.drawable.group_chat_icon);

                view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CommonFunctionality.generatePopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_BUY_PAID_VERSION);
                    }
                });
            }
        }

        ImageView view5=(ImageView)findViewById(R.id.welcomeScreen_imageView_1_1) ;

        if(view5!=null){

            view5.setImageResource(R.drawable.past_stays_icon);
        }

        ImageView view6=(ImageView)findViewById(R.id.welcomeScreen_imageView_1_2) ;

        if(view6!=null){

            view6.setImageResource(R.drawable.notifications_new);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {

            Menu menu = navigationView.getMenu();

            if(menu!=null){

                if(isCustomerPartOfAnyProperty){

                    menu.removeItem(R.id.customer_settle_yourself);
                }else{

                    menu.removeItem(R.id.customer_unsettle_yourself);
                }

                menu.removeItem(R.id.owner_my_properties);
                menu.removeItem(R.id.owner_add_properties);
                menu.removeItem(R.id.owner_occupancy);

                if (!isMealOffered) {
                    menu.removeItem(R.id.owner_meals);
                }
            }
        }
    }

    private void setScreenForOwners(){

        ImageView view1=(ImageView)findViewById(R.id.welcomeScreen_imageView_0_0) ;

        if(view1!=null){

            view1.setImageResource(R.drawable.my_property_new);

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isAnyPropertyRegistered){

                        Intent intent=new Intent(getApplicationContext(),MyPropertiesActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        CommonFunctionality.generateActivityRedirectPopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
                    }
                }
            });
        }

        ImageView view2=(ImageView)findViewById(R.id.welcomeScreen_imageView_0_1);

        if(view2!=null){

            view2.setImageResource(R.drawable.occupancy_new);

            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isAnyPropertyRegistered){

                        preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_OCCUPANCY_LOADED_FOR_FIRST_TIME,true);
                        preference.setBooleanValueInSharedPreference(Constants.SHARED_PREFERENCE_IS_CURRENT_OCCUPANCY_CHECKED_BOX_CHECKED,false);

                        Intent intent=new Intent(getApplicationContext(), OccupancyActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        CommonFunctionality.generateActivityRedirectPopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
                    }
                }
            });
        }

        ImageView view3=(ImageView)findViewById(R.id.welcomeScreen_imageView_0_2) ;

        if(view3!=null){

            view3.setImageResource(R.drawable.tenants_new);

            view3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isAnyPropertyRegistered){

                        Intent intent=new Intent(getApplicationContext(),TenantsActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        CommonFunctionality.generateActivityRedirectPopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
                    }
                }
            });
        }

        ImageView view4=(ImageView)findViewById(R.id.welcomeScreen_imageView_1_0) ;

        if(view4!=null){

            view4.setImageResource(R.drawable.my_finances);

            view4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CommonFunctionality.generatePopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.POPUP_MESSAGE_BUY_PAID_VERSION);

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
            });
        }

        ImageView view5=(ImageView)findViewById(R.id.welcomeScreen_imageView_1_1) ;

        if(view5!=null){

            view5.setImageResource(R.drawable.add_property_new);

            view5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    preference.clearAddProperty();

                    Intent intent=new Intent(getApplicationContext(),AddPropertyActivity.class);
                    intent.putExtra(Constants.INTENT_PREVIOUS_ACTIVITY,Constants.ACTIVITY_WELCOME_SCREEN);
                    startActivity(intent);
                    finish();
                }
            });
        }

        ImageView view6=(ImageView)findViewById(R.id.welcomeScreen_imageView_1_2) ;

        if(view6!=null){

            view6.setImageResource(R.drawable.notifications_new);

            view6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isAnyPropertyRegistered){

                        Intent intent=new Intent(getApplicationContext(), OwnerNotificationsActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        CommonFunctionality.generateActivityRedirectPopupMessage(WelcomeScreen.this,Constants.ALERT_MESSAGE,Constants.ALERT_BOX_POSITIVE_HEADING,Constants.POPUP_MESSAGE_NO_PROPERTY_FOUND, AddPropertyActivity.class);
                    }
                }
            });
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {

            Menu menu = navigationView.getMenu();

            if(menu!=null){

                if (!isMealOffered) {
                    menu.removeItem(R.id.owner_meals);
                }

                menu.removeItem(R.id.customer_settle_yourself);
                menu.removeItem(R.id.customer_unsettle_yourself);
            }
        }

        isAnyPropertyRegistered = preference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_ANY_PROPERTY_REGISTERED);

        if (!isAnyPropertyRegistered) {

            if (view1 != null && view2 != null && view3 != null && view4 != null && view5 != null && view6 != null) {
                view1.setColorFilter(Color.argb(150, 200, 200, 200));
                view1.setColorFilter(Color.argb(150, 200, 200, 200));
                view1.setColorFilter(Color.argb(150, 200, 200, 200));
                view1.setColorFilter(Color.argb(150, 200, 200, 200));
                view1.setColorFilter(Color.argb(150, 200, 200, 200));
                view1.setColorFilter(Color.argb(150, 200, 200, 200));
            }
        }
    }
}
