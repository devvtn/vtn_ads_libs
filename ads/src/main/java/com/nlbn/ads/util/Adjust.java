package com.nlbn.ads.util;

import android.app.Application;
import android.content.Context;

import com.applovin.mediation.MaxAd;
import com.google.android.gms.ads.AdValue;

public abstract class Adjust {
    public static Adjust getInstance() {
        return AdjustImpl.getInstance();
    }

    public abstract void init(AdsApplication context, String appToken);

    public abstract void init(Context context, String appToken);

    public abstract void trackAdRevenue(AdValue adValue);

    public abstract void trackMaxAdRevenue(MaxAd maxAd);
}
