package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

import java.util.Map;

@SuppressWarnings("ALL")
@JsType(isNative = true, name = "hello")
public class Hello {

    @JsMethod
    public static native void init(Map<String,String> credentials,Options options);

}