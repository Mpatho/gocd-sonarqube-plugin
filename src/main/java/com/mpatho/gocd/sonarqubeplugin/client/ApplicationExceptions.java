package com.mpatho.gocd.sonarqubeplugin.client;

import java.io.IOException;

public class ApplicationExceptions extends RuntimeException {
    public ApplicationExceptions() {
    }

    public ApplicationExceptions(String message) {
        super(message);
    }

    public ApplicationExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationExceptions(Throwable cause) {
        super(cause);
    }

    public ApplicationExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
