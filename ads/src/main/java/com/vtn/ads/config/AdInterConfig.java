package com.vtn.ads.config;

import com.vtn.ads.callback.AdCallback;

public class AdInterConfig {
    public String key;
    public AdCallback callback;

    public AdInterConfig(Builder builder) {
        this.key = builder.key;
        this.callback = builder.callback;
    }

    public String getKey() {
        return key;
    }

    public AdCallback getCallback() {
        return callback;
    }
    public static class Builder{
        private String key;
        private AdCallback callback;

        public Builder() {
        }
        public Builder setKey(String key){
            this.key = key;
            return this;
        }

        public Builder setCallback(AdCallback callback){
            this.callback = callback;
            return this;
        }
        public AdInterConfig build(){
            return new AdInterConfig(this);
        }

    }
}
