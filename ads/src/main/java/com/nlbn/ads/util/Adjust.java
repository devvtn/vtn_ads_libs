package com.nlbn.ads.util;

import android.app.Application;

import com.applovin.mediation.MaxAd;
import com.google.android.gms.ads.AdValue;

public abstract class Adjust {
    public static Adjust getInstance() {
        return AdjustImpl.getInstance();
    }

    public abstract void init(AdsApplication context, String appToken);

    public abstract void init(Application context, String appToken);

    public abstract void trackAdRevenue(AdValue adValue);

    public abstract void trackMaxAdRevenue(MaxAd maxAd);
}
