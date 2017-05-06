package com.github.spirylics.xgwt.essential;


import com.google.common.base.MoreObjects;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.gwt.query.client.GQuery.$;

@JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
public class XObject {

    @JsMethod
    public static native XObject create();

    @JsMethod
    public static native String[] keys(XObject smartJsObj);

    @JsOverlay
    public final XObject set(String name, Object value) {
        $(this).prop(name, value);
        return this;
    }

    @JsOverlay
    public final <V> V get(String name) {
        return $(this).prop(name);
    }

    @JsOverlay
    public final Map<String, XObject> toMap() {
        return Arrays.stream(keys(this)).collect(Collectors.toMap(Function.identity(), k -> get(k)));
    }

    @JsOverlay
    public final String asString() {
        MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
        toMap().entrySet().stream().forEach(e -> toStringHelper.add(e.getKey(), e.getValue()));
        return toStringHelper.toString();
    }
}
