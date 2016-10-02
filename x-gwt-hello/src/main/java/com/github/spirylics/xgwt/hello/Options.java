package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "hello", name = "Options")
public class Options {

    @JsProperty
    public native String getDisplay();

    @JsProperty
    public native void setDisplay(String display);
}
