package com.github.spirylics.xgwt.firebase.database;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.Error;
import com.github.spirylics.xgwt.essential.Promise;
import com.github.spirylics.xgwt.essential.XMapper;
import com.github.spirylics.xgwt.firebase.Event;
import com.google.common.base.Joiner;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.database", name = "Reference")
public class Reference {

    public native Reference child(String path);

    public native Reference equalTo(String value);

    public native Reference equalTo(String value, String key);

    @JsProperty
    public native String getKey();

    public native <D> Fn.Arg<DataSnapshot<D>> on(String event, Fn.Arg<DataSnapshot<D>> fn);

    public native <D> void once(String event, Fn.Arg<DataSnapshot<D>> succes, Fn.Arg<Error> error);

    public native <D> Promise<DataSnapshot<D>, Error> once(String event);

    public native void off();

    public native void off(String event);

    public native void off(String event, Fn.Arg<?> fn);

    public native Reference orderByChild(String path);

    public native Reference orderByKey();

    public native Reference orderByPriority();

    public native Reference orderByValue();

    public native Reference startAt(Object value, String key);

    public native Reference endAt(Object value, String key);

    public native Reference equalTo(Object value, String key);

    public native Reference push();

    public native <D> Promise<Void, Error> push(D data);

    public native Promise<Void, Error> remove();

    public native <D> Promise<Void, Error> set(D data);

    public native void unauth();

    @Override
    public native String toString();

    @JsOverlay
    public final EventRegistration handleEvent(Event event, final Fn.Arg<XDataSnapshot> fn) {
        return new EventRegistration(this, event, fn);
    }

    @JsOverlay
    public final <D> Fn.Arg<DataSnapshot<D>> wrapFn(final Fn.Arg<XDataSnapshot> fn) {
        return new Fn.Arg<DataSnapshot<D>>() {
            @Override
            public void e(DataSnapshot<D> data) {
                fn.e(new XDataSnapshot(data));
            }
        };
    }

    @JsOverlay
    public final EventRegistration xOn(Event event, final Fn.Arg<XDataSnapshot> fn) {
        return new EventRegistration(this, event, fn).on();
    }

    @JsOverlay
    public final void xOnce(final Event event, Fn.Arg<XDataSnapshot> sucess, Fn.Arg<Error> error) {
        once(event.name(), wrapFn(sucess), error);
    }

    @JsOverlay
    public final Promise<XDataSnapshot, Error> xOnce(final Event event) {
        return once(event.name()).then(new Fn.ArgRet<DataSnapshot<Object>, XDataSnapshot>() {
            @Override
            public XDataSnapshot e(DataSnapshot<Object> dataSnapshot) {
                return new XDataSnapshot(dataSnapshot);
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
        return push(XMapper.get().jsConvert(data));
    }

    @JsOverlay
    public final <D> Promise<Void, Error> xSet(D data) {
        return set(XMapper.get().jsConvert(data));
    }

    @JsOverlay
    public final Reference xChild(String... paths) {
        return child(Joiner.on("/").join(paths));
    }
}
