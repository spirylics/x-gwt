package com.github.spirylics.xgwt.firebase.auth;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.firebase.HandlerRegistration;

public class AuthRegistration implements HandlerRegistration {
    final Auth auth;
    final Fn.Arg<User> fn;
    Fn.NoArg unsubscribe;

    public AuthRegistration(Auth auth, Fn.Arg<User> fn) {
        this.auth = auth;
        this.fn = fn;
    }

    public Auth getAuth() {
        return auth;
    }

    public Fn.Arg<User> getFn() {
        return fn;
    }

    @Override
    public AuthRegistration on() {
        this.unsubscribe = getAuth().onAuthStateChanged(getFn());
        return this;
    }

    @Override
    public AuthRegistration off() {
        if (unsubscribe != null) {
            unsubscribe.e();
        }
        return this;
    }

}
