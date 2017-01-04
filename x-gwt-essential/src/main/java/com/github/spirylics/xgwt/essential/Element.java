package com.github.spirylics.xgwt.essential;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import java.util.*;
import java.util.function.Predicate;
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

    @JsProperty
    Map<HandlerRegistration, String> getHandlerRegistrations();

    @JsProperty
    void setHandlerRegistrations(Map<HandlerRegistration, String> handlerRegistrations);

    @JsOverlay
    default <E extends Element> Stream<E> querySelectorStream(String selector) {
        return Arrays.stream(querySelectorAll(selector));
    }

    @JsOverlay
    default void appendChild(Widget widget) {
        appendChild((Element) widget.getElement());
    }

    @JsOverlay
    default Map<HandlerRegistration, String> handlerRegistrations() {
        if (getHandlerRegistrations() == null) {
            setHandlerRegistrations(new HashMap<>());
        }
        return getHandlerRegistrations();
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

    @JsOverlay
    default Promise<Event, Error> once(final String event, final Predicate<Event>... predicates) {
        return once(event, Arrays.stream(predicates));
    }

    @JsOverlay
    default Promise<Event, Error> once(final String event, final Stream<Predicate<Event>> predicateStream) {
        return new Promise<>((resolve, reject) -> {
            List<HandlerRegistration> handlerRegistrations = new ArrayList<>(1);
            handlerRegistrations.add(on(event, e -> {
                resolve.e(e);
                handlerRegistrations.forEach(h -> h.removeHandler());
            }, predicateStream));
        });
    }

    @JsOverlay
    default Optional<HandlerRegistration> on(final String selector, final String event, Fn.Arg<Event> onFunction, final Predicate<Event>... predicates) {
        return on(querySelectorStream(selector), event, onFunction, predicates);
    }

    @JsOverlay
    default Optional<HandlerRegistration> on(Stream<Element> elementStream, final String event, Fn.Arg<Event> onFunction, final Predicate<Event>... predicates) {
        return elementStream.map(element -> on(event, onFunction, predicates)).reduce((h1, h2) -> () -> {
            h1.removeHandler();
            h2.removeHandler();
        });
    }

    @JsOverlay
    default HandlerRegistration on(final String event, Fn.Arg<Event> onFunction, final Predicate<Event>... predicates) {
        return on(event, onFunction, Arrays.stream(predicates));
    }

    @JsOverlay
    default HandlerRegistration on(final String event, Fn.Arg<Event> onFunction, final Stream<Predicate<Event>> predicateStream) {
        final Optional<Predicate<Event>> predicateOpt = predicateStream.reduce(Predicate::and);
        final Predicate<Event> predicate = predicateOpt.orElse(e -> true);
        final Fn.Arg<Event> wrapFunction = e -> {
            if (predicate.test(e)) {
                onFunction.e(e);
            }
        };
        addEventListener(event, onFunction);
        HandlerRegistration handlerRegistration = new HandlerRegistration() {
            @Override
            public void removeHandler() {
                removeEventListener(event, wrapFunction);
                getHandlerRegistrations().remove(this);
            }
        };
        handlerRegistrations().put(handlerRegistration, event);

        return handlerRegistration;
    }

    @JsOverlay
    default void off() {
        off(handlerRegistrationStringEntry -> true);
    }

    @JsOverlay
    default void off(String... events) {
        off(Arrays.stream(events).map(event ->
                (Predicate<Map.Entry<HandlerRegistration, String>>) handlerRegistrationStringEntry
                        -> handlerRegistrationStringEntry.getValue().equals(event)));
    }

    @JsOverlay
    default void off(Predicate<Map.Entry<HandlerRegistration, String>>... handlerRegistrationPredicates) {
        off(Arrays.stream(handlerRegistrationPredicates));
    }

    @JsOverlay
    default void off(Stream<Predicate<Map.Entry<HandlerRegistration, String>>> handlerRegistrationPredicates) {
        getHandlerRegistrations().entrySet().stream()
                .filter(handlerRegistrationPredicates.reduce(Predicate::and).orElse(e -> true))
                .forEach(handlerRegistrationStringEntry -> handlerRegistrationStringEntry.getKey().removeHandler());
    }

}
