package com.github.spirylics.xgwt.firebase.auth;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase.auth", name = "ActionCodeInfo")
public interface ActionCodeInfo {

    @JsProperty
    ActionCodeInfoData getData();

    @JsProperty
    void setData(ActionCodeInfoData data);

}
