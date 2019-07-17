package com.mpatho.sonarquiberqualitygate.client.interceptor;

import com.mpatho.sonarquiberqualitygate.client.Client;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ClientFactory {

    public static Client getClient(String hostname, int port, String scheme, String userToken) {
        HttpClient httpClient = HttpClientBuilder.create()
                .addInterceptorFirst(new UserTokenAuthenticationHttpRequestInterceptor(userToken))
                .build();
        HttpHost httpHost = new HttpHost(hostname, port, scheme);
        return new Client(httpClient, httpHost);
    }
}
