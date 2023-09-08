package com.vtn.ads.util;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.vtn.ads.config.AdBannerConfig;
import com.vtn.ads.config.AdInterConfig;
import com.vtn.ads.config.AdNativeConfig;
import com.vtn.ads.config.AdRewardConfig;
import com.vtn.ads.config.AdSplashConfig;

import java.util.List;

public abstract class RemoteAdmob {
    public static RemoteAdmob getInstance() {
        return RemoteAdmobImpl.getInstance();
    }

    public abstract void initListID();

    public abstract void addIdAds(String key, String id);

    public abstract void addListIdAds(String key, List<String> listId);

    public abstract String getIdAdsWithKey(String key);

    public abstract List<String> getListIdAdsWithKey(String key);

    public abstract int getIndexAd(String key);

    public abstract void onCheckShowSplashWhenFailWithConfig(final AppCompatActivity activity, AdSplashConfig config, int timeDelay);

    public abstract void loadAdSplashWithConfig(final Context context, AdSplashConfig config);

    public abstract void dismissLoadingDialog();

    public abstract void loadBannerWithConfig(Activity activity, AdBannerConfig adBannerConfig);

    public abstract void loadNativeWithConfig(Context context, AdNativeConfig adNativeConfig, boolean isInvisible);

    public abstract void loadInterWithKey(Context context, String key, boolean isOnCreate);

    public abstract void showInterWithConfig(Context context, AdInterConfig adInterConfig);

    public abstract void initRewardWithConfig(Context context, AdRewardConfig adRewardConfig);

    public abstract void showRewardWithConfig(Activity context, AdRewardConfig adRewardConfig);

    public abstract void disableAppResume();

    public abstract void enableAppResume();

    public abstract void disableAppResumeWithActivity(Class activityClass);

    public abstract void enableAppResumeWithActivity(Class activityClass);
}
