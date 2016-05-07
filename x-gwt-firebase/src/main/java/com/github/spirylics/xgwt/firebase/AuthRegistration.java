package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;

public class AuthRegistration implements HandlerRegistration {
    final Firebase ref;
    final Fn.Arg<Auth> fn;

    public AuthRegistration(Firebase ref, Fn.Arg<Auth> fn) {
        this.ref = ref;
        this.fn = fn;
    }

    public Firebase getRef() {
        return ref;
    }

    public Fn.Arg<Auth> getFn() {
        return fn;
    }

    @Override
    public AuthRegistration on() {
        getRef().onAuth(getFn());
        return this;
    }

    @Override
    public AuthRegistration off() {
        getRef().offAuth(getFn());
        return this;
    }

}
