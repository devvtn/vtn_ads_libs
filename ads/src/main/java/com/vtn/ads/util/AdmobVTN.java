package com.vtn.ads.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
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
import com.vtn.ads.model.IDADS;

import java.util.ArrayList;
import java.util.List;

public class AdmobVTN {

    public String TAG ="AdmobVTN";

    public static AdmobVTN INSTANCE;

    public static AdmobVTN getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdmobVTN();
        }
        return INSTANCE;
    }

    public List<IDADS> listIDAds;

    public void initListID() {
        if (listIDAds == null) {
            listIDAds = new ArrayList<>();
        } else {
            listIDAds.clear();
        }
    }

    public void insertIdAds(String key, String id) {
        IDADS idads = new IDADS();
        idads.setKeyAds(key);
        idads.setIdAds(id);
        if (listIDAds != null) {
            listIDAds.add(idads);
        }
    }

    public void insertListIdAds(String key, List<String> listId) {
        IDADS idads = new IDADS();
        idads.setKeyAds(key);
        idads.setListIdAds(listId);
        if (listIDAds != null) {
            listIDAds.add(idads);
        }
    }

    public String getIdAdsWithKey(String key) {
        String id = "";
        if (listIDAds != null && listIDAds.size() > 0) {
            for (int i = 0; i < listIDAds.size(); i++) {
                IDADS idads = listIDAds.get(i);
                if (key.equals(idads.keyAds)) {
                    id = idads.idAds;
                    break;
                }
            }
        }
        return id;
    }

    public List<String> getListIdAdsWithKey(String key) {
        List<String> listID = new ArrayList<>();
        if (listIDAds != null && listIDAds.size() > 0) {
            for (int i = 0; i < listIDAds.size(); i++) {
                IDADS idads = listIDAds.get(i);
                if (key.equals(idads.keyAds)) {
                    listID.addAll(idads.getListIdAds());
                    break;
                }
            }
        }
        return listID;
    }

    public int getIndexAd(String key){
        int index = -1;
        if (listIDAds != null && listIDAds.size() > 0) {
            for (int i = 0; i < listIDAds.size(); i++) {
                IDADS idads = listIDAds.get(i);
                if (key.equals(idads.keyAds)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    //splash
    public void onCheckShowSplashWhenFailWithConfig(final AppCompatActivity activity, AdSplashConfig config, long timeDelay) {
        if (config != null) {
            if (config.adSplashType == AdSplashType.SPLASH_INTER || config.adSplashType == AdSplashType.SPLASH_INTER_FLOOR) {
                Admob.getInstance().onCheckShowSplashWhenFail(activity, config.callback, timeDelay);
            } else {
                AppOpenManager.getInstance().onCheckShowSplashWhenFail(activity, config.callback, timeDelay);
            }
        }
    }

    public void loadAdSplashWithConfig(final Context context, AdSplashConfig config) {
        if (Helper.haveNetworkConnection(context)) {
            switch (config.adSplashType) {
                case SPLASH_INTER: {
                    Admob.getInstance().loadSplashInterAds2(context, getIdAdsWithKey(config.key), config.timeDelay, config.callback);
                    break;
                }
                case SPLASH_OPEN: {
                    AppOpenManager.getInstance().loadOpenAppAdSplash(context, getIdAdsWithKey(config.key), config.timeDelay, config.timeOut, config.isShowAdIfReady, config.callback);
                    break;
                }
                case SPLASH_INTER_FLOOR: {
                    Admob.getInstance().loadSplashInterAdsFloor(context, getListIdAdsWithKey(config.key), config.timeDelay, config.callback);
                    break;
                }
                case SPLASH_OPEN_FLOOR: {
                    AppOpenManager.getInstance().loadOpenAppAdSplashFloor(context, getListIdAdsWithKey(config.key), config.isShowAdIfReady, config.callback);
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


    public void dismissLoadingDialog() {
        Admob.getInstance().dismissLoadingDialog();
    }

    //banner
    public void loadBannerWithConfig(Activity activity,AdBannerConfig adBannerConfig){
        if (Helper.haveNetworkConnection(activity)){
            switch (adBannerConfig.bannerType){
                case BANNER:{
                    Admob.getInstance().loadBanner(activity,getIdAdsWithKey(adBannerConfig.key),adBannerConfig.view);
                    break;
                }
                case BANNER_COLLAPSE:{
                    Admob.getInstance().loadCollapsibleBanner(activity,getIdAdsWithKey(adBannerConfig.key),adBannerConfig.gravity,adBannerConfig.view);
                    break;
                }
            }
        }else {
            if (adBannerConfig.view!=null){
                adBannerConfig.view.removeAllViews();
            }
        }
    }
    //native
    public void loadNativeWithConfig(Context context , AdNativeConfig adNativeConfig , boolean isInvisible){
        if (Helper.haveNetworkConnection(context)){
            switch (adNativeConfig.adNativeType){
                case NATIVE:{
                    Admob.getInstance().loadNativeAd(context,getIdAdsWithKey(adNativeConfig.key),new NativeCallback(){
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            if (nativeAd!=null) {
                                if (adNativeConfig.view != null) {
                                    NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(adNativeConfig.layout, null);
                                    adNativeConfig.view.removeAllViews();
                                    adNativeConfig.view.addView(adView);
                                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);

                                }
                            }else {
                                if (adNativeConfig.view !=null){
                                    if (isInvisible) {
                                        adNativeConfig.view.setVisibility(View.INVISIBLE);
                                    }else {
                                        adNativeConfig.view.removeAllViews();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onAdFailedToLoad() {
                            if (adNativeConfig.view !=null){
                                if (isInvisible) {
                                    adNativeConfig.view.setVisibility(View.INVISIBLE);
                                }else {
                                    adNativeConfig.view.removeAllViews();
                                }
                            }
                        }
                    });
                    break;
                }
                case NATIVE_FLOOR:{
                    Admob.getInstance().loadNativeAdFloor(context,getListIdAdsWithKey(adNativeConfig.key),new NativeCallback(){
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            if (nativeAd!=null) {
                                if (adNativeConfig.view != null) {
                                    NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(adNativeConfig.layout, null);
                                    adNativeConfig.view.removeAllViews();
                                    adNativeConfig.view.addView(adView);
                                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);

                                }
                            }else {
                                if (adNativeConfig.view !=null){
                                    if (isInvisible) {
                                        adNativeConfig.view.setVisibility(View.INVISIBLE);
                                    }else {
                                        adNativeConfig.view.removeAllViews();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onAdFailedToLoad() {
                            if (adNativeConfig.view !=null){
                                if (isInvisible) {
                                    adNativeConfig.view.setVisibility(View.INVISIBLE);
                                }else {
                                    adNativeConfig.view.removeAllViews();
                                }
                            }
                        }
                    });
                    break;
                }
            }
        }else {
            if (adNativeConfig.view !=null){
                if (isInvisible) {
                    adNativeConfig.view.setVisibility(View.INVISIBLE);
                }else {
                    adNativeConfig.view.removeAllViews();
                }
            }
        }
    }

    //Inter
    public void loadInterWithKey(Context context , String key , boolean isOnCreate){
        int index = getIndexAd(key);
        if (index!=-1) {
            if (isOnCreate) {
                if (listIDAds.get(index).mInterstitialAd==null && Helper.haveNetworkConnection(context)){
                    Admob.getInstance().loadInterAds(context, listIDAds.get(index).idAds, new AdCallback() {
                        @Override
                        public void onInterstitialLoad(InterstitialAd interstitialAd) {
                            super.onInterstitialLoad(interstitialAd);
                            listIDAds.get(index).setmInterstitialAd(interstitialAd);
                        }
                    });
                }
            }else {
                if (Helper.haveNetworkConnection(context)){
                    Admob.getInstance().loadInterAds(context, listIDAds.get(index).idAds, new AdCallback() {
                        @Override
                        public void onInterstitialLoad(InterstitialAd interstitialAd) {
                            super.onInterstitialLoad(interstitialAd);
                            listIDAds.get(index).setmInterstitialAd(interstitialAd);
                        }
                    });
                }else {
                    listIDAds.get(index).setmInterstitialAd(null);
                }
            }
        }
    }

    public void showInterWithConfig(Context context, AdInterConfig adInterConfig){
        if (Helper.haveNetworkConnection(context)){
            Admob.getInstance().showInterAds(context,listIDAds.get(getIndexAd(adInterConfig.key)).mInterstitialAd,adInterConfig.callback);
        }else {
            if (adInterConfig.callback!=null) {
                adInterConfig.callback.onNextAction();
                adInterConfig.callback.onAdClosed();
            }
        }
    }

    //reward
    public void initRewardWithConfig(Context context , AdRewardConfig adRewardConfig){
        if (Helper.haveNetworkConnection(context)) {
            Admob.getInstance().initRewardAds(context, getIdAdsWithKey(adRewardConfig.key));
        }
    }

    public void showRewardWithConfig(Activity context, AdRewardConfig adRewardConfig){
        if (Helper.haveNetworkConnection(context)){
            Admob.getInstance().showRewardAds(context,adRewardConfig.rewardCallback);
        }else {
            if (adRewardConfig.rewardCallback!=null){
                adRewardConfig.rewardCallback.onAdClosed();
            }
        }
    }

    //resume
    public void disableAppResume(){
        AppOpenManager.getInstance().disableAppResume();
    }

    public void enableAppResume(){
        AppOpenManager.getInstance().enableAppResume();
    }

    public void disableAppResumeWithActivity(Class activityClass){
        AppOpenManager.getInstance().disableAppResumeWithActivity(activityClass);
    }
    public void enableAppResumeWithActivity(Class activityClass){
        AppOpenManager.getInstance().enableAppResumeWithActivity(activityClass);
    }




}
