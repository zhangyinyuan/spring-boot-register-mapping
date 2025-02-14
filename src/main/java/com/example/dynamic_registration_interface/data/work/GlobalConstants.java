package com.example.dynamic_registration_interface.data.work;

import org.springframework.web.bind.annotation.RequestMethod;

public final class GlobalConstants {
    private GlobalConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 动态API前缀路径
     */
    public static final String DYNAMIC_API_PREFIX = "/dynamic";

    /**
     * 路径分隔符
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * 请求方式映射
     */
    public enum RequestMethodMapping {
        GET("GET", RequestMethod.GET), POST("POST", RequestMethod.POST), PUT("PUT", RequestMethod.PUT), DELETE("DELETE", RequestMethod.DELETE);
        public final String method;
        public final RequestMethod requestMethod;

        RequestMethodMapping(String method, RequestMethod requestMethod) {
            this.method = method;
            this.requestMethod = requestMethod;
        }

        public static RequestMethod requestMethod(String method) {
            for (RequestMethodMapping value : RequestMethodMapping.values()) {
                if (value.method.equalsIgnoreCase(method)) {
                    return value.requestMethod;
                }
            }
            throw new RuntimeException("不支持的请求方法:[" + method + "]");
        }

        public static boolean isSupported(String method) {
            for (RequestMethodMapping value : values()) {
                if (value.method.equalsIgnoreCase(method)) {
                    return true;
                }
            }
            return false;
        }

    }
}
