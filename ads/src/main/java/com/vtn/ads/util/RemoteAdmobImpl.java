package com.vtn.ads.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.vtn.ads.adstype.AdSplashType;
import com.vtn.ads.callback.AdCallback;
import com.vtn.ads.callback.NativeCallback;
import com.vtn.ads.config.AdBannerConfig;
import com.vtn.ads.config.AdInterConfig;
import com.vtn.ads.config.AdNativeConfig;
import com.vtn.ads.config.AdRewardConfig;
import com.vtn.ads.config.AdSplashConfig;
import com.vtn.ads.model.AdmobUnit;

import java.util.ArrayList;
import java.util.List;

class RemoteAdmobImpl extends RemoteAdmob {

    public String TAG = "AdmobVTN";

    public static RemoteAdmobImpl INSTANCE;

    public static RemoteAdmobImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteAdmobImpl();
        }
        return INSTANCE;
    }

    public List<AdmobUnit> listIDAds;

    @Override
    public void initListID() {
        if (listIDAds == null) {
            listIDAds = new ArrayList<>();
        } else {
            listIDAds.clear();
        }
    }

    @Override
    public void addIdAds(String key, String id) {
        AdmobUnit admobUnit = new AdmobUnit();
        admobUnit.setKeyAds(key);
        admobUnit.setIdAds(id);
        if (listIDAds != null) {
            listIDAds.add(admobUnit);
        }
    }

    @Override
    public void addListIdAds(String key, List<String> listId) {
        AdmobUnit admobUnit = new AdmobUnit();
        admobUnit.setKeyAds(key);
        admobUnit.setListIdAds(listId);
        if (listIDAds != null) {
            listIDAds.add(admobUnit);
        }
    }

    @Override
    public String getIdAdsWithKey(String key) {
        String id = "";
        if (listIDAds != null && listIDAds.size() > 0) {
            for (int i = 0; i < listIDAds.size(); i++) {
                AdmobUnit admobUnit = listIDAds.get(i);
                if (key.equals(admobUnit.keyAds)) {
                    id = admobUnit.idAds;
                    break;
                }
            }
        }
        return id;
    }

    @Override
    public List<String> getListIdAdsWithKey(String key) {
        List<String> listID = new ArrayList<>();
        if (listIDAds != null && listIDAds.size() > 0) {
            for (int i = 0; i < listIDAds.size(); i++) {
                AdmobUnit admobUnit = listIDAds.get(i);
                if (key.equals(admobUnit.keyAds)) {
                    listID.addAll(admobUnit.getListIdAds());
                    break;
                }
            }
        }
        return listID;
    }

    @Override
    public int getIndexAd(String key) {
        int index = -1;
        if (listIDAds != null && listIDAds.size() > 0) {
            for (int i = 0; i < listIDAds.size(); i++) {
                AdmobUnit admobUnit = listIDAds.get(i);
                if (key.equals(admobUnit.keyAds)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    //splash
    @Override
    public void onCheckShowSplashWhenFailWithConfig(final AppCompatActivity activity, AdSplashConfig config, int timeDelay) {
        if (config != null) {
            if (config.adSplashType == AdSplashType.SPLASH_INTER || config.adSplashType == AdSplashType.SPLASH_INTER_FLOOR) {
                Admob.getInstance().onCheckShowSplashWhenFail(activity, config.callback, timeDelay);
            } else {
                AppOpenManager.getInstance().onCheckShowSplashWhenFail(activity, config.callback, timeDelay);
            }
        }
    }

    @Override
    public void loadAdSplashWithConfig(final Context context, AdSplashConfig config) {
        if (Helper.haveNetworkConnection(context)) {
            switch (config.adSplashType) {
                case SPLASH_INTER: {
                    Admob.getInstance().loadSplashInterAds2(context, getIdAdsWithKey(config.key), config.timeDelay, config.callback);
                    break;
                }
                case SPLASH_OPEN: {
                    AppOpenManagerImpl.getInstance().loadOpenAppAdSplash(context, getIdAdsWithKey(config.key), config.timeDelay, config.timeOut, config.isShowAdIfReady, config.callback);
                    break;
                }
                case SPLASH_INTER_FLOOR: {
                    Admob.getInstance().loadSplashInterAdsFloor(context, getListIdAdsWithKey(config.key), config.timeDelay, config.callback);
                    break;
                }
                case SPLASH_OPEN_FLOOR: {
                    AppOpenManagerImpl.getInstance().loadOpenAppAdSplashFloor(context, getListIdAdsWithKey(config.key), config.isShowAdIfReady, config.callback);
                    break;
                }
                default:
                    if (config.callback != null) {
                        config.callback.onAdClosed();
                        config.callback.onNextAction();
                    }
                    break;
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (config.callback != null) {
                        config.callback.onAdClosed();
                        config.callback.onNextAction();
                    }
                }
            }, config.timeDelay);
        }


    }

    @Override
    public void dismissLoadingDialog() {
        Admob.getInstance().dismissLoadingDialog();
    }

    //banner
    @Override
    public void loadBannerWithConfig(Activity activity, AdBannerConfig adBannerConfig) {
        if (Helper.haveNetworkConnection(activity)) {
            switch (adBannerConfig.bannerType) {
                case BANNER: {
                    Admob.getInstance().loadBanner(activity, getIdAdsWithKey(adBannerConfig.key), adBannerConfig.view);
                    break;
                }
                case BANNER_COLLAPSE: {
                    Admob.getInstance().loadCollapsibleBanner(activity, getIdAdsWithKey(adBannerConfig.key), adBannerConfig.gravity, adBannerConfig.view);
                    break;
                }
            }
        } else {
            if (adBannerConfig.view != null) {
                adBannerConfig.view.removeAllViews();
            }
        }
    }

    //native
    @Override
    public void loadNativeWithConfig(Context context, AdNativeConfig adNativeConfig, boolean isInvisible) {
        if (Helper.haveNetworkConnection(context)) {
            switch (adNativeConfig.adNativeType) {
                case NATIVE: {
                    Admob.getInstance().loadNativeAd(context, getIdAdsWithKey(adNativeConfig.key), new NativeCallback() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            if (nativeAd != null) {
                                if (adNativeConfig.view != null) {
                                    NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(adNativeConfig.layout, null);
                                    adNativeConfig.view.removeAllViews();
                                    adNativeConfig.view.addView(adView);
                                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);

                                }
                            } else {
                                if (adNativeConfig.view != null) {
                                    if (isInvisible) {
                                        adNativeConfig.view.setVisibility(View.INVISIBLE);
                                    } else {
                                        adNativeConfig.view.removeAllViews();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onAdFailedToLoad() {
                            if (adNativeConfig.view != null) {
                                if (isInvisible) {
                                    adNativeConfig.view.setVisibility(View.INVISIBLE);
                                } else {
                                    adNativeConfig.view.removeAllViews();
                                }
                            }
                        }
                    });
                    break;
                }
                case NATIVE_FLOOR: {
                    Admob.getInstance().loadNativeAdFloor(context, getListIdAdsWithKey(adNativeConfig.key), new NativeCallback() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            if (nativeAd != null) {
                                if (adNativeConfig.view != null) {
                                    NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(adNativeConfig.layout, null);
                                    adNativeConfig.view.removeAllViews();
                                    adNativeConfig.view.addView(adView);
                                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);

                                }
                            } else {
                                if (adNativeConfig.view != null) {
                                    if (isInvisible) {
                                        adNativeConfig.view.setVisibility(View.INVISIBLE);
                                    } else {
                                        adNativeConfig.view.removeAllViews();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onAdFailedToLoad() {
                            if (adNativeConfig.view != null) {
                                if (isInvisible) {
                                    adNativeConfig.view.setVisibility(View.INVISIBLE);
                                } else {
                                    adNativeConfig.view.removeAllViews();
                                }
                            }
                        }
                    });
                    break;
                }
            }
        } else {
            if (adNativeConfig.view != null) {
                if (isInvisible) {
                    adNativeConfig.view.setVisibility(View.INVISIBLE);
                } else {
                    adNativeConfig.view.removeAllViews();
                }
            }
        }
    }

    //Inter
    @Override
    public void loadInterWithKey(Context context, String key, boolean isOnCreate) {
        int index = getIndexAd(key);
        if (index != -1) {
            if (isOnCreate) {
                if (listIDAds.get(index).mInterstitialAd == null && Helper.haveNetworkConnection(context)) {
                    Admob.getInstance().loadInterAds(context, listIDAds.get(index).idAds, new AdCallback() {
                        @Override
                        public void onInterstitialLoad(InterstitialAd interstitialAd) {
                            super.onInterstitialLoad(interstitialAd);
                            listIDAds.get(index).setmInterstitialAd(interstitialAd);
                        }
                    });
                }
            } else {
                if (Helper.haveNetworkConnection(context)) {
                    Admob.getInstance().loadInterAds(context, listIDAds.get(index).idAds, new AdCallback() {
                        @Override
                        public void onInterstitialLoad(InterstitialAd interstitialAd) {
                            super.onInterstitialLoad(interstitialAd);
                            listIDAds.get(index).setmInterstitialAd(interstitialAd);
                        }
                    });
                } else {
                    listIDAds.get(index).setmInterstitialAd(null);
                }
            }
        }
    }

    @Override
    public void showInterWithConfig(Context context, AdInterConfig adInterConfig) {
        if (Helper.haveNetworkConnection(context)) {
            Admob.getInstance().showInterAds(context, listIDAds.get(getIndexAd(adInterConfig.key)).mInterstitialAd, adInterConfig.callback);
        } else {
            if (adInterConfig.callback != null) {
                adInterConfig.callback.onNextAction();
                adInterConfig.callback.onAdClosed();
            }
        }
    }

    //reward
    @Override
    public void initRewardWithConfig(Context context, AdRewardConfig adRewardConfig) {
        if (Helper.haveNetworkConnection(context)) {
            Admob.getInstance().initRewardAds(context, getIdAdsWithKey(adRewardConfig.key));
        }
    }

    @Override
    public void showRewardWithConfig(Activity context, AdRewardConfig adRewardConfig) {
        if (Helper.haveNetworkConnection(context)) {
            Admob.getInstance().showRewardAds(context, adRewardConfig.rewardCallback);
        } else {
            if (adRewardConfig.rewardCallback != null) {
                adRewardConfig.rewardCallback.onAdClosed();
            }
        }
    }

    //resume
    @Override
    public void disableAppResume() {
        AppOpenManagerImpl.getInstance().disableAppResume();
    }

    @Override
    public void enableAppResume() {
        AppOpenManagerImpl.getInstance().enableAppResume();
    }

    @Override
    public void disableAppResumeWithActivity(Class activityClass) {
        AppOpenManagerImpl.getInstance().disableAppResumeWithActivity(activityClass);
    }

    @Override
    public void enableAppResumeWithActivity(Class activityClass) {
        AppOpenManagerImpl.getInstance().enableAppResumeWithActivity(activityClass);
    }


}
