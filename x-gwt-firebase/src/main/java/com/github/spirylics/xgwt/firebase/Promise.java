package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true)
public interface Promise<S, E> {

    Promise then(Fn.Arg<S> success, Fn.Arg<E> error);

    Promise then(Fn.NoArg success, Fn.Arg<E> error);

    <R> Promise then(Fn.ArgRet<S, R> success, Fn.ArgRet<E, E> error);
}
