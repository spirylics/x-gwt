package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true)
public interface ActionCodeInfoData {
    @JsProperty
    String getEmail();
}
