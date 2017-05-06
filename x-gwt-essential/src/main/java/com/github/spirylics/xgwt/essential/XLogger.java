package com.github.spirylics.xgwt.essential;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.logging.client.NullLogHandler;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XLogger implements GWT.UncaughtExceptionHandler {
    private final static Joiner JOINER = Joiner.on(": ").skipNulls();

    final Logger logger;

    public XLogger(Logger logger) {
        this.logger = logger;
    }

    public XLogger logUncaughtException() {
        XGWT.addUncaughtExceptionHandler(this);
        return this;
    }

    public XLogger addHandlerIfNotNull(Handler h) {
        if (h != null && !(h instanceof NullLogHandler)) {
            logger.addHandler(h);
        }
        return this;
    }

    private String formatMessage(String format, Object... args) {
        String msg = format;
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("{" + i + "}", args[i] instanceof Object[]
                    ? Arrays.toString((Object[]) args[i])
                    : String.valueOf(args[i]));
        }
        return JOINER.join(XContext.get().getPlatform(), XContext.get().getVersion(), XContext.get().getModel(), XContext.get().getAppName(), XContext.get().getAppVersion(), msg);
    }

    private void log(Level level, String format, Throwable t, Object... args) {
        if (logger.isLoggable(level)) {
            logger.log(level, formatMessage(format, args), t);
        }
    }

    private void log(Level level, String format, Object... args) {
        if (logger.isLoggable(level)) {
            logger.log(level, formatMessage(format, args));
        }
    }

    public void severe(String format, Throwable t, Object... args) {
        log(Level.SEVERE, format, t, args);
    }

    public void severe(String format, Object... args) {
        log(Level.SEVERE, format, args);
    }

    public void warning(String format, Object... args) {
        log(Level.WARNING, format, args);
    }

    public void warning(String format, Throwable t, Object... args) {
        log(Level.WARNING, format, t, args);
    }

    public void info(String format, Object... args) {
        log(Level.INFO, format, args);
    }

    public void fine(String format, Object... args) {
        log(Level.FINE, format, args);
    }

    public void finer(String format, Object... args) {
        log(Level.FINER, format, args);
    }

    public void finest(String format, Object... args) {
        log(Level.FINEST, format, args);
    }

    @Override
    public void onUncaughtException(Throwable e) {
        severe("Uncaught exception", e);
    }
}
