package com.nlbn.ads.applovin;

import android.app.Application;

public abstract class ApplovinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLovin.getInstance().init(this);
        if (enableAppResume())
            AppOpenManager.getInstance().init(this, getAppResumeId());
    }

    protected abstract String getAppResumeId();

    protected abstract boolean enableAppResume();
}
