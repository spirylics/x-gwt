package com.github.spirylics.xgwt.polymer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface PolymerElement extends Base {

    @JsProperty
    PolymerWrapper getPolymerWrapper();

    @JsProperty
    void setPolymerWrapper(PolymerWrapper polymerWrapper);

    @JsOverlay
    default PolymerWrapper w() {
        if (getPolymerWrapper() == null) {
            setPolymerWrapper(new PolymerWrapper(this));
        }
        return getPolymerWrapper();
    }

    @JsOverlay
    default GQuery $() {
        return w().$();
    }

    @JsOverlay
    default Element el() {
        return w().el();
    }
}
