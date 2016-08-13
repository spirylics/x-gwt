package com.github.spirylics.xgwt.firebase;

public enum ErrorCode {
    PERMISSION_DENIED;

    public boolean is(String errorCode) {
        return name().equals(errorCode);
    }
}
