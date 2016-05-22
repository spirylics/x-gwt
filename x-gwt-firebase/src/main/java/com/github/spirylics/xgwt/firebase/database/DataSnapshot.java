package com.github.spirylics.xgwt.firebase.database;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.firebase.Firebase;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.database", name = "DataSnapshot")
public interface DataSnapshot<V> {

    boolean exists();

    @JsProperty
    String getKey();

    V val();

    <X> boolean forEach(Fn.Arg<DataSnapshot<X>> fn);

    @JsProperty
    Reference getRef();

}
