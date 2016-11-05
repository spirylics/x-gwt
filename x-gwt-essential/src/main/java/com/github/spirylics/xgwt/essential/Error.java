package com.github.spirylics.xgwt.essential;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface Error {
    @JsProperty
    String getCode();

    @JsProperty
    String getMessage();
}
