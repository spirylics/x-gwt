package com.github.spirylics.xgwt.server;

import com.google.gwt.logging.server.RemoteLoggingServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class XRemoteLoggingService extends RemoteLoggingServiceImpl {
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        final String symbolMapsDirectory = config.getInitParameter("symbolMapsDirectory");
        setSymbolMapsDirectory(config.getServletContext().getRealPath(symbolMapsDirectory));
    }
}
