package com.vtnadslibrary;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.vtn.ads.util.AdsApplication;
import com.vtn.ads.util.AppFlyer;
import com.vtn.ads.util.AppOpenManager;
import com.vtn.ads.util.RemoteAdmob;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends AdsApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppOpenManager.getInstance().disableAppResumeWithActivity(Splash.class);

//        AppFlyer.getInstance().initAppFlyer(this, "", true);

    }

    @Override
    public boolean enableAdsResume() {
        return true;
    }

    @Override
    public boolean enableRemoteAdsResume() {
        return true;
    }

    @Override
    public String getKeyRemoteAdsResume() {
        return AdsConfig.key_ad_app_resume_id;
    }

    @Override
    public int getDefaultsAsyncFirebase() {
        return R.xml.remote_config_defaults;
    }

    @Override
    public List<String> getListTestDeviceId() {
        return null;
    }

    @Override
    public String getResumeAdId() {
        return getString(R.string.ad_app_open_resume_ad_id);
    }

    @Override
    public Boolean buildDebug() {
        return true;
    }





}
