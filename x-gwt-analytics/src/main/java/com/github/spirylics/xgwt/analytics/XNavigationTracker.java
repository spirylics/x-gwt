package com.github.spirylics.xgwt.analytics;


import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;

import javax.inject.Inject;

public class XNavigationTracker implements NavigationHandler {
    final XAnalytics xAnalytics;

    @Inject
    XNavigationTracker(XAnalytics xAnalytics, EventBus eventBus) {
        this.xAnalytics = xAnalytics;
        eventBus.addHandler(NavigationEvent.getType(), this);
    }

    @Override
    public void onNavigation(final NavigationEvent navigationEvent) {
        xAnalytics.sendPlaceRequest(navigationEvent.getRequest());
    }
}
