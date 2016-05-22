package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.firebase.auth.Auth;
import com.github.spirylics.xgwt.firebase.database.Database;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.app", name = "App")
public class Firebase {

    @JsMethod(namespace = "firebase")
    public static native Firebase initializeApp(Config config);

    public native Auth auth();

    public native Database database();
}
