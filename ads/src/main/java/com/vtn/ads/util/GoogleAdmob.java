package com.vtn.ads.util;

public abstract class GoogleAdmob {
    public GoogleAdmob() {
    }

    public static GoogleAdmob getInstance() {
        return GoogleAdmobImpl.getInstance();
    }

    public abstract void loadNative();
}
