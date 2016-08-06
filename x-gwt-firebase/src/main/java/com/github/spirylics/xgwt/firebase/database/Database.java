package com.github.spirylics.xgwt.firebase.database;

import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.database", name = "Database")
public interface Database {

    Reference ref();

    Reference ref(String path);

}
