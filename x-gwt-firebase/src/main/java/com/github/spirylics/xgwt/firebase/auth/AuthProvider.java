package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.auth", name = "AuthProvider")
public class AuthProvider {

    @JsProperty
    public native String getProviderId();

    @JsProperty
    public native void setProviderId(String providerId);

}
