package com.mpatho.sonarquiberqualitygate.client.interceptor;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Base64;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserTokenAuthenticationHttpRequestInterceptorTest {

    @Test
    public void process() {
        String userToken = "bla bla";
        String encoding = Base64.getEncoder().encodeToString((userToken + ":").getBytes());
        HttpRequest request = mock(HttpRequest.class);
        HttpContext context = mock(HttpContext.class);

        UserTokenAuthenticationHttpRequestInterceptor interceptor = new UserTokenAuthenticationHttpRequestInterceptor(userToken);

        interceptor.process(request, context);

        verify(request).setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
    }
}
