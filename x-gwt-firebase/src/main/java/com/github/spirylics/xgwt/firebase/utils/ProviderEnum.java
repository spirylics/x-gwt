package com.github.spirylics.xgwt.firebase.utils;


public enum ProviderEnum {

    GOOGLE("google.com"),
    FACEBOOK("facebook.com");

    private String provider;

    ProviderEnum(String provider){
        this.provider = provider;

    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
