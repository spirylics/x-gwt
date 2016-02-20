package com.github.spirylics.xgwt.firebase;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface Facebook {

    @JsProperty
    String getDisplayName();
}
