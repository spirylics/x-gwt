package com.github.spirylics.xgwt.essential;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface GQueryElement {

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
    default Element el() {
        return $().get(0);
    }

}
