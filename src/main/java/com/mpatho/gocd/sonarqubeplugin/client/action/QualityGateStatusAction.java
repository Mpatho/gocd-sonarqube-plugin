package com.mpatho.gocd.sonarqubeplugin.client.action;

public class QualityGateStatusAction implements Action {
    private static final String ACTION = "/api/qualitygates/project_status";
    private final String projectKey;

    public QualityGateStatusAction(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getURI() {
        return String.format("%s?projectKey=%s", ACTION, projectKey);
    }

}
