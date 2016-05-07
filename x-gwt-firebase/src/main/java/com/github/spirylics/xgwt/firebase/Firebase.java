package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.XMapper;
import com.google.common.base.Joiner;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Firebase {

    private static XMapper xMapper;
    private Auth auth;

    @JsConstructor
    private Firebase(String location) {
    }

    @JsOverlay
    public static final Firebase create(String location, XMapper xMapper) {
        Firebase.xMapper = xMapper;
        XSnapshot.xMapper = xMapper;
        final Firebase firebase = new Firebase(location);
        firebase.onAuth(new Fn.Arg<Auth>() {
            @Override
            public void e(Auth auth) {
                firebase.setAuth(auth);
            }
        });
        return firebase;
    }

    public native Promise<Auth, Error> authWithOAuthPopup(String provider);

    public native Promise<Auth, Error> authWithPassword(Credentials credentials);

    public native Promise<Auth, Error> authAnonymously();

    public native Firebase child(String path);

    public native Promise<Auth, Error> createUser(Credentials credentials);

    public native String key();

    public native <D> Fn.Arg<Snapshot<D>> on(String event, Fn.Arg<Snapshot<D>> fn);

    public native <D> void once(String event, Fn.Arg<Snapshot<D>> succes, Fn.Arg<Error> error);

    public native <D> Promise<Snapshot<D>, Error> once(String event);

    public native void off();

    public native void off(String event);

    public native void off(String event, Fn.Arg<?> fn);

    public native void onAuth(Fn.Arg<Auth> fn);

    public native void offAuth(Fn.Arg<Auth> fn);

    public native Firebase push();

    public native <D> Promise<Void, Error> push(D data);

    public native Promise<Void, Error> remove();

    public native <D> Promise<Void, Error> set(D data);

    @Override
    public native String toString();

    @JsOverlay
    public final AuthRegistration handleAuth(Event event, final Fn.Arg<Auth> fn) {
        return new AuthRegistration(this, fn);
    }

    @JsOverlay
    public final EventRegistration handleEvent(Event event, final Fn.Arg<XSnapshot> fn) {
        return new EventRegistration(this, event, fn);
    }

    @JsOverlay
    public final boolean isAuth() {
        return getAuth() != null;
    }

    @JsOverlay
    public final Auth getAuth() {
        return auth;
    }

    @JsOverlay
    final Firebase setAuth(Auth auth) {
        this.auth = auth;
        return this;
    }

    @JsOverlay
    public final <D> Fn.Arg<Snapshot<D>> wrapFn(final Fn.Arg<XSnapshot> fn) {
        return new Fn.Arg<Snapshot<D>>() {
            @Override
            public void e(Snapshot<D> data) {
                fn.e(new XSnapshot(data));
            }
        };
    }

    @JsOverlay
    public final EventRegistration xOn(Event event, final Fn.Arg<XSnapshot> fn) {
        return new EventRegistration(this, event, fn).on();
    }

    @JsOverlay
    public final void xOnce(final Event event, Fn.Arg<XSnapshot> sucess, Fn.Arg<Error> error) {
        once(event.name(), wrapFn(sucess), error);
    }

    @JsOverlay
    public final Promise<XSnapshot, Error> xOnce(final Event event) {
        return once(event.name()).then(new Fn.ArgRet<Snapshot<Object>, XSnapshot>() {
            @Override
            public XSnapshot e(Snapshot<Object> snapshot) {
                return new XSnapshot(snapshot);
            }
        });
    }

    @JsOverlay
    public final void xOff(final Event event) {
        off(event.name());
    }

    @JsOverlay
    public final void xOff(final Event event, Fn.Arg<?> fn) {
        off(event.name(), fn);
    }

    @JsOverlay
    public final <D> Promise<Void, Error> xPush(D data) {
        return push(xMapper.jsConvert(data));
    }

    @JsOverlay
    public final <D> Promise<Void, Error> xSet(D data) {
        return set(xMapper.jsConvert(data));
    }


    @JsOverlay
    public final Promise<Auth, Error> xAuthWithPassword(String email, String password) {
        return authWithPassword(Credentials.create(email, password));
    }

    @JsOverlay
    public final Firebase xChild(String... paths) {
        return child(Joiner.on("/").join(paths)).setAuth(getAuth());
    }

    @JsOverlay
    public final Promise<Auth, Error> xCreateUser(String email, String password) {
        return createUser(Credentials.create(email, password));
    }

}
