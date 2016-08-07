package com.github.spirylics.xgwt.firebase.auth;


public enum Provider {
    FACEBOOK, GOOGLE;

    public AuthProvider makeAuthProvider() {
        AuthProvider authProvider;
        switch (this) {
            case FACEBOOK:
                authProvider = new FacebookAuthProvider();
                break;
            case GOOGLE:
                authProvider = new GoogleAuthProvider();
                break;
            default:
                throw new UnsupportedOperationException("Provider not supported");
        }
        return authProvider;
    }
}
