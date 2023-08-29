package com.vtnadslibrary;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.vtn.ads.util.RemoteAdmob;
import com.vtn.ads.util.AppFlyer;
import com.vtn.ads.util.AppOpenManager;
import com.vtn.ads.util.AdsApplication;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends AdsApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppOpenManager.getInstance().disableAppResumeWithActivity(Splash.class);
        initRemoteConfig();
        AppFlyer.getInstance().initAppFlyer(this, "", true);
        initIDADS();

    }

    @Override
    public boolean enableAdsResume() {
        return true;
    }

    @Override
    public boolean enableAdjustTracking() {
        return false;
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
    public String getAdjustToken() {
        return null;
    }

    @Override
    public Boolean buildDebug() {
        return true;
    }


    public static void initRemoteConfig() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetchAndActivate();
    }

    public void initIDADS(){
        RemoteAdmob.getInstance().initListID();
        RemoteAdmob.getInstance().insertIdAds(AdsConfig.key_ad_interstitial_id,getString(R.string.ad_interstitial_id));
        RemoteAdmob.getInstance().insertIdAds(AdsConfig.key_ad_banner_id,getString(R.string.ad_banner_id));
        RemoteAdmob.getInstance().insertIdAds(AdsConfig.key_ad_banner_id_collapse,getString(R.string.ad_banner_id_collapse));
        RemoteAdmob.getInstance().insertIdAds(AdsConfig.key_ad_native_id,getString(R.string.ad_native_id));
        RemoteAdmob.getInstance().insertIdAds(AdsConfig.key_ad_app_open_ad_id,getString(R.string.ad_app_open_ad_id));
        RemoteAdmob.getInstance().insertIdAds(AdsConfig.key_ad_app_reward_id,getString(R.string.ad_app_reward_id));


        List<String> listIDSplash = new ArrayList<>();
        listIDSplash.add(getString(R.string.ad_interstitial_id));
        listIDSplash.add(getString(R.string.ad_interstitial_id));
        RemoteAdmob.getInstance().insertListIdAds(AdsConfig.key_ad_splash_floor_id,listIDSplash);

        List<String> listIDSplashOpen = new ArrayList<>();
        listIDSplashOpen.add(getString(R.string.ad_app_open_ad_id));
        listIDSplashOpen.add(getString(R.string.ad_app_open_ad_id));
        RemoteAdmob.getInstance().insertListIdAds(AdsConfig.key_ad_open_floor_id,listIDSplashOpen);

        List<String> listIDNative = new ArrayList<>();
        listIDNative.add(getString(R.string.ad_native_id));
        listIDNative.add(getString(R.string.ad_native_id));
        RemoteAdmob.getInstance().insertListIdAds(AdsConfig.key_ad_native_floor_id,listIDNative);
    }


}
