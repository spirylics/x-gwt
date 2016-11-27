package com.github.spirylics.xgwt.polymer;

import com.github.spirylics.xgwt.essential.GQueryElement;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface PolymerElement extends GQueryElement {

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

}
