package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, name = "Credential")
public class Credential {

    @JsProperty
    public native String getAccess_token();

    @JsProperty
    public native void  setAccess_token(String access_token);

    @JsProperty
    public native String getExpires();

    @JsProperty
    public native void  setExpires(String expires);
}
