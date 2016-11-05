package com.github.spirylics.xgwt.polymer;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Polymer")
public abstract class Polymer {

    @JsProperty(name = "Base")
    public static native Base Base();
}
