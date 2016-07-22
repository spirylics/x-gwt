package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase", name = "UserInfo")
public class UserInfo {

    @JsProperty
    public native String getDisplayName();

    @JsProperty
    public native String getEmail();
    
    @JsProperty
    public native String getPhotoURL();

    @JsProperty
    public native String getUid();

    @JsProperty
    public native String getProviderId();

}
