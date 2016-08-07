package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.auth", name = "AuthProvider")
public abstract class AuthProvider {

    @JsProperty
    public native String getProviderId();

    @JsMethod
    public native void addScope(String scope);

    @JsOverlay
    public final AuthProvider xAddScope(Scope.Generic scope) {
        if (this instanceof FacebookAuthProvider) {
            addScope(scope.getFacebookScope().name());

        } else if (this instanceof GoogleAuthProvider) {
            addScope(scope.getGoogleScope().name());
        }
        return this;
    }
}
