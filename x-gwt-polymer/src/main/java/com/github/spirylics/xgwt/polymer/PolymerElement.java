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

    @JsOverlay
    default Lifecycle lifecycle() {
        return w().lifecycle();
    }

    @JsOverlay
    default <T> T call(String method, Object... args) {
        return w().call(method, args);
    }

    @JsOverlay
    default Promise call(Lifecycle.State state, final String method, final Object... args) {
        return w().call(state, method, args);
    }

    @JsOverlay
    default Promise callWhenAttached(String method, Object... args) {
        return w().callWhenAttached(method, args);
    }

    @JsOverlay
    default String getAttribute(String key) {
        return w().getAttribute(key);
    }

    @JsOverlay
    default PolymerElement removeAttribute(String key) {
        w().removeAttribute(key);
        return this;
    }

    @JsOverlay
    default PolymerElement setAttribute(String key, Object value) {
        w().setAttribute(key, value);
        return this;
    }

    @JsOverlay
    default <T> T get(String key) {
        return w().get(key);
    }

    @JsOverlay
    default <T> PolymerElement set(String key, T value) {
        w().set(key, value);
        return this;
    }

    @JsOverlay
    default GQuery find(String selector) {
        return w().find(selector);
    }

    @JsOverlay
    default Element findElement(String selector) {
        return w().findElement(selector);
    }

    @JsOverlay
    default PolymerElement off(String event, Function function) {
        w().off(event, function);
        return this;
    }

    @JsOverlay
    default PolymerElement on(String event, Function function) {
        w().on(event, function);
        return this;
    }

    @JsOverlay
    default PolymerElement once(final String event, final Function function) {
        w().once(event, function);
        return this;
    }

    @JsOverlay
    default PolymerElement appendTo(Node node) {
        w().appendTo(node);
        return this;
    }

    @JsOverlay
    default PolymerElement appendTo(GQuery gQuery) {
        w().appendTo(gQuery);
        return this;
    }

    @JsOverlay
    default void offPropertyChange(String key, Function function) {
        w().offPropertyChange(key, function);
    }

    @JsOverlay
    default void onPropertyChange(String key, Function function) {
        w().onPropertyChange(key, function);
    }

    @JsOverlay
    default void oncePropertyChange(String key, Function function) {
        w().oncePropertyChange(key, function);
    }

    @JsOverlay
    default Promise whenEvent(final String event) {
        return w().whenEvent(event);
    }

    @JsOverlay
    default boolean isPropertyEquals(final String key, final Object value) {
        return w().isPropertyEquals(key, value);
    }

    @JsOverlay
    default Promise whenProperty(final String key, final Object value) {
        return w().whenProperty(key, value);
    }
}
