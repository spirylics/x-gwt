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
    public static native AuthCredential credential(String idToken);

    @JsMethod
    public static native AuthCredential credential(String idToken, String accessToken);

}
