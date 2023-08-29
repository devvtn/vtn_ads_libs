package com.vtn.ads.util;

public class GoogleAdmobImpl extends GoogleAdmob {

    public static GoogleAdmobImpl getInstance() {
        return new GoogleAdmobImpl();
    }

    @Override
    public void loadNative() {
        System.out.println("Hello world");
    }
}
