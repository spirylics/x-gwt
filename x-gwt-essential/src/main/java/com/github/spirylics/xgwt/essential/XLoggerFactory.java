package com.github.spirylics.xgwt.essential;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.logging.shared.RemoteLoggingService;
import com.google.gwt.logging.shared.RemoteLoggingServiceAsync;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class XLoggerFactory {

    @Named("remoteLoggingPath")
    @Inject
    static String remoteLoggingPath;

    private static Handler remoteHandler;

    public static XLogger getLogger(Class<?> clazz) {
        if (remoteHandler == null && !Strings.isNullOrEmpty(remoteLoggingPath)) {
            remoteHandler = makeRemoteLogHandler(remoteLoggingPath);
        }
        return new XLogger(Logger.getLogger(clazz.getName())).addHandlerIfNotNull(remoteHandler);
    }

    public static Handler makeRemoteLogHandler(String remoteLoggingPath) {
        RemoteLoggingServiceAsync service = GWT.create(RemoteLoggingService.class);
        ((ServiceDefTarget) service).setServiceEntryPoint(remoteLoggingPath);
        Handler handler = GWT.create(XLoggerRemoteHandler.class);
        if (handler instanceof XLoggerRemoteHandler) {
            ((XLoggerRemoteHandler) handler).setService(service);
        }
        return handler;
    }
}
