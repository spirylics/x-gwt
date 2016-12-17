package com.github.spirylics.xgwt.polymer;


import com.github.spirylics.xgwt.essential.Element;
import com.google.gwt.user.client.ui.Widget;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface DomApi extends Dom {

    <E extends Element> E[] getEffectiveChildNodes();

    <E extends Element> E[] getEffectiveChildren();

    <E extends Element> E queryEffectiveChildren(String selector);

    <E extends Element> E[] queryAllEffectiveChildren(String selector);

    <E extends Element> E querySelector(String selector);

    <E extends Element> E[] querySelectorAll(String selector);

    void appendChild(com.google.gwt.dom.client.Element element);

    void appendChild(Element element);

    void setAttribute(String attribute, Object value);

    void removeAttribute(String attribute);

    @JsOverlay
    default void appendChild(Widget widget) {
        com.google.gwt.dom.client.Element element = widget.getElement();
        appendChild(element);
    }
}
