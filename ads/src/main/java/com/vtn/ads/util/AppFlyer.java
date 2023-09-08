package com.vtn.ads.util;

import android.app.Application;

import com.google.android.gms.ads.AdValue;

public abstract class AppFlyer {
    public static AppFlyer getInstance() {
        return AppFlyerImpl.getInstance();
    }

    public abstract void initAppFlyer(Application context, String devKey, boolean enableTrackingAppFlyerRevenue);

    public abstract void initAppFlyer(Application context, String devKey, boolean enableTrackingAppFlyerRevenue, boolean enableDebug);

    public abstract void initAppFlyerDebug(Application context, String devKey, boolean enableDebugLog);

    public abstract void pushTrackEventAdmob(AdValue adValue, String adId, String adType);
}
