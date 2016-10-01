package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@JsType(isNative = true, name = "hello")
public class Hello {

    @JsMethod(namespace = JsPackage.GLOBAL)
    public native static Hello hello();

    @JsMethod(namespace = JsPackage.GLOBAL)
    public native static Hello hello(String network);

    public native Hello init(Map<String,String> credential, Options options);

    public native void login();

    public native Credential getAuthResponse();

}