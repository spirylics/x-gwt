package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase", name = "User")
public class User extends UserInfo {
    
    @JsProperty
    public native boolean getEmailVerified();

    @JsProperty
    public native boolean getIsAnonymous();

    @JsProperty
    public native UserInfo[] getProviderData();

}
