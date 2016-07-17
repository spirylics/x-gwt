package com.github.spirylics.xgwt.polymer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.HTMLPanel;

public class PolymerComponent extends HTMLPanel {
    public final PolymerWrapper w;

    public PolymerComponent(String tag, String html) {
        super(tag, html);
        this.w = new PolymerWrapper(getElement());
    }

    public <T> T call(String method, Object... args) {
        return JsUtils.jsni(getElement(), method, args);
    }

    public Lifecycle lifecycle() {
        return w.lifecycle;
    }

    public GQuery $() {
        return w.$;
    }

    public Promise call(Lifecycle.State state, final String method, final Object... args) {
        return w.call(state, method, args);
    }

    public Promise callWhenAttached(String method, Object... args) {
        return w.callWhenAttached(method, args);
    }

    public String getAttribute(String key) {
        return w.getAttribute(key);
    }

    public <P extends PolymerComponent> P setAttribute(String key, Object value) {
        w.setAttribute(key, value);
        return (P) this;
    }

    public <T> T get(String key) {
        return w.get(key);
    }

    public <T, P extends PolymerComponent> P set(String key, T value) {
        w.set(key, value);
        return (P) this;
    }

    public GQuery find(String selector) {
        return w.find(selector);
    }

    public Element findElement(String selector) {
        return w.findElement(selector);
    }

    public <P extends PolymerComponent> P off(String event, Function function) {
        w.off(event, function);
        return (P) this;
    }

    public <P extends PolymerComponent> P on(String event, Function function) {
        w.on(event, function);
        return (P) this;
    }

    public <P extends PolymerComponent> P once(final String event, final Function function) {
        w.once(event, function);
        return (P) this;
    }

    public void offPropertyChange(String key, Function function) {
        w.offPropertyChange(key, function);
    }

    public void onPropertyChange(String key, Function function) {
        w.onPropertyChange(key, function);
    }

    public void oncePropertyChange(String key, Function function) {
        w.onPropertyChange(key, function);
    }

    public Promise whenEvent(final String event) {
        return w.whenEvent(event);
    }

    public boolean isPropertyEquals(final String key, final Object value) {
        return w.isPropertyEquals(key, value);
    }

    public Promise whenProperty(final String key, final Object value) {
        return w.whenProperty(key, value);
    }
}
