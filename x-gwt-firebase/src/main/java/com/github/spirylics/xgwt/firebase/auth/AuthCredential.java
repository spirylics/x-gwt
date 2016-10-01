package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.auth", name = "AuthCredential")
public class AuthCredential {

    @JsProperty
    public native String getProvider();

    @JsProperty
    public native void setProvider(String provider);

}
