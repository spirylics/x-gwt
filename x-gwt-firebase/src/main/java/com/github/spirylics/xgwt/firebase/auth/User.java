package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase", name = "User")
public interface User {
    @JsProperty
    String getUid();

    @JsProperty
    String getProvider();
}
