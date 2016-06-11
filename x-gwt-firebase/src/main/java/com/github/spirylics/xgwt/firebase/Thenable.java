package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase", name = "Thenable")
public class Thenable<S, E> {

    public native <T extends Thenable> T then(Fn.Arg<S> onResolve, Fn.Arg<E> onReject);

    public native <T extends Thenable> T then(Fn.NoArg onResolve, Fn.Arg<E> onReject);

    public native <T extends Thenable> T then(Fn.Arg<S> onResolve);

    public native <T extends Thenable> T then(Fn.NoArg onResolve);

    public native <R, T extends Thenable> T then(Fn.ArgRet<S, R> onResolve);

    public native <R> Thenable then(Fn.ArgRet<S, R> onResolve, Fn.ArgRet<E, E> onReject);

    @JsMethod(name = "catch")
    public native <T extends Thenable> T katch(Fn.Arg<E> onReject);

    @JsMethod(name = "catch")
    public native <T extends Thenable> T katch(Fn.ArgRet<E, E> onReject);
}
