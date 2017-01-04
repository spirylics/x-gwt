package com.github.spirylics.xgwt.polymer;

import com.github.spirylics.xgwt.essential.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.deferred.PromiseFunction;
import jsinterop.annotations.*;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Polymer")
public abstract class Polymer {

    @JsProperty(name = "Base")
    public static native Base Base();

    @JsProperty(name = "dom")
    public static native Dom dom();

    @JsMethod
    public static native DomApi dom(Base base);

    @JsMethod
    public static native DomApi dom(Element element);

    @JsMethod
    public static native DomApi dom(com.google.gwt.dom.client.Element element);

    @JsOverlay
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
