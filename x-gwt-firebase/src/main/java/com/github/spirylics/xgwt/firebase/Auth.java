package com.github.spirylics.xgwt.firebase;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true)
public interface Auth {
    @JsProperty
    String getUid();

    @JsProperty
    String getProvider();

    @JsProperty
    Facebook getFacebook();
}
