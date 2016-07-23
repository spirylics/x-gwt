package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.auth", name = "UserCredential")
public interface UserCredential {

    @JsProperty
    User getUser();

    @JsProperty
    AuthCredential getCredential();

}
