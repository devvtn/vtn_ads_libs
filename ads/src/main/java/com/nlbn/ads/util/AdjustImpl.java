package com.nlbn.ads.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adjust.sdk.AdjustAdRevenue;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.BuildConfig;
import com.adjust.sdk.LogLevel;
import com.applovin.mediation.MaxAd;
import com.google.android.gms.ads.AdValue;

class AdjustImpl extends Adjust implements Application.ActivityLifecycleCallbacks {
    private static AdjustImpl INSTANCE;
    AdsApplication adsApplication;

    public static AdjustImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdjustImpl();
        }
        return INSTANCE;
    }

    @Override
    public void init(AdsApplication context, String appToken) {
        this.adsApplication = context;
        String environment = BuildConfig.DEBUG ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(context, appToken, environment);
        if (BuildConfig.DEBUG) {
            config.setLogLevel(LogLevel.VERBOSE);
        }
        com.adjust.sdk.Adjust.onCreate(config);
        context.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void init(Context context, String appToken) {
        String environment = BuildConfig.DEBUG ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(context, appToken, environment);
        if (BuildConfig.DEBUG) {
            config.setLogLevel(LogLevel.VERBOSE);
        }
        com.adjust.sdk.Adjust.onCreate(config);
        ((Application) context).registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void trackAdRevenue(AdValue adValue) {
        if (adsApplication != null && adsApplication.enableAdjustTracking()) {
            AdjustAdRevenue revenue = new AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB);
            revenue.setRevenue((double) adValue.getValueMicros() / 1000000, adValue.getCurrencyCode());
            com.adjust.sdk.Adjust.trackAdRevenue(revenue);
        }

    }

    @Override
    public void trackMaxAdRevenue(MaxAd maxAd) {
        AdjustAdRevenue revenue = new AdjustAdRevenue(AdjustConfig.AD_REVENUE_APPLOVIN_MAX);
        revenue.setRevenue(maxAd.getRevenue(), "USD");
        revenue.setAdRevenueNetwork(maxAd.getNetworkName());
        revenue.setAdRevenueUnit(maxAd.getAdUnitId());
        com.adjust.sdk.Adjust.trackAdRevenue(revenue);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        com.adjust.sdk.Adjust.onResume();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        com.adjust.sdk.Adjust.onPause();
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
