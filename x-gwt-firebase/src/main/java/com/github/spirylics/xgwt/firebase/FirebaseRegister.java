package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.firebase.auth.Auth;
import com.github.spirylics.xgwt.firebase.auth.AuthRegistration;
import com.github.spirylics.xgwt.firebase.auth.User;
import com.github.spirylics.xgwt.firebase.database.EventRegistration;
import com.github.spirylics.xgwt.firebase.database.Reference;
import com.github.spirylics.xgwt.firebase.database.XDataSnapshot;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class FirebaseRegister {

    private final Map<HandlerRegistration, Boolean> handlerRegistrations = Maps.newHashMap();

    public HandlerRegistration add(Reference reference, Event event, Fn.Arg<XDataSnapshot> fn, boolean auth, boolean on) {
        return add(new EventRegistration(reference, event, fn), auth, on);
    }

    public HandlerRegistration addAuth(Auth auth, Fn.Arg<User> fn, boolean on) {
        return add(new AuthRegistration(auth, fn), false, on);
    }

    public HandlerRegistration add(HandlerRegistration handlerRegistration, boolean auth, boolean on) {
        handlerRegistrations.put(handlerRegistration, auth);
        if (on) {
            handlerRegistration.on();
        } else {
            handlerRegistration.off();
        }
        return handlerRegistration;
    }

    public HandlerRegistration add(Reference reference, Event event, Fn.Arg<XDataSnapshot> fn) {
        return add(new EventRegistration(reference, event, fn), true, true);
    }

    public HandlerRegistration addOff(Reference reference, Event event, Fn.Arg<XDataSnapshot> fn) {
        return add(new EventRegistration(reference, event, fn), true, false);
    }

    public void enableFirebaseEvents() {
        Sets.newHashSet(handlerRegistrations.keySet()).forEach(HandlerRegistration::on);
    }

    public void disableFirebaseEvents() {
        Sets.newHashSet(handlerRegistrations.keySet()).forEach(HandlerRegistration::off);
    }

    public void removeRegistration(HandlerRegistration handlerRegistration) {
        if (handlerRegistration != null) {
            handlerRegistration.off();
            handlerRegistrations.remove(handlerRegistration);
        }
    }

    public void removeFirebaseAuthEvents() {
        Set<HandlerRegistration> authHandlerRegistrations = Maps
                .filterValues(handlerRegistrations, auth -> auth).keySet();
        Sets.newHashSet(authHandlerRegistrations).forEach(this::removeRegistration);
    }

    public void removeAllRegistrations() {
        Sets.newHashSet(handlerRegistrations.keySet()).forEach(this::removeRegistration);
    }
}
