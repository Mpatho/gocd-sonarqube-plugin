package com.mpatho.sonarquiberqualitygate.client;

import com.mpatho.sonarquiberqualitygate.client.action.Action;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    @InjectMocks
    private Client client;

    @Mock
    private HttpClient httpClient;

    @Test
    public void shouldCallHttpExecuteWithHttpUriRequestHavingSameURIAsAction() throws IOException {
        Action action = mock(Action.class);

        when(action.getURI()).thenReturn("/qualitygates/project_status?projectKey=com.psybergate:pg-swift-core");

        client.get(action);

        ArgumentCaptor<HttpUriRequest> argumentCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(any(HttpHost.class), argumentCaptor.capture());
        HttpUriRequest httpUriRequest = argumentCaptor.getValue();
        assertThat(httpUriRequest.getURI().toString(), is("/qualitygates/project_status?projectKey=com.psybergate:pg-swift-core"));
    }

    @Test
    public void shouldReturnSameHttpResponseAsHttpClientExecute() throws IOException {
        HttpResponse expected = mock(HttpResponse.class);
        Action action = mock(Action.class);

        when(httpClient.execute(any(HttpHost.class), any(HttpUriRequest.class))).thenReturn(expected);
        when(action.getURI()).thenReturn("/qualitygates/");

        HttpResponse result = client.get(action);

        assertThat(result, is(expected));
    }
}
