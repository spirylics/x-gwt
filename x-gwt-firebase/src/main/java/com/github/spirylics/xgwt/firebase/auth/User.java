package com.github.spirylics.xgwt.firebase.auth;

import com.google.common.base.Strings;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@SuppressWarnings("ALL")
@JsType(isNative = true, namespace = "firebase", name = "User")
public class User extends UserInfo {

    @JsProperty
    public native boolean getEmailVerified();

    @JsProperty
    public native boolean getIsAnonymous();

    @JsProperty
    public native UserInfo[] getProviderData();

    @JsOverlay
    public final String xGetEmail() {
        if (isInProviderData(getEmail())) {
            return getProviderData()[0].getEmail();
        } else {
            return getEmail();
        }
    }

    @JsOverlay
    public final String xGetDisplayName() {
        if (isInProviderData(getDisplayName())) {
            return getFirstProviderData().getDisplayName();
        } else {
            return getDisplayName();
        }
    }

    @JsOverlay
    public final String xGetProviderId() {
        if (isInProviderData(getProviderId())) {
            return getFirstProviderData().getProviderId();
        } else {
            return getProviderId();
        }
    }

    @JsOverlay
    public final String xGetPhotoURL() {
        if (isInProviderData(getPhotoURL())) {
            return getFirstProviderData().getPhotoURL();
        } else {
            return getPhotoURL();
        }
    }

    @JsOverlay
    final boolean isInProviderData(String value) {
        return Strings.isNullOrEmpty(value) && getProviderData().length > 0;
    }

    @JsOverlay
    final UserInfo getFirstProviderData() {
        return getProviderData()[0];
    }

}
