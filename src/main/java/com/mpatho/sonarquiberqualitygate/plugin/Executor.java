package com.mpatho.sonarquiberqualitygate.plugin;

import com.google.gson.GsonBuilder;
import com.mpatho.sonarquiberqualitygate.client.Client;
import com.mpatho.sonarquiberqualitygate.client.action.QualityGateStatusAction;
import com.mpatho.sonarquiberqualitygate.client.interceptor.ClientFactory;
import com.thoughtworks.go.plugin.api.response.DefaultGoApiResponse;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class Executor {

    public Result execute(Config config, Context context, JobConsoleLogger console) {
        try {
            return runCommand(context, config, console);
        } catch (Exception e) {
            return new Result(false, e.getMessage(), DefaultGoApiResponse.SUCCESS_RESPONSE_CODE);
        }
    }

    private Result runCommand(Context context, Config config, JobConsoleLogger console) throws IOException {
        console.printLine("Server URI : " + config.getServerUri());
        console.printLine("Project key : " + config.getProjectKey());
        Client client = ClientFactory.getClient(config.getServerUri(), config.getUserToken());
        HttpResponse httpResponse = client.get(new QualityGateStatusAction(config.getProjectKey()));
        switch (httpResponse.getStatusLine().getStatusCode()) {
            case HttpStatus.SC_OK:
                String status = getStatus(httpResponse);
                return new Result("OK".equals(status), status, HttpStatus.SC_OK);
            case HttpStatus.SC_BAD_REQUEST:
                return new Result(false, "Bad request", HttpStatus.SC_BAD_REQUEST);
            default:
                return new Result(false, "Cannot understand the Response", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getStatus(HttpResponse httpResponse) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        httpResponse.getEntity().writeTo(out);
        String responseString = out.toString();
        Map<String, Map> map = (Map) new GsonBuilder().create().fromJson(responseString, Object.class);
        return (String) map.get("projectStatus").get("status");
    }
}
