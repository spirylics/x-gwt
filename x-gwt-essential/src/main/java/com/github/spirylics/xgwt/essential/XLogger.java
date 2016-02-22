package com.github.spirylics.xgwt.essential;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.gwt.logging.client.NullLogHandler;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XLogger {
    public final Logger logger;
    final String appName;
    final String appVersion;
    final Joiner joiner = Joiner.on(": ").skipNulls();
    String platform;
    String version;
    String model;

    public XLogger(Logger logger, String appName, String appVersion) {
        this.logger = logger;
        this.appName = appName;
        this.appVersion = appVersion;
    }


    public void addHandlerIfNotNull(Handler h) {
        if (!(h instanceof NullLogHandler)) {
            logger.addHandler(h);
        }
    }

    public XLogger setPlatform(String platform) {
        this.platform = Strings.emptyToNull(platform);
        return this;
    }

    public XLogger setVersion(String version) {
        this.version = Strings.emptyToNull(version);
        return this;
    }

    public XLogger setModel(String model) {
        this.model = Strings.emptyToNull(model);
        return this;
    }

    private String formatMessage(String msg) {
        return joiner.join(platform, version, model, appName, appVersion, msg);
    }

    private String formatMessage(String format, Object... args) {
        String msg = format;
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("{" + i + "}", args[i] instanceof Object[]
                    ? Arrays.toString((Object[]) args[i])
                    : String.valueOf(args[i]));
        }
        return formatMessage(msg);
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
}
