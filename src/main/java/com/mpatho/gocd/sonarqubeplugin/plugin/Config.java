package com.mpatho.gocd.sonarqubeplugin.plugin;

import java.util.Map;

public class Config {

    private String serverUri;
    private String userToken;
    private String projectKey;

    public Config(Map config) {
        serverUri = getValue(config, QualityGateStatusTask.SERVER_URI);
        userToken = getValue(config, QualityGateStatusTask.USER_TOKEN);
        projectKey = getValue(config, QualityGateStatusTask.PROJECT_KEY);
    }

    public String getServerUri() {
        return serverUri;
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
