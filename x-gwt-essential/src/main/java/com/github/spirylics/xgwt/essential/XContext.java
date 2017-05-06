package com.github.spirylics.xgwt.essential;

import com.google.common.base.Strings;

public class XContext {
    private static final XContext CTX = new XContext();

    private String appName;
    private String appVersion;
    private String platform;
    private String version;
    private String model;

    public static XContext get() {
        return CTX;
    }

    private XContext() {
    }

    public String getAppName() {
        return appName;
    }

    public XContext setAppName(String appName) {
        this.appName = Strings.emptyToNull(appName);
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public XContext setAppVersion(String appVersion) {
        this.appVersion = Strings.emptyToNull(appVersion);
        return this;
    }

    public String getPlatform() {
        return platform;
    }

    public XContext setPlatform(String platform) {
        this.platform = Strings.emptyToNull(platform);
        return this;
    }

    public String getVersion() {
        return version;
    }

    public XContext setVersion(String version) {
        this.version = Strings.emptyToNull(version);
        return this;
    }

    public String getModel() {
        return model;
    }

    public XContext setModel(String model) {
        this.model = Strings.emptyToNull(model);
        return this;
    }
}
