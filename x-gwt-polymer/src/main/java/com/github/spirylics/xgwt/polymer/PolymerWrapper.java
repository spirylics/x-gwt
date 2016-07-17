package com.github.spirylics.xgwt.polymer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;

import static com.google.gwt.query.client.GQuery.$;

public class PolymerWrapper {
    public final Element el;
    public final Lifecycle lifecycle;
    public final GQuery $;

    public PolymerWrapper(Element element) {
        this.el = element;
        this.lifecycle = XPolymer.lifecycle(element);
        this.$ = $(el);
    }

    public <T> T call(String method, Object... args) {
        return JsUtils.jsni(this.el, method, args);
    }

    public Promise call(Lifecycle.State state, final String method, final Object... args) {
        return lifecycle.promise(state).then(new Function() {
            @Override
            public Object f(Object... arguments) {
                return call(method, args);
            }
        });
    }

    public Promise callWhenAttached(String method, Object... args) {
        return call(Lifecycle.State.attached, method, args);
    }

    public String getAttribute(String key) {
        return $.attr(key);
    }

    public <P extends PolymerWrapper> P setAttribute(String key, Object value) {
        $.attr(key, value);
        return (P) this;
    }

    public <T> T get(String key) {
        return $.prop(key);
    }

    public <T, P extends PolymerWrapper> P set(String key, T value) {
        $.prop(key, value);
        return (P) this;
    }

    public GQuery find(String selector) {
        return $.find(selector);
    }

    public Element findElement(String selector) {
        return find(selector).get(0);
    }

    public <P extends PolymerWrapper> P off(String event, Function function) {
        $.off(event, function);
        return (P) this;
    }

    public <P extends PolymerWrapper> P on(String event, Function function) {
        $.on(event, function);
        return (P) this;
    }

    public <P extends PolymerWrapper> P once(final String event, final Function function) {
        on(event, new Function() {
            @Override
            public void f() {
                function.f(getEvent(), getArguments());
                off(event, this);
            }
        });
        return (P) this;
    }

    public <P extends PolymerWrapper> P appendTo(Node node) {
        $.appendTo(node);
        return (P) this;
    }

    public <P extends PolymerWrapper> P appendTo(GQuery gQuery) {
        $.appendTo(gQuery);
        return (P) this;
    }

    public void offPropertyChange(String key, Function function) {
        off(key + "-changed", function);
    }

    public void onPropertyChange(String key, Function function) {
        on(key + "-changed", function);
    }

    public void oncePropertyChange(String key, Function function) {
        once(key + "-changed", function);
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
}
