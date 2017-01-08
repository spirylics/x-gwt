package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
public class Options {

    @JsProperty
    public native String getRedirect_uri();

    @JsProperty
    public native void setRedirect_uri(String redirect_uri);

    @JsProperty
    public native String getScope();

    @JsProperty
    public native void setScope(String scope);

    @JsProperty
    public native String getDisplay();

    @JsProperty
    public native void setDisplay(String display);

}
