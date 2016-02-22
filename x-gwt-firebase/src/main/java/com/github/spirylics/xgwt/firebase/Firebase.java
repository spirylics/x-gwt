package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.XMapper;
import com.google.common.base.Joiner;
import com.google.gwt.core.client.JavaScriptObject;
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

    public native Firebase child(String path);

    public native Promise<Auth, Error> createUser(Credentials credentials);

    public native <D> Fn.Arg<Snapshot<D>> on(String event, Fn.Arg<Snapshot<D>> fn);

    public native void off();

    public native void off(String event);

    public native <D> void off(String event, Fn.Arg<Snapshot<D>> fn);

    public native void onAuth(Fn.Arg<Auth> fn);

    public native <D> Promise<Void, Error> push(D data);

    public native <D> Promise<Void, Error> set(D data);

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
    public final <D> Fn.Arg<Snapshot<JavaScriptObject>> wrapFn(final Fn.Arg<XSnapshot> fn) {
        return new Fn.Arg<Snapshot<JavaScriptObject>>() {
            @Override
            public void e(Snapshot<JavaScriptObject> data) {
                fn.e(new XSnapshot(data));
            }
        };
    }

    @JsOverlay
    public final <D> Fn.Arg<Snapshot<JavaScriptObject>> xOn(Event event, final Fn.Arg<XSnapshot> fn) {
        return on(event.name(), wrapFn(fn));
    }

    @JsOverlay
    public final <D> Promise<Void, Error> xPush(D data) {
        return push(xMapper.toJso(data));
    }

    @JsOverlay
    public final <D> Promise<Void, Error> xSet(D data) {
        return set(xMapper.toJso(data));
    }


    @JsOverlay
    public final Promise<Auth, Error> xAuthWithPassword(String email, String password) {
        return authWithPassword(Credentials.create(email, password));
    }

    @JsOverlay
    public final Firebase xChild(String... paths) {
        return child(Joiner.on("/").join(paths));
    }

    @JsOverlay
    public final Promise<Auth, Error> xCreateUser(String email, String password) {
        return createUser(Credentials.create(email, password));
    }

}
