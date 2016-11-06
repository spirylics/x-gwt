package com.github.spirylics.xgwt.polymer;


import com.google.gwt.dom.client.Element;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface DomApi extends Dom {

    PolymerElement querySelector(String selector);

    PolymerElement[] querySelectorAll(String selector);

    void appendChild(PolymerElement element);

    void appendChild(Element element);

}
