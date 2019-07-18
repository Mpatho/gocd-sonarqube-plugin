package com.mpatho.gocd.sonarqubeplugin.plugin;

import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoApiResponse;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import org.apache.commons.io.IOUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Extension
public class QualityGateStatusTask implements GoPlugin {

    public static final String SERVER_URI = "ServerUri";
    public static final String USER_TOKEN = "UserToken";
    public static final String PROJECT_KEY = "ProjectKey";

    @Override
    public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest request) throws UnhandledRequestTypeException {
        if ("configuration".equals(request.requestName())) {
            return handleGetConfigRequest();
        } else if ("validate".equals(request.requestName())) {
            return handleValidation(request);
        } else if ("execute".equals(request.requestName())) {
            return handleTaskExecution(request);
        } else if ("view".equals(request.requestName())) {
            return handleTaskView();
        }
        throw new UnhandledRequestTypeException(request.requestName());
    }


    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return new GoPluginIdentifier("task", Arrays.asList("1.0"));
    }


    private GoPluginApiResponse handleTaskView() {
        int responseCode = DefaultGoApiResponse.SUCCESS_RESPONSE_CODE;
        HashMap view = new HashMap();
        view.put("displayValue", "SonarQube Quality Gates status");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8"));
        } catch (Exception e) {
            responseCode = DefaultGoApiResponse.INTERNAL_ERROR;
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
        }
        return createResponse(responseCode, view);
    }

    private GoPluginApiResponse handleTaskExecution(GoPluginApiRequest request) {
        Executor executor = new Executor();
        Map executionRequest = (Map) new GsonBuilder().create().fromJson(request.requestBody(), Object.class);
        Map config = (Map) executionRequest.get("config");
        Map context = (Map) executionRequest.get("context");
        Result result = executor.execute(new Config(config), new Context(context), JobConsoleLogger.getConsoleLogger());
        return createResponse(result.responseCode(), result.toMap());
    }

    private GoPluginApiResponse handleValidation(GoPluginApiRequest request) {
        HashMap validationResult = new HashMap();
        int responseCode = DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE;
        Map configMap = (Map) new GsonBuilder().create().fromJson(request.requestBody(), Object.class);
        HashMap errorMap = new HashMap();
        if (isEmpty(configMap, SERVER_URI)) {
            errorMap.put(SERVER_URI, "Server URI cannot be empty");
        }
        if (isEmpty(configMap, USER_TOKEN)) {
            errorMap.put(USER_TOKEN, "User token cannot be empty");
        }
        if (isEmpty(configMap, PROJECT_KEY)) {
            errorMap.put(PROJECT_KEY, "Project key cannot be empty");
        }
        validationResult.put("errors", errorMap);
        return createResponse(responseCode, validationResult);
    }

    private boolean isEmpty(Map configMap, String fieldName) {
        Map field = (Map) configMap.get(fieldName);
        return field == null || field.get("value") == null || ((String) field.get("value")).trim().isEmpty();
    }

    private GoPluginApiResponse handleGetConfigRequest() {
        HashMap config = new HashMap();
        addField(config, SERVER_URI, new HashMap(), "Server Uri", "0", true);
        addField(config, USER_TOKEN, new HashMap(), "User Token", "1", true);
        addField(config, PROJECT_KEY, new HashMap(), "Project Key", "2", true);
        return createResponse(DefaultGoPluginApiResponse.SUCCESS_RESPONSE_CODE, config);
    }

    private void addField(HashMap<String, Map<String, Object>> config, String key, HashMap<String, Object> values, String name, String order, boolean required) {
        values.put("display-order", order);
        values.put("display-name", name);
        values.put("required", required);
        config.put(key, values);
    }

    private GoPluginApiResponse createResponse(int responseCode, Map body) {
        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(new GsonBuilder().serializeNulls().create().toJson(body));
        return response;
    }
}
