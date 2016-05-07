package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;

public class EventRegistration implements HandlerRegistration {
    final Firebase ref;
    final Event event;
    final Fn.Arg<XSnapshot> fn;
    final Fn.Arg<Snapshot<Object>> wrapFn;

    public EventRegistration(Firebase ref, Event event, Fn.Arg<XSnapshot> fn) {
        this.ref = ref;
        this.event = event;
        this.fn = fn;
        this.wrapFn = ref.wrapFn(fn);
    }

    public Event getEvent() {
        return event;
    }

    public Firebase getRef() {
        return ref;
    }

    public Fn.Arg<XSnapshot> getFn() {
        return fn;
    }

    @Override
    public EventRegistration on() {
        getRef().on(getEvent().name(), wrapFn);
        return this;
    }

    @Override
    public EventRegistration off() {
        getRef().off(getEvent().name(), wrapFn);
        return this;
    }

}
