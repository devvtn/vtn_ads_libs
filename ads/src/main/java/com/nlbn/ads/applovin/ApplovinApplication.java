package com.nlbn.ads.applovin;

import android.app.Application;

import com.nlbn.ads.util.Adjust;


public abstract class ApplovinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Adjust.getInstance().init(this, getAdjustToken());
        AppLovin.getInstance().init(this);
        if (enableAppResume())
            AppOpenManager.getInstance().init(this, getAppResumeId());
    }

    protected abstract String getAppResumeId();

    protected abstract boolean enableAppResume();

    protected abstract String getAdjustToken();
}
