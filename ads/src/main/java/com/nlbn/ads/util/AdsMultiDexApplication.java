package com.nlbn.ads.util;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import java.util.List;

public abstract class AdsMultiDexApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.BUILD_DEBUG = buildDebug();
        Log.i("Application", " run debug: " + AppUtil.BUILD_DEBUG);
        Admob.getInstance().initAdmob(this, getListTestDeviceId());
        if(enableAdsResume()) {
            AppOpenManager.getInstance().init(this);
            AppOpenManager.getInstance().setAppResumeAdId(getOpenAppAdId());
        }
    }

    public abstract boolean enableAdsResume();

    public abstract List<String> getListTestDeviceId();

    public abstract String getOpenAppAdId();
    public abstract Boolean buildDebug();
}
