package com.github.spirylics.xgwt.essential;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Promise<S, E> extends Thenable<S, E> {

    @JsConstructor
    public Promise(Fn.Resolver<S, E> resolver) {
    }

    public static native <A, B> Promise<A, B> all(Promise<?, ?>... promises);
}
