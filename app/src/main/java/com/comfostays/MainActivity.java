package com.comfostays;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.comfostays.activities.loginactivities.LoginActivity;
import com.comfostays.databasehandler.OwnerLocalDatabaseHandler;
import com.comfostays.sharedpreference.SharedPreference;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image=(ImageView)findViewById(R.id.startingScreen_fadinLogo);

        final SharedPreference sharedPreference=new SharedPreference(this);

        final boolean isUserLoggedIn=sharedPreference.getBooleanValueFromSharedPreference(Constants.SHARED_PREFERENCE_IS_USER_LOGGED_IN);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(image, "alpha", 0f, 1f);
        fadeIn.setDuration(3000);

        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    final Intent mainIntent;
                    if(!isUserLoggedIn) {
                        mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    }else{
                        mainIntent = new Intent(MainActivity.this, WelcomeScreen.class);
                    }
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
            });
            mAnimationSet.start();
        }
}
