package com.mpatho.sonarquiberqualitygate.client.model;

public class ProjectStatus {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProjectStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
