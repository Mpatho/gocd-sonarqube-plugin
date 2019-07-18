package com.mpatho.gocd.sonarqubeplugin.client;

import com.mpatho.gocd.sonarqubeplugin.client.interceptor.UserTokenAuthenticationHttpRequestInterceptor;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ClientFactory {

    public static Client getClient(String uri, String userToken) {
        HttpClient httpClient = HttpClientBuilder.create()
                .addInterceptorFirst(new UserTokenAuthenticationHttpRequestInterceptor(userToken))
                .build();
        HttpHost httpHost = HttpHost.create(uri);
        return new Client(httpClient, httpHost);
    }
}
