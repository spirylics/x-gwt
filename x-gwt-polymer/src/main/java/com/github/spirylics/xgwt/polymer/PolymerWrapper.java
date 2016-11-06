package com.github.spirylics.xgwt.polymer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;

public class PolymerWrapper {
    final Lifecycle lifecycle;
    final GQuery $;

    public PolymerWrapper(Object target) {
        this.$ = com.google.gwt.query.client.GQuery.$(target);
        this.lifecycle = new Lifecycle(el());
    }

    public GQuery $() {
        return this.$;
    }

    public Element el() {
        return $().get(0);
    }

    public Lifecycle lifecycle() {
        return this.lifecycle;
    }

    public <T> T call(String method, Object... args) {
        return JsUtils.jsni(el(), method, args);
    }

    public Promise call(Lifecycle.State state, final String method, final Object... args) {
        return lifecycle().promise(state).then(new Function() {
            @Override
            public Object f(Object... arguments) {
                return call(method, args);
            }
        });
    }

    public Promise callWhenAttached(String method, Object... args) {
        return call(Lifecycle.State.attached, method, args);
    }

    public PolymerWrapper addClass(String... classes) {
        $().addClass(classes);
        return this;
    }

    public String getAttribute(String key) {
        return $().attr(key);
    }

    public PolymerWrapper removeAttribute(String key) {
        $().removeAttr(key);
        return this;
    }

    public PolymerWrapper setAttribute(String key, Object value) {
        $().attr(key, value);
        return this;
    }

    public <T> T get(String key) {
        return $.prop(key);
    }

    public <T> PolymerWrapper set(String key, T value) {
        $().prop(key, value);
        return this;
    }

    public GQuery find(String selector) {
        return $.find(selector);
    }

    public Element findElement(String selector) {
        return find(selector).get(0);
    }

    public PolymerWrapper off(String event, Function function) {
        $().off(event, function);
        return this;
    }

    public PolymerWrapper on(String event, Function function) {
        $().on(event, function);
        return this;
    }

    public PolymerWrapper once(final String event, final Function function) {
        on(event, new Function() {
            @Override
            public void f() {
                function.f(getEvent(), getArguments());
                off(event, this);
            }
        });
        return this;
    }

    public PolymerWrapper appendTo(Node node) {
        $().appendTo(node);
        return this;
    }

    public PolymerWrapper appendTo(GQuery gQuery) {
        $().appendTo(gQuery);
        return this;
    }

    public void offPropertyChange(String key, Function function) {
        off(changedEvent(key), function);
    }

    public void onPropertyChange(String key, Function function) {
        on(changedEvent(key), function);
    }

    public void oncePropertyChange(String key, Function function) {
        once(changedEvent(key), function);
    }

    public Promise whenEvent(final String event) {
        final Promise.Deferred deferred = GQuery.Deferred();
        once(event, new Function() {
            @Override
            public void f() {
                deferred.resolve();
            }
        });
        return deferred.promise();
    }

    public boolean isPropertyEquals(final String key, final Object value) {
        Object property = get(key);
        return property == value || (property != null && property.equals(value));
    }

    public Promise whenProperty(final String key, final Object value) {
        final Promise.Deferred deferred = GQuery.Deferred();
        if (isPropertyEquals(key, value)) {
            deferred.resolve(value);
        } else {
            onPropertyChange(key, new Function() {
                @Override
                public void f() {
                    if (isPropertyEquals(key, value)) {
                        offPropertyChange(key, this);
                        deferred.resolve(value);
                    }
                }
            });
        }
        return deferred.promise();
    }

    String changedEvent(String key) {
        return key + "-changed";
    }

    public DomApi domApi() {
        return Polymer.dom(this.el());
    }

    public PolymerElement $$(String selector) {
        return domApi().querySelector(selector);
    }

    public PolymerElement[] querySelectorAll(String selector) {
        return domApi().querySelectorAll(selector);
    }

    public PolymerWrapper appendChild(PolymerElement element) {
        domApi().appendChild(element);
        return this;
    }

    public PolymerWrapper appendChild(Element element) {
        domApi().appendChild(element);
        return this;
    }

    public PolymerWrapper appendChild(PolymerWrapper polymerWrapper) {
        return appendChild(polymerWrapper.el());
    }

    public PolymerWrapper appendChild(PolymerWidget polymerWidget) {
        return appendChild(polymerWidget.w());
    }
}
