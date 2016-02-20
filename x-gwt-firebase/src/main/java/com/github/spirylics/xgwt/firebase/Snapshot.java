package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface Snapshot<V> {

    boolean exists();

    String key();

    V val();

    <X> boolean forEach(Fn.Arg<Snapshot<X>> fn);

}
