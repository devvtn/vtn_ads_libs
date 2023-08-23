package com.vtnadslibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.vtn.ads.billing.AppPurchase;
import com.vtn.ads.callback.BillingListener;
import com.vtn.ads.callback.AdCallback;
import com.vtn.ads.config.AdSplashConfig;
import com.vtn.ads.adstype.AdSplashType;
import com.vtn.ads.util.AdmobVTN;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    public static String PRODUCT_ID_MONTH = "android.test.purchased";
    public AdCallback adCallback;
    public AdSplashConfig adSplashConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //Admob.getInstance().setOpenShowAllAds(true);
        //Admob.getInstance().setDisableAdResumeWhenClickAds(true);
        //Admob.getInstance().setOpenEventLoadTimeLoadAdsSplash(true);
        // Admob.getInstance().setOpenEventLoadTimeShowAdsInter(true);
        // Admob.getInstance().setOpenActivityAfterShowInterAds(false);

        adCallback = new AdCallback() {
            @Override
            public void onNextAction() {
                super.onNextAction();
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        };


        // Admob
        AppPurchase.getInstance().setBillingListener(new BillingListener() {
            @Override
            public void onInitBillingListener(int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ShowSplashInter();
                        //ShowSplashInterFloor();
                        //ShowSplashOpen();
                         ShowSplashOpenFloor();
                    }
                });
            }
        }, 5000);

        initBilling();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AdmobVTN.getInstance().dismissLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdmobVTN.getInstance().dismissLoadingDialog();
    }

    private void initBilling() {
        List<String> listINAPId = new ArrayList<>();
        listINAPId.add(PRODUCT_ID_MONTH);
        List<String> listSubsId = new ArrayList<>();
        AppPurchase.getInstance().initBilling(getApplication(), listINAPId, listSubsId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AdmobVTN.getInstance().onCheckShowSplashWhenFailWithConfig(this, adSplashConfig, 1000);
    }

    public void ShowSplashInter() {
        adSplashConfig = new AdSplashConfig.Builder()
                .setKey(AdsConfig.key_ad_interstitial_id)
                .setAdSplashType(AdSplashType.SPLASH_INTER)
                .setTimeDelay(3000)
                .setCallback(adCallback)
                .build();
        AdmobVTN.getInstance().loadAdSplashWithConfig(this, adSplashConfig);

    }

    public void ShowSplashInterFloor() {
        adSplashConfig = new AdSplashConfig.Builder()
                .setKey(AdsConfig.key_ad_splash_floor_id)
                .setAdSplashType(AdSplashType.SPLASH_INTER_FLOOR)
                .setTimeDelay(3000)
                .setCallback(adCallback)
                .build();
        AdmobVTN.getInstance().loadAdSplashWithConfig(this, adSplashConfig);

    }

    public void ShowSplashOpen() {
        adSplashConfig = new AdSplashConfig.Builder()
                .setKey(AdsConfig.key_ad_app_open_ad_id)
                .setAdSplashType(AdSplashType.SPLASH_OPEN)
                .setTimeOut(15000)
                .setTimeDelay(3000)
                .setShowAdIfReady(true)
                .setCallback(adCallback)
                .build();
        AdmobVTN.getInstance().loadAdSplashWithConfig(this, adSplashConfig);

    }

    public void ShowSplashOpenFloor() {
        adSplashConfig = new AdSplashConfig.Builder()
                .setKey(AdsConfig.key_ad_open_floor_id)
                .setAdSplashType(AdSplashType.SPLASH_OPEN_FLOOR)
                .setShowAdIfReady(true)
                .setCallback(adCallback)
                .build();
        AdmobVTN.getInstance().loadAdSplashWithConfig(this, adSplashConfig);

    }
}