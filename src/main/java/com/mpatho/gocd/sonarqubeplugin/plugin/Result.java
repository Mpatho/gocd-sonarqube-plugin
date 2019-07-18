package com.mpatho.gocd.sonarqubeplugin.plugin;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private final boolean success;
    private final String message;
    private final int responseCode;

    public Result(boolean success, String message, int responseCode) {
        this.success = success;
        this.message = message;
        this.responseCode = responseCode;
    }

    public Map toMap() {
        final HashMap result = new HashMap();
        result.put("success", success);
        result.put("message", message);
        return result;
    }

    public int responseCode() {
        return responseCode;
    }
}
