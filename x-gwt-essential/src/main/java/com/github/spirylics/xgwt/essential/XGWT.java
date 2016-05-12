package com.github.spirylics.xgwt.essential;

import com.google.common.collect.Sets;
import com.google.gwt.core.client.GWT;

import java.util.Set;

public class XGWT {

    private static Set<GWT.UncaughtExceptionHandler> UNCAUGHT_EXCEPTION_HANDLERS;

    private XGWT() {
    }

    public static void addUncaughtExceptionHandler(GWT.UncaughtExceptionHandler handler) {
        if (UNCAUGHT_EXCEPTION_HANDLERS == null) {
            UNCAUGHT_EXCEPTION_HANDLERS = Sets.newHashSet();
            GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
                @Override
                public void onUncaughtException(Throwable throwable) {
                    for (GWT.UncaughtExceptionHandler handler : UNCAUGHT_EXCEPTION_HANDLERS) {
                        handler.onUncaughtException(throwable);
                    }
                }
            });
        }
        UNCAUGHT_EXCEPTION_HANDLERS.add(handler);
    }
}
