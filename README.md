<h1>VTNAdsLibraty</h1>
<h3><li>Adding the library to your project: Add the following in your root build.gradle at the end of repositories:</li></h3>

<pre>
  allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }	    
    }
}
</pre>
<h5>Implement library in your app level build.gradle:</h5>
<pre>
 dependencies {
    //ads
    implementation 'com.github.devvtn:vtn_ads_libs:Tag'
    implementation 'com.google.android.gms:play-services-ads:22.2.0'
    //multidex
    implementation "androidx.multidex:multidex:2.0.1"
    implementation 'com.facebook.shimmer:shimmer:0.5.0'
  }

  defaultConfig {
    multiDexEnabled true
  }
</pre>
<h3><li>Add app id in Manifest:</br></h3>
<pre>
     < meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="@string/app_id" />
</pre>
<h3><li>Init aplication</br></h3>
<pre> < application
   android:name=".MyApplication"
   .
   .
   .../></pre>
<pre>

public class MyApplication extends AdsApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppOpenManager.getInstance().disableAppResumeWithActivity(Splash.class);
        initIDADS();

    }

    @Override
    public boolean enableAdsResume() {
        return true;
    }

    @Override
    public List<String> getListTestDeviceId() {
        return null;
    }

    @Override
    public String getResumeAdId() {
        return getString(id);
    }

    @Override
    public Boolean buildDebug() {
        return false;
    }

    public void initIDADS(){
        AdmobVTN.getInstance().initListID();
        AdmobVTN.getInstance().insertIdAds(key,id);
        List<String> listID = new ArrayList<>();
        listIDSplash.add(id);
        listIDSplash.add(id);
        AdmobVTN.getInstance().insertListIdAds(key,listID);
    }


}

</pre>
<h2>- BannerAds</h2>
<div class="content">
  <h4>View xml</h4>
<pre>

      <include
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        layout="@layout/layout_banner"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_alignParentBottom="true"/>

</pre>
<h4>Config banner</h4>
<pre>

AdBannerConfig adBannerConfig;
adBannerConfig = new AdBannerConfig.Builder()
                .setKey(key)
                .setBannerType(AdBannerType.BANNER)
                .setGravity(BannerGravity.top)
                .setView(findViewById(R.id.banner))
                .build();

</pre>
<h4>Load banner in ativity</h4>
<pre>

     AdmobVTN.getInstance().loadBannerWithConfig(this,adBannerConfig);

</pre>

</div>
<h2>IntertitialAds</h2>
<div class="content">
<h3>Splash</h3>
<h4>Splash callback</h4>
<pre>

public AdCallback adCallback;
 adCallback = new AdCallback() {
    @Override
    public void onNextAction() {
        super.onNextAction();
        startActivity(new Intent(Splash.this, MainActivity.class));
        finish();
    }
};

</pre>
<h4>Splash config</h4>
<pre>

public AdSplashConfig adSplashConfig;
adSplashConfig = new AdSplashConfig.Builder()
                .setKey(key)
                .setAdSplashType(AdSplashType.SPLASH_INTER)
                .setTimeOut(15000)
                .setTimeDelay(3000)
                .setShowAdIfReady(true)
                .setCallback(adCallback)
                .build();

</pre>
<h4>Show ads splash</h4>
<pre>

AdmobVTN.getInstance().loadAdSplashWithConfig(this, adSplashConfig);

</pre>
<pre>

@Override
protected void onResume() {
  super.onResume();
  AdmobVTN.getInstance().onCheckShowSplashWhenFailWithConfig(this, adSplashConfig, 1000);
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

</pre>
<h3>- InterstitialAds</h3>
  <h4>Inter config</h4>
<pre>

public AdInterConfig adInterConfig;

adInterConfig = new AdInterConfig.Builder()
      .setKey(AdsConfig.key_ad_interstitial_id)
      .setCallback(new AdCallback() {
        @Override
        public void onNextAction() {
            super.onNextAction();
            startActivity(new Intent(MainActivity.this, MainActivity3.class));
            AdmobVTN.getInstance().loadInterWithKey(MainActivity.this, AdsConfig.key_ad_interstitial_id, false);
        }
      })
      .build();

</pre>
<h4>Show Inter</h4>
<pre>

AdmobVTN.getInstance().showInterWithConfig(MainActivity.this, adInterConfig);

</pre>
</div>

<h2>- RewardAds</h2>
<div class="content">
  <h4>RewardAds config</h4>
<pre>

public AdRewardConfig adRewardConfig;
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

</pre>
<h4>Init RewardAds</h4>
<pre>
AdmobVTN.getInstance().initRewardWithConfig(this, adRewardConfig);
</pre>
<h4>Show RewardAds</h4>
<pre>
AdmobVTN.getInstance().showRewardWithConfig(MainActivity.this, adRewardConfig);

</pre>
</div>

<h2>- NativeAds</h2>
<div class="content">
  <h4>View xml</h4>
<pre>

    <FrameLayout
        android:id="@+id/native_ads"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true">

        <include layout="@layout/ads_native_shimer" />
    </FrameLayout>

</pre>
<h4>NativeAds config</h4>
<pre>

      AdNativeConfig adNativeConfig;
      adNativeConfig = new AdNativeConfig.Builder()
                .setKey(key)
                .setNativeType(AdNativeType.NATIVE)
                .setLayout(R.layout.layout_native_custom)
                .setView(findViewById(R.id.native_ads))
                .build();

</pre>

<h4>NativeAds show</h4>
<pre>

      AdNativeConfig adNativeConfig;

</pre>

</div>

<h4>Hide all ads</h4>
<pre>
 Admob.getInstance().setShowAllAds(true);
 true - show all ads
 false - hide all ads
</pre>
