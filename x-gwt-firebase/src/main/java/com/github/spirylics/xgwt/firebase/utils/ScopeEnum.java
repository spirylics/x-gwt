package com.github.spirylics.xgwt.firebase.utils;


public enum ScopeEnum {

    EMAIL("email"),
    PROFILE("profile");

    private String scope;

    ScopeEnum(String scope){
        this.scope = scope;
        
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}