package com.github.spirylics.xgwt.essential;

import com.google.gwt.user.client.Event;

public class XEvent {
    final Event event;
    final Element element;

    public XEvent(Event event, Element element) {
        this.event = event;
        this.element = element;
    }

    public Event getEvent() {
        return event;
    }

    public <E extends Element> E getElement() {
        return (E) element;
    }

}
