package com.nlbn.ads.util;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.adrevenue.AppsFlyerAdRevenue;
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork;
import com.appsflyer.adrevenue.adnetworks.generic.Scheme;
import com.google.android.gms.ads.AdValue;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class AppFlyerImpl extends AppFlyer {
    private static AppFlyerImpl INSTANCE;
    private boolean enableTrackingAppFlyerRevenue = false;
    private static final String TAG = "AppFlyer";

    public static AppFlyerImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppFlyerImpl();
        }
        return INSTANCE;
    }

    @Override
    public void initAppFlyer(Application context, String devKey, boolean enableTrackingAppFlyerRevenue) {
        this.enableTrackingAppFlyerRevenue = enableTrackingAppFlyerRevenue;
        initAppFlyerDebug(context, devKey, false);
    }

    @Override
    public void initAppFlyer(Application context, String devKey, boolean enableTrackingAppFlyerRevenue, boolean enableDebug) {
        this.enableTrackingAppFlyerRevenue = enableTrackingAppFlyerRevenue;
        initAppFlyerDebug(context, devKey, enableDebug);
    }

    @Override
    public void initAppFlyerDebug(Application context, String devKey, boolean enableDebugLog) {
        AppsFlyerLib.getInstance().init(devKey, null, context);
        AppsFlyerLib.getInstance().start(context);

        AppsFlyerAdRevenue.Builder afRevenueBuilder = new AppsFlyerAdRevenue.Builder(context);
        AppsFlyerAdRevenue.initialize(afRevenueBuilder.build());
        AppsFlyerLib.getInstance().setDebugLog(enableDebugLog);
    }

    @Override
    public void pushTrackEventAdmob(AdValue adValue, String adId, String adType) {
        Log.e(TAG, "Log tracking event AppFlyer: enableAppFlyer:" + this.enableTrackingAppFlyerRevenue + " --- AdType: " + adType + " --- value: " + adValue.getValueMicros() / 1000000);
        if (enableTrackingAppFlyerRevenue) {
            Map<String, String> customParams = new HashMap<>();
            customParams.put(Scheme.AD_UNIT, adId);
            customParams.put(Scheme.AD_TYPE, adType);
            AppsFlyerAdRevenue.logAdRevenue(
                    "Admob",
                    MediationNetwork.googleadmob,
                    Currency.getInstance(Locale.US),
                    (double) adValue.getValueMicros() / 1000000.0,
                    customParams
            );
        }
    }
}
