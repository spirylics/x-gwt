package com.github.spirylics.xgwt.hello;


import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface Credential {

    @JsProperty
    String getAccess_token();

    @JsProperty
    void setAccess_token(String access_token);

    @JsProperty
    String getExpires();

    @JsProperty
    void setExpires(String expires);
}
