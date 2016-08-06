package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "firebase.auth", name = "GoogleAuthProvider")
public class GoogleAuthProvider extends AuthProvider {

    @JsConstructor
    public GoogleAuthProvider() {
    }

    @JsMethod
    public native void addScope(String scope);

    @JsMethod
    public native AuthCredential credential(String idToken, String accessToken);


}
