package com.comfostays.activities.customer_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.comfostays.R;

public class JoinPropertyActivity extends AppCompatActivity {

    boolean isActivityLoadedForFirstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_property);
    }
}
