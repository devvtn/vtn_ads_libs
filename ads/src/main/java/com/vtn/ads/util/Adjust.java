package com.vtn.ads.util;

import com.google.android.gms.ads.AdValue;

public abstract class Adjust {
    public static Adjust getInstance() {
        return AdjustImpl.getInstance();
    }

    public abstract void init(AdsApplication context, String appToken);

    public abstract void trackAdRevenue(AdValue adValue);
}
