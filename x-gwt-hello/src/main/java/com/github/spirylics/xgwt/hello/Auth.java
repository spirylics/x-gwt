package com.github.spirylics.xgwt.hello;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface Auth {

    @JsProperty
    String getNetwork();

    @JsProperty
    Credential getAuthResponse();

}
