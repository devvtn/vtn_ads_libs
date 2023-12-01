package com.vtn.ads.util;

import android.app.Application;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.List;

public abstract class AdsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.BUILD_DEBUG = buildDebug();
        Log.i("Application", " run debug: " + AppUtil.BUILD_DEBUG);
        Admob.getInstance().initAdmob(this, getListTestDeviceId());

        initRemoteConfig(getDefaultsAsyncFirebase());
        if (enableRemoteAdsResume()) {
            if (enableAdsResume()) {
                AppOpenManager.getInstance().init(this, FirebaseRemoteConfig.getInstance().getString(getKeyRemoteAdsResume()));
            }
        } else {
            if (enableAdsResume()) {
                AppOpenManager.getInstance().init(this, getResumeAdId());
            }
        }

        if (enableAdjustTracking()) {
            Adjust.getInstance().init(this, getAdjustToken());
        }
    }

    public static void initRemoteConfig(int defaultAsync) {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(5)
            .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultAsync);
        mFirebaseRemoteConfig.fetchAndActivate();
    }

    public abstract boolean enableAdsResume();

    protected boolean enableRemoteAdsResume() {
        return false;
    }


    protected String getKeyRemoteAdsResume() {
        return "";
    }

    public abstract int getDefaultsAsyncFirebase();

    protected boolean enableAdjustTracking() {
        return false;
    }

    public abstract List<String> getListTestDeviceId();

    public abstract String getResumeAdId();

    protected String getAdjustToken() {
        return null;
    }

    public abstract Boolean buildDebug();
}
