package com.github.spirylics.xgwt.essential;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import java.util.Arrays;
import java.util.stream.Stream;

@JsType(isNative = true)
public interface Element {

    @JsOverlay
    static <E extends Element> E create(String tag) {
        return (E) DOM.createElement(tag);
    }

    void addEventListener(String event, Fn.Arg<Event> function, boolean useCapture);

    void addEventListener(String event, Fn.Arg<Event> function);

    void removeEventListener(String event, Fn.Arg<Event> function, boolean useCapture);

    void removeEventListener(String event, Fn.Arg<Event> function);

    String getAttribute(String attribute);

    void setAttribute(String attribute, Object value);

    void removeAttribute(String attribute);

    <E extends Element> E querySelector(String selector);

    <E extends Element> E[] querySelectorAll(String selector);

    void focus();

    void blur();

    void appendChild(Element element);

    @JsOverlay
    default <E extends Element> Stream<E> querySelectorStream(String selector) {
        return Arrays.stream(querySelectorAll(selector));
    }

    @JsOverlay
    default void appendChild(Widget widget) {
        appendChild((Element) widget.getElement());
    }


    @JsOverlay
    default <E extends Element> E focus(boolean focused) {
        if (focused) {
            focus();
        } else {
            blur();
        }
        return (E) this;
    }

    @JsOverlay
    default <E extends Element> E hidden(boolean hidden) {
        return attribute("hidden", hidden);
    }

    @JsOverlay
    default <E extends Element> E attribute(String attribute, Object value) {
        if (value == null || Boolean.FALSE.equals(value)) {
            removeAttribute(attribute);
        } else {
            setAttribute(attribute, XMapper.get().write(value));
        }
        return (E) this;
    }

    @JsOverlay
    default <V> V attribute(String attribute, Class<V> clazz) {
        return XMapper.get().read(getAttribute(attribute), clazz);
    }

}
