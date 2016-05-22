package com.github.spirylics.xgwt.firebase;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = false)
public class Config {
    @JsProperty
    public native void setApiKey(String apiKey);

    @JsProperty
    public native String getApiKey();

    @JsProperty
    public native void setAuthDomain(String authDomain);

    @JsProperty
    public native String getAuthDomain();

    @JsProperty
    public native void setDatabaseURL(String databaseURL);

    @JsProperty
    public native String getDatabaseURL();

    @JsProperty
    public native void setStorageBucket(String storageBucket);

    @JsProperty
    public native String getStorageBucket();
}
