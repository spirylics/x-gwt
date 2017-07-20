package com.github.spirylics.xgwt.essential;

import com.google.gwt.user.client.Event;

import static com.google.gwt.query.client.GQuery.$;

public class XEvent {
    final Event event;

    public XEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public <E extends Element> E getElement() {
        return (E) $(getEvent()).get(0);
    }

}
