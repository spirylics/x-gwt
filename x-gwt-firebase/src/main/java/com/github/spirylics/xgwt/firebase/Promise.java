package com.github.spirylics.xgwt.firebase;

import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase", name = "Promise")
public class Promise<S, E> extends Thenable<S, E> {

    public static native <A, B> Promise<A, B> all(Promise<?, ?>... promises);
}
