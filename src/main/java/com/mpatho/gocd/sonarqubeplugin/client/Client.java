package com.mpatho.gocd.sonarqubeplugin.client;

import com.mpatho.gocd.sonarqubeplugin.client.action.Action;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class Client {

    private final HttpClient httpClient;
    private final HttpHost httpHost;

    public Client(HttpClient httpClient, HttpHost httpHost) {
        this.httpClient = httpClient;
        this.httpHost = httpHost;
    }

    public HttpResponse get(Action action) {
        try {
            HttpGet request = new HttpGet(action.getURI());
            return httpClient.execute(httpHost, request);
        } catch (IOException e) {
            throw new ApplicationExceptions(e);
        }
    }

}
