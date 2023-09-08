package com.vtnadslibrary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vtn.ads.adstype.AdNativeType;
import com.vtn.ads.config.AdBannerConfig;
import com.vtn.ads.adstype.AdBannerType;
import com.vtn.ads.config.AdNativeConfig;
import com.vtn.ads.util.BannerGravity;
import com.vtn.ads.util.RemoteAdmob;

public class BannerActivity extends AppCompatActivity {

    AdBannerConfig adBannerConfig;
    AdNativeConfig adNativeConfig;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        loadBanner();
       // loadBannerCollapseTop();
       // loadBannerCollapseBot();
        //loadNative();
        loadNativeFloor();


    }

    public void loadBanner(){
        adBannerConfig = new AdBannerConfig.Builder()
                .setKey(AdsConfig.key_ad_banner_id)
                .setBannerType(AdBannerType.BANNER)
                .setView(findViewById(R.id.bannerBot))
                .setGravity(BannerGravity.top)
                .build();
        RemoteAdmob.getInstance().loadBannerWithConfig(this,adBannerConfig);
    }

    public void loadBannerCollapseTop(){
        adBannerConfig = new AdBannerConfig.Builder()
                .setKey(AdsConfig.key_ad_banner_id_collapse)
                .setBannerType(AdBannerType.BANNER_COLLAPSE)
                .setGravity(BannerGravity.top)
                .setView(findViewById(R.id.bannerTop))
                .build();
        RemoteAdmob.getInstance().loadBannerWithConfig(this,adBannerConfig);
    }

    public void loadBannerCollapseBot(){
        adBannerConfig = new AdBannerConfig.Builder()
                .setKey(AdsConfig.key_ad_banner_id_collapse)
                .setBannerType(AdBannerType.BANNER_COLLAPSE)
                .setGravity(BannerGravity.bottom)
                .setView(findViewById(R.id.bannerBot))
                .build();
        RemoteAdmob.getInstance().loadBannerWithConfig(this,adBannerConfig);
    }

    public void loadNative(){
        adNativeConfig = new AdNativeConfig.Builder()
                .setKey(AdsConfig.key_ad_native_id)
                .setNativeType(AdNativeType.NATIVE)
                .setLayout(R.layout.layout_native_custom)
                .setView(findViewById(R.id.native_ads))
                .build();
        RemoteAdmob.getInstance().loadNativeWithConfig(this,adNativeConfig,false);
    }

    public void loadNativeFloor(){
        adNativeConfig = new AdNativeConfig.Builder()
                .setKey(AdsConfig.key_ad_native_floor_id)
                .setNativeType(AdNativeType.NATIVE_FLOOR)
                .setLayout(R.layout.layout_native_custom)
                .setView(findViewById(R.id.native_ads))
                .build();
        RemoteAdmob.getInstance().loadNativeWithConfig(this,adNativeConfig,false);
    }
}
