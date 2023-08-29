package com.vtnadslibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vtn.ads.banner.BannerPlugin;
import com.vtn.ads.billing.AppPurchase;
import com.vtn.ads.callback.IClickBtn;
import com.vtn.ads.callback.NativeCallback;
import com.vtn.ads.callback.PurchaseListioner;
import com.vtn.ads.callback.RewardCallback;
import com.vtn.ads.callback.AdCallback;
import com.vtn.ads.config.AdInterConfig;
import com.vtn.ads.config.AdRewardConfig;
import com.vtn.ads.rate.RateBuilder;
import com.vtn.ads.util.Admob;
import com.vtn.ads.util.RemoteAdmob;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private FrameLayout native_ads;

    public static String PRODUCT_ID_YEAR = "android.test.purchased";
    public static String PRODUCT_ID_MONTH = "android.test.purchased";

    public AdRewardConfig adRewardConfig;
    public AdInterConfig adInterConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        native_ads = findViewById(R.id.native_ads);
        // Admob.getInstance().loadCollapsibleBanner(this, getString(R.string.admod_banner_id), BannerGravity.bottom);
        adRewardConfig = new AdRewardConfig.Builder()
                .setKey(AdsConfig.key_ad_app_reward_id)
                .setRewardCallback(new RewardCallback() {
                    @Override
                    public void onEarnedReward(RewardItem rewardItem) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(MainActivity.this, "Close ads", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToShow(int codeError) {
                        Toast.makeText(MainActivity.this, "Loa ads err", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        RemoteAdmob.getInstance().initRewardWithConfig(this, adRewardConfig);


        BannerPlugin.Config config = new BannerPlugin.Config();
        config.defaultAdUnitId = getString(R.string.admod_banner_id);
        config.defaultBannerType = BannerPlugin.BannerType.Adaptive;
        config.setConfigKey("test_banner_flugin");


        //BannerPlugin bannerPlugin = new BannerPlugin(this,findViewById(R.id.banner),findViewById(R.id.shimmer),config);
        // Admob.getInstance().loadBannerPlugin(this, findViewById(R.id.banner), findViewById(R.id.shimmer), config);
        Admob.getInstance().loadCollapsibleBanner(this, getString(R.string.admod_banner_id_collapse), "bottom");
        RemoteAdmob.getInstance().loadInterWithKey(this, AdsConfig.key_ad_interstitial_id, true);
        loadAdsNative();


        findViewById(R.id.clickFGM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
        findViewById(R.id.testbanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
            }
        });
        takePermission();


        findViewById(R.id.btnClickInter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 adInterConfig = new AdInterConfig.Builder()
                        .setKey(AdsConfig.key_ad_interstitial_id)
                        .setCallback(new AdCallback() {
                            @Override
                            public void onNextAction() {
                                super.onNextAction();
                                startActivity(new Intent(MainActivity.this, MainActivity3.class));
                                RemoteAdmob.getInstance().loadInterWithKey(MainActivity.this, AdsConfig.key_ad_interstitial_id, false);
                            }
                        })
                        .build();
                RemoteAdmob.getInstance().showInterWithConfig(MainActivity.this, adInterConfig);

            }
        });


        findViewById(R.id.btnClickReward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteAdmob.getInstance().showRewardWithConfig(MainActivity.this, adRewardConfig);
            }
        });


        findViewById(R.id.btnBilding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPurchase.getInstance().consumePurchase(PRODUCT_ID_MONTH);
                AppPurchase.getInstance().purchase(MainActivity.this, PRODUCT_ID_MONTH);
                //real
                // AppPurchase.getInstance().subscribe(MainActivity.this, SubID);
            }
        });


        AppPurchase.getInstance().setPurchaseListioner(new PurchaseListioner() {
            @Override
            public void onProductPurchased(String productId, String transactionDetails) {
                Toast.makeText(MainActivity.this, "Purchase success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void displayErrorMessage(String errorMsg) {
                Toast.makeText(MainActivity.this, "Purchase fall", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserCancelBilling() {

                Toast.makeText(MainActivity.this, "Purchase cancel", Toast.LENGTH_SHORT).show();
            }
        });
        // reset pay Purchase
       /*AppPurchase.getInstance().consumePurchase(Constants.PRODUCT_ID_MONTH);
        AppPurchase.getInstance().consumePurchase(Constants.PRODUCT_ID_YEAR);
        AppPurchase.getInstance().setPurchaseListioner(new PurchaseListioner() {
            @Override
            public void onProductPurchased(String productId,String transactionDetails) {

            }

            @Override
            public void displayErrorMessage(String errorMsg) {
                Log.e("PurchaseListioner","displayErrorMessage:"+ errorMsg);
            }

            @Override
            public void onUserCancelBilling() {

            }
        });*/
    }

    private void takePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
    }

    private void loadAdsNative() {
        List<String> listID = new ArrayList<>();
        listID.add("1");
        listID.add("2");
        listID.add("3");

        Admob.getInstance().loadNativeAdFloor(this, listID, new NativeCallback() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                NativeAdView adView = (NativeAdView) LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_native_custom, null);
                native_ads.removeAllViews();
                native_ads.addView(adView);
                Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);
            }

            @Override
            public void onAdFailedToLoad() {
                native_ads.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onBackPressed() {
        RateBuilder builder = new RateBuilder(this)
                .setArrStar(new int[]{R.drawable.ic_mstar_0, R.drawable.ic_mstar_1, R.drawable.ic_mstar_2, R.drawable.ic_mstar_3, R.drawable.ic_mstar_4, R.drawable.ic_mstar_5})
                .setTextTitle("Rate us")
                .setTextContent("Tap a star to set your rating")
                .setTextButton("Rate now", "Not now")
                .setTextTitleColor(Color.parseColor("#000000"))
                .setTextNotNowColor(Color.parseColor("#EDEDED"))
                .setDrawableButtonRate(R.drawable.border_rate)
                .setBackgroundDialog(R.drawable.border_bg_dialog)
                .setBackgroundStar(R.drawable.border_bg_star)
                .setColorRatingBar("#FAFF00")
                .setColorRatingBarBG("#E0E0E0")
                .setTextNotNowSize(12)
                .setNumberRateInApp(5)
                .setFontFamily(ResourcesCompat.getFont(this, R.font.poppins_regular))
                .setFontFamilyTitle(ResourcesCompat.getFont(this, R.font.poppins_semibold))
                .setOnclickBtn(new IClickBtn() {
                    @Override
                    public void onclickNotNow() {
                        Toast.makeText(MainActivity.this, "onclickNotNow", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onClickRate(float rate) {
                        Toast.makeText(MainActivity.this, rate + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReviewAppSuccess() {

                    }
                });
        builder.build();
        builder.rateAppDiaLog.show();
    }
}