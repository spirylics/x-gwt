package com.github.spirylics.xgwt.firebase.auth;


public enum Scope {

    EMAIL("email"),
    GOOGLE_PROFILE("profile"),
    FACEBOOK_PROFILE("public_profile");


    private String scope;

    Scope(String scope) {
        this.scope = scope;

    }

    public String getScope() {
        return scope;
    }
}
