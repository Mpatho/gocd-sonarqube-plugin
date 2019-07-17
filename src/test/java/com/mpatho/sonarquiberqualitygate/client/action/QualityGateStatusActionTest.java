package com.mpatho.sonarquiberqualitygate.client.action;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class QualityGateStatusActionTest {

    @Test
    public void getURL() {
        QualityGateStatusAction action = new QualityGateStatusAction("com.psybergate:pg-swift-core");

        String result = action.getURI();

        assertThat(result, is("/api/qualitygates/project_status?projectKey=com.psybergate:pg-swift-core"));
    }
}
