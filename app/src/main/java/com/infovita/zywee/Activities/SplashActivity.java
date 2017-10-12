package com.infovita.zywee.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.infovita.zywee.R;

import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import com.infovita.zywee.Sharedvalues.Serverdatas;

public class SplashActivity extends Activity {
    Serverdatas sd = Serverdatas.getSingletonObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

         new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // close this activity
                finish();
                SharedPreferences prefs = getSharedPreferences(sd.LOGIN_FLAG, MODE_PRIVATE);
                Integer islogin = prefs.getInt("LOGIN_FLAG", 0);
                if(islogin == 0){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else if(islogin == 1){
                    Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
                    startActivity(intent);
                }else{
                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);
    }

    @Override
    public void onStart() {
        super.onStart();

        Branch branch = Branch.getInstance(this);
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked before showing up
                    Log.i("BranchConfigTest", "deep link data: " + referringParams.toString());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
