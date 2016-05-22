package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true)
public class Credentials {

    @JsOverlay
    public static final Credentials create(String email, String password) {
        Credentials credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);
        return credentials;
    }

    @JsProperty
    public native String getEmail();

    @JsProperty
    public native void setEmail(String email);

    @JsProperty
    public native String getPassword();

    @JsProperty
    public native void setPassword(String password);
}
