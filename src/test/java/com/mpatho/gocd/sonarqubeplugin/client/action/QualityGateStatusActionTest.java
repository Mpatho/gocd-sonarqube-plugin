package com.mpatho.gocd.sonarqubeplugin.client.action;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class QualityGateStatusActionTest {

    @Test
    public void getURI() {
        QualityGateStatusAction action = new QualityGateStatusAction("com.mpatho:finance");

        String result = action.getURI();

        assertThat(result, is("/api/qualitygates/project_status?projectKey=com.mpatho:finance"));
    }
}
