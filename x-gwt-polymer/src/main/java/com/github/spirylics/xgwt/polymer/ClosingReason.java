package com.github.spirylics.xgwt.polymer;


import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true)
public class ClosingReason {
    @JsProperty
    public native Boolean getCanceled();

    @JsProperty
    public native Boolean getConfirmed();

}
