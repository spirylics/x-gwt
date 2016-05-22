package com.github.spirylics.xgwt.firebase.auth;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.firebase.Error;
import com.github.spirylics.xgwt.firebase.Event;
import com.github.spirylics.xgwt.firebase.Promise;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;


@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.auth", name = "Auth")
public class Auth {

    public native Promise<AuthResult, Error> signInWithPopup(AuthProvider authProvider);

    public native Promise<User, Error> signInWithEmailAndPassword(String email, String password);

    public native Promise<User, Error> signInAnonymously();

    public native Promise<User, Error> createUserWithEmailAndPassword(String email, String password);

    @JsProperty
    public native User getCurrentUser();

    public native Fn.NoArg onAuthStateChanged(Fn.Arg<User> fn);

    @JsOverlay
    public final boolean isAuth() {
        return getCurrentUser() != null;
    }

    @JsOverlay
    public final AuthRegistration handleAuth(Event event, final Fn.Arg<User> fn) {
        return new AuthRegistration(this, fn);
    }
}
