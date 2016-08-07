package com.github.spirylics.xgwt.firebase.auth;


import com.google.common.collect.ImmutableMap;

import java.util.Map;

public interface Scope {

    Map<Generic, Facebook> GENERIC_FACEBOOK_SCOPE = new ImmutableMap.Builder<Scope.Generic, Scope.Facebook>()
            .put(Scope.Generic.email, Scope.Facebook.email)
            .put(Scope.Generic.profile, Scope.Facebook.public_profile)
            .build();

    Map<Scope.Generic, Scope.Google> GENERIC_GOOGLE_SCOPE = new ImmutableMap.Builder<Scope.Generic, Scope.Google>()
            .put(Scope.Generic.email, Scope.Google.email)
            .put(Scope.Generic.profile, Scope.Google.profile)
            .build();

    enum Generic {
        email, profile;

        Facebook getFacebookScope() {
            return GENERIC_FACEBOOK_SCOPE.get(this);
        }

        Google getGoogleScope() {
            return GENERIC_GOOGLE_SCOPE.get(this);
        }
    }

    enum Google {
        email, profile;
    }

    enum Facebook {
        email, public_profile;
    }
}
