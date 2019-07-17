package com.mpatho.sonarquiberqualitygate.plugin;

import java.util.Map;

public class Config {

    private String hostname;
    private String port;
    private String scheme;
    private String userToken;
    private String projectKey;

    public Config(Map config) {
        hostname = getValue(config, QualityGateStatusTask.HOSTNAME);
        port = getValue(config, QualityGateStatusTask.PORT);
        scheme = getValue(config, QualityGateStatusTask.SCHEME);
        userToken = getValue(config, QualityGateStatusTask.USER_TOKEN);
        projectKey = getValue(config, QualityGateStatusTask.PROJECT_KEY);
    }

    public String getHostname() {
        return hostname;
    }

    public String getPort() {
        return port;
    }

    public String getScheme() {
        return scheme;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getProjectKey() {
        return projectKey;
    }

    private String getValue(Map config, String property) {
        return (String) ((Map) config.get(property)).get("value");
    }
}
