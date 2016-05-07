package com.github.spirylics.xgwt.firebase;

public interface HandlerRegistration {
    <R extends HandlerRegistration> R on();

    <R extends HandlerRegistration> R off();
}
