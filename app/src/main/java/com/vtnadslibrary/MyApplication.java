package com.vtnadslibrary;

import com.nlbn.ads.util.AdsApplication;
import com.nlbn.ads.util.AppOpenManager;

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
        return AdsConfig.KEY_AD_APP_RESUME_ID;
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
