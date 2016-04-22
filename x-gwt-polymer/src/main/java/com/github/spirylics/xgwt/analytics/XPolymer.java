package com.github.spirylics.xgwt.analytics;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.deferred.PromiseFunction;
import com.google.gwt.user.client.ui.UIObject;

import javax.annotation.Nullable;
import java.util.Arrays;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;

public class XPolymer {

    public static Promise promiseAttached(final UIObject uiObject) {
        return promise(uiObject.getElement(), "attached");
    }

    public static void extendAttached(final Element element, final Function fn) {
        extendAttached(element, JsUtils.wrapFunction(fn));
    }

    public static void extendAttached(final Element element, final JavaScriptObject fn) {
        extend(element, "attached", fn);
    }

    public static Lifecycle lifecycle(final UIObject uiObject) {
        return new Lifecycle(uiObject.getElement());
    }

    public static Lifecycle lifecycle(final Element element) {
        return new Lifecycle(element);
    }

    public static Promise promise(final Element element, final String functionName) {
        return promise(element, functionName, GQuery.Deferred());
    }

    public static Promise promise(final Element element, final String functionName, final Promise.Deferred deferred) {
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
        $wnd.Polymer.dom.flush();
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

    public static Promise importHtmls(final String... hrefs) {
        return GQuery.when(Lists.transform(Arrays.asList(hrefs), new com.google.common.base.Function<String, Promise>() {
            @Nullable
            @Override
            public Promise apply(@Nullable String href) {
                return importHtml(href);
            }
        }).toArray());
    }

    static Promise importHtml(final String href) {
        return new PromiseFunction() {
            @Override
            public void f(final Deferred dfd) {
                importHtml(href, dfd);
            }
        };
    }

    static native void importHtml(final String href, Promise.Deferred dfd) /*-{
        $wnd.Polymer.Base.importHref(href, function () {
            dfd.@com.google.gwt.query.client.Promise.Deferred::resolve(*)([]);
        }, function () {
            dfd.@com.google.gwt.query.client.Promise.Deferred::reject(*)([]);
        });
    }-*/;


    public static Promise async(final Function fn) {
        return async(fn, 0);
    }


    public static Promise async(final Function fn, final int waitTime) {
        return new PromiseFunction() {
            @Override
            public void f(final Deferred dfd) {
                async(JsUtils.wrapFunction(new Function() {
                    @Override
                    public void f() {
                        fn.f();
                        dfd.resolve();
                    }
                }), waitTime);

            }
        };
    }

    public static native void appendChild(final Element parent, Element child) /*-{
        $wnd.Polymer.dom(parent).appendChild(child);
    }-*/;

    public static native Element querySelector(final Element parent, String child) /*-{
        return $wnd.Polymer.dom(parent).querySelector(child);
    }-*/;

    native static void async(final JavaScriptObject fn, int waitTime) /*-{
        $wnd.Polymer.Async.run(fn, waitTime);
    }-*/;

    public static Promise googleMapsApiPromise() {
        return new PromiseFunction() {
            @Override
            public void f(final Deferred deferred) {
                if ($("google-maps-api").isEmpty()) {
                    $(body).append("<google-maps-api></google-maps-api>");
                }
                GQuery gMapsApi = $("google-maps-api");
                if (gMapsApi.prop("libraryLoaded", Boolean.class)) {
                    deferred.resolve();
                } else {
                    gMapsApi.on("api-load", new Function() {
                        @Override
                        public void f() {
                            deferred.resolve();
                        }
                    });
                }
            }
        };
    }

}
