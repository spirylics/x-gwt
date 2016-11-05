package com.github.spirylics.xgwt.essential;

import com.google.common.collect.Sets;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.Promise.Deferred;
import com.google.gwt.query.client.js.JsUtils;

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

    public static Promise promise(final Element element, final String functionName, final Deferred deferred) {
        extend(element, functionName, new Function() {
            @Override
            public void f() {
                deferred.resolve();
            }
        });
        return deferred.promise();
    }

    public static void extend(final Element element, final String functionName, final Function fn) {
        extend(element, functionName, JsUtils.wrapFunction(fn));
    }

    public static native void extend(final Element element, final String functionName, final JavaScriptObject fn) /*-{
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
}
