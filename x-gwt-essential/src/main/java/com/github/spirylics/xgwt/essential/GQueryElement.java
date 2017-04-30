package com.github.spirylics.xgwt.essential;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@JsType(isNative = true)
public interface GQueryElement extends Element {

    @JsProperty
    GQuery getGQuery();

    @SuppressWarnings("unusable-by-js")
    @JsProperty
    void setGQuery(GQuery gQuery);

    @JsOverlay
    default GQuery $() {
        if (getGQuery() == null) {
            setGQuery(com.google.gwt.query.client.GQuery.$(this));
        }
        return getGQuery();
    }

    @JsOverlay
    default com.google.gwt.dom.client.Element el() {
        return $().get(0);
    }

    @JsOverlay
    default <T> T get(String key) {
        return $().prop(key);
    }

    @JsOverlay
    default <T, G extends GQueryElement> G set(String key, T value) {
        $().prop(key, value);
        return (G) this;
    }

    @JsOverlay
    default HandlerRegistration on(final String eventName,
                                   final Fn.Arg<XEvent> onFunction) {
        return on(eventName, onFunction, Stream.empty(), Stream.empty());
    }

    @JsOverlay
    default HandlerRegistration on(final String eventName,
                                   final Fn.Arg<XEvent> onFunction,
                                   final Predicate<XEvent>... predicates) {
        return on(eventName, onFunction, Stream.empty(), Arrays.stream(predicates));
    }

    @JsOverlay
    default HandlerRegistration on(final String eventName,
                                   final Fn.Arg<XEvent> onFunction,
                                   final String... selectors) {
        return on(eventName, onFunction, Arrays.stream(selectors), Stream.empty());
    }

    @JsOverlay
    default HandlerRegistration on(final String eventName,
                                   final Fn.Arg<XEvent> onFunction,
                                   final Stream<String> selectors,
                                   final Stream<Predicate<XEvent>> predicateStream) {
        final Optional<Predicate<XEvent>> predicateOpt = predicateStream.reduce(Predicate::and);
        final Predicate<XEvent> predicate = predicateOpt.orElse(e -> true);
        final Fn.Arg<Event> wrapFn = event -> {
            XEvent xEvent = new XEvent(event, this);
            if (predicate.test(xEvent)) {
                onFunction.e(xEvent);
            }
        };
        return selectors
                .map(selector -> {
                    final Function wrapGFn = new Function() {
                        @Override
                        public void f() {
                            wrapFn.e(getEvent());
                        }
                    };
                    $().on(eventName, selector, wrapGFn);
                    return (HandlerRegistration) () -> $().off(eventName, wrapGFn);
                })
                .reduce((h1, h2) -> () -> {
                    h1.removeHandler();
                    h2.removeHandler();
                })
                .orElseGet(() -> {
                    addEventListener(eventName, wrapFn);
                    return () -> removeEventListener(eventName, wrapFn);
                });
    }

    @JsOverlay
    default Promise<XEvent, Error> once(final String eventName, final Predicate<XEvent>... predicates) {
        return once(eventName, Arrays.stream(predicates));
    }

    @JsOverlay
    default Promise<XEvent, Error> once(final String eventName, final Stream<Predicate<XEvent>> predicateStream) {
        return new Promise<>((resolve, reject) -> {
            List<HandlerRegistration> handlerRegistrations = new ArrayList<>(1);
            handlerRegistrations.add(on(eventName, e -> {
                resolve.e(e);
                handlerRegistrations.forEach(h -> h.removeHandler());
            }, Stream.empty(), predicateStream));
        });
    }

    @JsOverlay
    default String asString() {
        return $().toString(true);
    }

}
