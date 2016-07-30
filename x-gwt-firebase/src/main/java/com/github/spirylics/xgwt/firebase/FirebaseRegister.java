package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.firebase.auth.Auth;
import com.github.spirylics.xgwt.firebase.auth.AuthRegistration;
import com.github.spirylics.xgwt.firebase.auth.User;
import com.github.spirylics.xgwt.firebase.database.EventRegistration;
import com.github.spirylics.xgwt.firebase.database.Reference;
import com.github.spirylics.xgwt.firebase.database.XDataSnapshot;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
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
        for (HandlerRegistration handlerRegistration : Sets.newHashSet(handlerRegistrations.keySet())) {
            handlerRegistration.on();
        }
    }

    public void disableFirebaseEvents() {
        for (HandlerRegistration handlerRegistration : Sets.newHashSet(handlerRegistrations.keySet())) {
            handlerRegistration.off();
        }
    }

    public void removeRegistration(HandlerRegistration handlerRegistration) {
        if (handlerRegistration != null) {
            handlerRegistration.off();
            handlerRegistrations.remove(handlerRegistration);
        }
    }

    public void removeFirebaseAuthEvents() {
        Set<HandlerRegistration> authHandlerRegistrations = Maps
                .filterValues(handlerRegistrations, new Predicate<Boolean>() {
                    @Override
                    public boolean apply(@Nullable Boolean auth) {
                        return auth;
                    }
                }).keySet();
        for (HandlerRegistration authHandlerRegistration : Sets.newHashSet(authHandlerRegistrations)) {
            removeRegistration(authHandlerRegistration);
        }
    }

    public void removeAllRegistrations() {
        for (HandlerRegistration authHandlerRegistration : Sets.newHashSet(handlerRegistrations.keySet())) {
            removeRegistration(authHandlerRegistration);
        }
    }
}
