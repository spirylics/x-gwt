package com.github.spirylics.xgwt.firebase.auth;

import com.google.common.base.Strings;
import jsinterop.annotations.JsOverlay;
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

    @JsOverlay
    public final String xGetEmail() {
        if (Strings.isNullOrEmpty(getEmail()) && getProviderData().length > 0) {
            return getProviderData()[0].getEmail();
        } else {
            return getEmail();
        }
    }

}
