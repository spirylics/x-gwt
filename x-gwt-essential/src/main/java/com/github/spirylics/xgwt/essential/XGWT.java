package com.github.spirylics.xgwt.essential;

import com.google.common.collect.Sets;
import com.google.gwt.core.client.GWT;

import java.util.Set;

public class XGWT {

    final static Set<GWT.UncaughtExceptionHandler> UNCAUGHT_EXCEPTION_HANDLERS = Sets.newHashSet();

    static {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable throwable) {
                for (GWT.UncaughtExceptionHandler handler : UNCAUGHT_EXCEPTION_HANDLERS) {
                    handler.onUncaughtException(throwable);
                }
            }
        });
    }

    public static void addUncaughtExceptionHandler(GWT.UncaughtExceptionHandler handler) {
        UNCAUGHT_EXCEPTION_HANDLERS.add(handler);
    }
}
