package com.github.spirylics.xgwt.essential;


import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

import static com.google.gwt.query.client.GQuery.$;

@JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
public class SmartJsObject {

    @JsOverlay
    public final SmartJsObject set(String name, Object value) {
        $(this).prop(name, value);
        return this;
    }

    @JsOverlay
    public final <V> V get(String name) {
        return $(this).prop(name);
    }

}
