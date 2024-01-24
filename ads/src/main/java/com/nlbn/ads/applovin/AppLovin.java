package com.nlbn.ads.applovin;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.nlbn.ads.callback.AdCallback;

public abstract class AppLovin {
    public static AppLovin getInstance() {
        return AppLovinImpl.getInstance();
    }

    public abstract void init(Context context);

    public abstract void setOpenActivityAfterShowInterAds(boolean openActivityAfterShowInterAds);

    public abstract void loadBanner(Context context, String adsId, ViewGroup container);

    public abstract void loadAndShowInter(Activity activity, String adsId, AdCallback adCallback);

    public abstract void loadNativeWithDefaultTemplate(Context context, String adsId, ViewGroup container);

    public abstract void loadNativeWithCustomLayout(Context context, String adsId, int layout, ViewGroup container);
}
