package com.sourtime.www.handyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sourtime.www.handyapp.R;
import com.sourtime.www.handyapp.fragments.SplashFragment;

/**
 * Created by user on 06/12/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private final Handler mHandler = new Handler();

    private final Runnable mGoToLoginScreen = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            SplashActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        SplashFragment fragment = new SplashFragment();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();

        mHandler.postDelayed(mGoToLoginScreen,2000);
    }
}
