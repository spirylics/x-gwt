package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class FirebaseRegister {

    private final Map<HandlerRegistration, Boolean> handlerRegistrations = Maps.newHashMap();
    private final Firebase firebase;

    public FirebaseRegister(Firebase firebase) {
        this.firebase = firebase;
    }

    public HandlerRegistration add(Firebase firebase, Event event, Fn.Arg<XSnapshot> fn, boolean auth, boolean on) {
        return add(new EventRegistration(firebase, event, fn), auth, on);
    }

    public HandlerRegistration addAuth(Fn.Arg<Auth> fn, boolean on) {
        return add(new AuthRegistration(firebase, fn), false, on);
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

    public void removeFirebaseAuthEvents() {
        Set<HandlerRegistration> authHandlerRegistrations = Maps
                .filterValues(handlerRegistrations, new Predicate<Boolean>() {
                    @Override
                    public boolean apply(@Nullable Boolean auth) {
                        return auth;
                    }
                }).keySet();
        for (HandlerRegistration authHandlerRegistration : Sets.newHashSet(authHandlerRegistrations)) {
            authHandlerRegistration.off();
            handlerRegistrations.remove(authHandlerRegistration);
        }
    }
}
