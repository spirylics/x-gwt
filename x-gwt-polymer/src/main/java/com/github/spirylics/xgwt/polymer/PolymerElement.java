package com.github.spirylics.xgwt.polymer;

import com.github.spirylics.xgwt.essential.*;
import com.github.spirylics.xgwt.essential.Error;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.query.client.js.JsUtils;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

@JsType(isNative = true)
public interface PolymerElement extends GQueryElement, Base {

    @JsProperty
    Lifecycle getLifecycle();

    @SuppressWarnings("unusable-by-js")
    @JsProperty
    void setLifecycle(Lifecycle lifecycle);

    @JsOverlay
    default Lifecycle lifecycle() {
        if (getLifecycle() == null) {
            setLifecycle(new Lifecycle(this));
        }
        return getLifecycle();
    }

    @JsOverlay
    default <T> T call(final String method, final Object... args) {
        return JsUtils.jsni(this.el(), method, args);
    }

    @JsOverlay
    default <S, E, T> Promise<T, E> callWhen(Promise<S, E> promise, final String method, final Object... args) {
        return promise.then(new Fn.ArgRet<S, T>() {
            @Override
            public T e(S arg) {
                return call(method, args);
            }
        });
    }

    @JsOverlay
    default <T> Promise<T, Error> callWhen(Lifecycle.State state, final String method, final Object... args) {
        return callWhen(when(state), method, args);
    }

    @JsOverlay
    default Promise<Lifecycle.State, Error> when(Lifecycle.State state) {
        return lifecycle().promise(state);
    }

    @JsOverlay
    default String changedEvent(String key) {
        return key + "-changed";
    }

    @JsOverlay
    default <P> HandlerRegistration onPropertyChange(final String key, Fn.Arg<P> onChangeFunction, final Predicate<P>... predicates) {
        final String camelKey = Polymer.toCamelCase(key);
        return on(
                changedEvent(key),
                event -> {
                    PolymerElement p = event.getElement();
                    onChangeFunction.e(p.get(camelKey));
                },
                Stream.empty(),
                Arrays.stream(predicates).map(predicate -> event -> {
                    PolymerElement p = event.getElement();
                    return predicate.test(p.get(camelKey));
                }));
    }

    @JsOverlay
    default <P> Promise<P, Error> whenProperty(final String key, final Predicate<P>... predicates) {
        final String camelKey = Polymer.toCamelCase(key);
        P property = get(camelKey);
        if (Arrays.stream(predicates).reduce(Predicate::and).orElse(e -> true).test(property)) {
            return Promise.resolve(property);
        } else {
            return once(changedEvent(key), Arrays.stream(predicates).map(predicate -> (Predicate<XEvent>) event -> {
                PolymerElement p = event.getElement();
                return predicate.test(p.get(camelKey));
            })).then(new Fn.ArgRet<XEvent, P>() {
                @Override
                public P e(XEvent event) {
                    PolymerElement p = event.getElement();
                    return p.get(camelKey);
                }
            });
        }
    }

    @JsOverlay
    default DomApi domApi() {
        return Polymer.dom((Base) this);
    }

    @JsOverlay
    default DomApi rootDomApi() {
        return Polymer.dom(this.<Element>get("root"));
    }

}
