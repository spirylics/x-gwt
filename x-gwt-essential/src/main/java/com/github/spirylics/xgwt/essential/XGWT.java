package com.github.spirylics.xgwt.essential;

import com.google.common.collect.Sets;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import java.util.Set;

public class XGWT {

    private static Set<GWT.UncaughtExceptionHandler> UNCAUGHT_EXCEPTION_HANDLERS;

    private XGWT() {
    }

    public static void addUncaughtExceptionHandler(GWT.UncaughtExceptionHandler handler) {
        if (UNCAUGHT_EXCEPTION_HANDLERS == null) {
            UNCAUGHT_EXCEPTION_HANDLERS = Sets.newHashSet();
            GWT.setUncaughtExceptionHandler(throwable ->
                    UNCAUGHT_EXCEPTION_HANDLERS.forEach(h -> h.onUncaughtException(throwable)));
        }
        UNCAUGHT_EXCEPTION_HANDLERS.add(handler);
    }

    public static native JavaScriptObject wrapFunction(final java.util.function.Function f) /*-{
        return function (r) {
            var o = @java.util.ArrayList::new()();
            for (i in arguments) {
                r = @com.google.gwt.query.client.js.JsCache::gwtBox(*)([arguments[i]]);
                o.@java.util.ArrayList::add(Ljava/lang/Object;)(r);
            }
            o = o.@java.util.ArrayList::toArray()();
            return f.@java.util.function.Function::apply(*)(o);
        }
    }-*/;

    public static native void extend(final Object element, final String functionName, final JavaScriptObject fn) /*-{
        var wrap = element[functionName];
        element[functionName] = function (fn, wrap) {
            return function () {
                if (wrap != null) {
                    wrap.apply(this, arguments);
                }
                return fn.apply(this, arguments);
            };
        }(fn, wrap);
    }-*/;

    public static void extend(final com.github.spirylics.xgwt.essential.Element element, final String functionName, final java.util.function.Function fn) {
        extend(element, functionName, wrapFunction(fn));
    }

    public static boolean exists(String... properties) {
        for (String property : properties) {
            if (!exists(property)) {
                return false;
            }
        }
        return true;
    }

    public static native boolean exists(String property) /*-{
        return typeof $wnd[name] !== 'undefined' && $wnd[name] !== null;
    }-*/;


    public static Promise<Void, Exception> loadScript(String url, String... properties) {
        return new Promise<>((resolve, reject) -> {
            if (exists(properties)) {
                resolve.e(null);
            } else {
                Ajax.loadScript(url).done(new Function() {
                    @Override
                    public void f() {
                        resolve.e(null);
                    }
                }).fail(new Function() {
                    @Override
                    public void f() {
                        reject.e(new Exception(url + " not loaded"));
                    }
                });
            }
        });
    }
}
