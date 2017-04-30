package com.github.spirylics.xgwt.essential;

import com.google.gwt.user.client.Event;

public class XEvent {
    final Event event;

    public XEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public <E extends Element> E getElement() {
        return (E) event.getEventTarget();
    }

}
