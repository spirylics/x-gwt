package com.github.spirylics.xgwt.cordova;

import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;

public class GeolocationCancelableCallback implements GeolocationCallback {
    boolean canceled;

    boolean isCanceled() {
        return canceled;
    }

    void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public GeolocationCancelableCallback cancel() {
        setCanceled(true);
        return this;
    }


    @Override
    public void onSuccess(Position position) {
        if (!isCanceled()) {
            onCancelableSuccess(position);
        }
    }

    @Override
    public void onFailure(PositionError error) {
        if (!isCanceled()) {
            onCancelableFailure(error);
        }
    }

    public void onCancelableSuccess(Position position) {

    }

    public void onCancelableFailure(PositionError error) {

    }
}
