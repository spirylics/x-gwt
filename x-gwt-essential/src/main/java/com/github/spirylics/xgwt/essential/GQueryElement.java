package com.github.spirylics.xgwt.essential;

import com.google.gwt.query.client.GQuery;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface GQueryElement extends Element {

    @JsProperty
    GQuery getGQuery();

    @JsProperty
    void setGQuery(GQuery gQuery);

    @JsOverlay
    default GQuery $() {
        if (getGQuery() == null) {
            setGQuery(com.google.gwt.query.client.GQuery.$(this));
        }
        return getGQuery();
    }

    @JsOverlay
    default com.google.gwt.dom.client.Element el() {
        return $().get(0);
    }

    @JsOverlay
    default <T> T get(String key) {
        return $().prop(key);
    }

    @JsOverlay
    default <T, G extends GQueryElement> G set(String key, T value) {
        $().prop(key, value);
        return (G) this;
    }

    @JsOverlay
    default String asString() {
        return $().toString(true);
    }

}
