package com.github.spirylics.xgwt.firebase.database;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.firebase.Event;
import com.github.spirylics.xgwt.firebase.HandlerRegistration;

public class EventRegistration implements HandlerRegistration {
    final Reference reference;
    final Event event;
    final Fn.Arg<XDataSnapshot> fn;
    final Fn.Arg<DataSnapshot<Object>> wrapFn;

    public EventRegistration(Reference reference, Event event, Fn.Arg<XDataSnapshot> fn) {
        this.reference = reference;
        this.event = event;
        this.fn = fn;
        this.wrapFn = reference.wrapFn(fn);
    }

    public Event getEvent() {
        return event;
    }

    public Reference getRef() {
        return reference;
    }

    public Fn.Arg<XDataSnapshot> getFn() {
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
