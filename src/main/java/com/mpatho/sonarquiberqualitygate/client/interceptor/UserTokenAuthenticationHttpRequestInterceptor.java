package com.mpatho.sonarquiberqualitygate.client.interceptor;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.util.Base64;

public class UserTokenAuthenticationHttpRequestInterceptor implements HttpRequestInterceptor {
    private final String userToken;

    public UserTokenAuthenticationHttpRequestInterceptor(String userToken) {
        this.userToken = userToken;
    }

    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        String encoding = Base64.getEncoder().encodeToString((userToken + ":").getBytes());
        httpRequest.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
    }
}
