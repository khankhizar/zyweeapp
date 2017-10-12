package com.infovita.zywee.Supports;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import io.branch.referral.Branch;

/**
 * Created by Khizarkhan on 21-04-2016.
 */
public class ZyweeApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Branch.getAutoInstance(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);



    }
}
