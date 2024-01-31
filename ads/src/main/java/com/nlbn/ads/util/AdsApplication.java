package com.nlbn.ads.util;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.nlbn.ads.R;

import java.util.List;

public abstract class AdsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.BUILD_DEBUG = buildDebug();
        Log.i("Application", " run debug: " + AppUtil.BUILD_DEBUG);
        Admob.getInstance().initAdmob(this, getListTestDeviceId());

        if (enableAdsResume()) {
            AppOpenManager.getInstance().init(this);
        }
        initRemoteConfig(getDefaultsAsyncFirebase(), getMinimumFetch());

        if (enableAdjustTracking()) {
            Adjust.getInstance().init(this, getAdjustToken());
        }
    }

    private void initRemoteConfig(int defaultAsync, long minimumFetch) {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(minimumFetch)
            .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultAsync);
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    if (enableRemoteAdsResume()) {
                        if (enableAdsResume()) {
                            AppOpenManager.getInstance().setAppResumeAdId(FirebaseRemoteConfig.getInstance().getString(getKeyRemoteAdsResume()));
                        }
                    } else {
                        if (enableAdsResume()) {
                            AppOpenManager.getInstance().setAppResumeAdId(getResumeAdId());
                        }
                    }
                }
            }
        });
    }

    public abstract boolean enableAdsResume();

    protected boolean enableRemoteAdsResume() {
        return false;
    }


    protected String getKeyRemoteAdsResume() {
        return "";
    }

    protected long getMinimumFetch() {
        return 30L;
    }

    protected int getDefaultsAsyncFirebase() {
        return R.xml.remote_config_defaults;
    }

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
