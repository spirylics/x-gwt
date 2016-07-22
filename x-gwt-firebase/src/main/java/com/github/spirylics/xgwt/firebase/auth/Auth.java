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

    public native Promise<Void, Error> applyActionCode(String code);

    public native Promise<ActionCodeInfo, Error> checkActionCode(String code);

    public native Promise<Void, Error> confirmPasswordReset(String code, String newPassword);

    public native Promise<AuthResult, Error> signInWithPopup(AuthProvider authProvider);

    public native Promise<User, Error> signInWithEmailAndPassword(String email, String password);

    public native Promise<User, Error> signInAnonymously();

    public native Promise<Void, Error> signOut();

    public native Promise<User, Error> createUserWithEmailAndPassword(String email, String password);

    public native Promise<String[], Error> fetchProvidersForEmail(String email);

    public native Promise<Void, Error> sendPasswordResetEmail(String email);

    public native Promise<User, Error> signInWithCredential(AuthCredential authCredential);

    public native Promise<User, Error> signInWithCustomToken(String token);

    public native Promise<Void, Error> signInWithRedirect(AuthProvider authProvider);

    public native Promise<String, Error> verifyPasswordResetCode(String code);


    @JsProperty
    public native User getCurrentUser();

    public native Fn.NoArg onAuthStateChanged(Fn.Arg<User> fn);

    @JsOverlay
    public final boolean isAuth() {
        return getCurrentUser() != null;
    }

    @JsOverlay
    public final AuthRegistration handleAuth(final Fn.Arg<User> fn) {
        return new AuthRegistration(this, fn);
    }
}
