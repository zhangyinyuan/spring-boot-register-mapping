package com.example.dynamic_registration_interface.data.work;

public class RequestContext {
    private static ThreadLocal<String> requestIdHolder = new ThreadLocal<>();

    public static void setRequestId(String requestId) {
        requestIdHolder.set(requestId);
    }

    public static String getRequestId() {
        return requestIdHolder.get();
    }

    public static void clear() {
        requestIdHolder.remove();
    }
}
