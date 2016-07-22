package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.auth", name = "AuthCredential")
public interface AuthCredential {

    @JsProperty
    String getProvider();

    @JsProperty
    void setProvider(String provider);

}
