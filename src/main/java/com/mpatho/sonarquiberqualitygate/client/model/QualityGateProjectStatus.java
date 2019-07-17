package com.mpatho.sonarquiberqualitygate.client.model;

public class QualityGateProjectStatus {
    private ProjectStatus projectStatus;

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Override
    public String toString() {
        return "QualityGateProjectStatus{" +
                "projectStatus=" + projectStatus +
                '}';
    }
}
