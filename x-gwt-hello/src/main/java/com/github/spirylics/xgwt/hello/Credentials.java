package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
public class Credentials {

    @JsProperty
    public native String getFacebook();

    @JsProperty
    public native void setFacebook(String facebook);

}
