package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, name = "Credentials")
public class Credentials {

    @JsProperty
    public native String getFacebook();

    @JsProperty
    public native void  setFacebook(String facebook);

}
