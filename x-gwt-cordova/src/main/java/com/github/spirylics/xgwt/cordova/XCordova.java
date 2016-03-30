package com.github.spirylics.xgwt.cordova;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.deferred.PromiseFunction;
import com.google.gwt.user.client.Window;
import com.googlecode.gwtphonegap.client.*;
import com.googlecode.gwtphonegap.client.device.Device;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationOptions;

public class XCordova {
    final PhoneGap phoneGap;
    final GeolocationOptions geolocationOptions;
    GeolocationCancelableCallback singleGeolocationCallback;
    boolean networkIndicationShow;

    public XCordova() {
        this((PhoneGap) GWT.create(PhoneGap.class));
    }

    public XCordova(PhoneGap phoneGap) {
        this.phoneGap = phoneGap;
        this.geolocationOptions = new GeolocationOptions();
        this.geolocationOptions.setTimeout(5000);
        this.geolocationOptions.setEnableHighAccuracy(true);
        this.geolocationOptions.setMaximumAge(60000);
    }

    public XCordova initialize(final Promise.Deferred deferred) {
        phoneGap.addHandler(new PhoneGapAvailableHandler() {
            @Override
            public void onPhoneGapAvailable(PhoneGapAvailableEvent phoneGapAvailableEvent) {
                deferred.resolve();
            }
        });
        phoneGap.addHandler(new PhoneGapTimeoutHandler() {
            @Override
            public void onPhoneGapTimeout(PhoneGapTimeoutEvent phoneGapTimeoutEvent) {
                deferred.reject();
            }
        });
        phoneGap.initializePhoneGap();
        return this;
    }

    public Promise initialize() {
        return new PromiseFunction() {
            @Override
            public void f(Deferred deferred) {
                initialize(deferred);
            }
        };
    }

    public Device getDevice() {
        return phoneGap.getDevice();
    }

    public Promise getDevicePromise() {
        return initialize().then(new Function() {
            @Override
            public Object f(Object... args) {
                return phoneGap.getDevice();
            }
        });
    }

    public XCordova hideSplashScreen() {
        phoneGap.getSplashScreen().hide();
        return this;
    }

    public XCordova openKeyboard() {
        JsUtils.jsni("cordova.plugins.Keyboard.show");
        return this;
    }

    public XCordova dismissKeyboard() {
        JsUtils.jsni("cordova.plugins.Keyboard.close");
        return this;
    }

    public XCordova showNetworkIndicator(boolean show) {
        if (this.networkIndicationShow != show) {
            JsUtils.jsni(show ? "NetworkActivityIndicator.show" : "NetworkActivityIndicator.hide");
            this.networkIndicationShow = show;
        }
        return this;
    }

    public void cancelSingleGeolocation() {
        if (singleGeolocationCallback != null) {
            singleGeolocationCallback.cancel();
            singleGeolocationCallback = null;
        }
    }

    public XCordova getSingleCurrentPosition(GeolocationCancelableCallback callback) {
        cancelSingleGeolocation();
        this.singleGeolocationCallback = callback;
        getCurrentPosition(callback);
        return this;
    }

    public XCordova getCurrentPosition(GeolocationCallback callback) {
        phoneGap.getGeolocation().getCurrentPosition(callback, geolocationOptions);
        return this;
    }

    public boolean isNative() {
        return phoneGap.isPhoneGapDevice();
    }

    public boolean isDesktop() {
        return !Window.Navigator.getUserAgent().matches(".*(iPhone|iPod|iPad|Android|BlackBerry|IEMobile).*");
    }

    public native boolean isTactile()  /*-{
        return !!('ontouchstart' in $wnd) || !!('onmsgesturechange' in $wnd);
    }-*/;
}
