package com.github.spirylics.xgwt.essential;

import com.google.gwt.logging.client.RemoteLogHandlerBase;
import com.google.gwt.logging.shared.RemoteLoggingServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class XLoggerRemoteHandler extends RemoteLogHandlerBase {

    private AsyncCallback<String> callback;
    private RemoteLoggingServiceAsync service;

    public XLoggerRemoteHandler() {
        this.callback = new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                wireLogger.log(Level.SEVERE, "Remote logging failed: ", caught);
            }

            public void onSuccess(String result) {
                if (result == null) {
                    wireLogger.finest("Remote logging message acknowledged");
                } else {
                    wireLogger.severe("Remote logging failed: " + result);
                }
            }
        };
    }

    public void setService(RemoteLoggingServiceAsync service) {
        this.service = service;
    }

    @Override
    public void publish(LogRecord record) {
        if (isLoggable(record)) {
            service.logOnServer(record, callback);
        }
    }
}