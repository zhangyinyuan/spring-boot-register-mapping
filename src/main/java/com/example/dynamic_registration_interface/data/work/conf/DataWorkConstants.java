package com.example.dynamic_registration_interface.data.work.conf;

import org.springframework.web.bind.annotation.RequestMethod;

public final class DataWorkConstants {
    private DataWorkConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String TEST = "test";

    public static final String RELEASE = "release";

    /**
     * 动态API前缀路径
     */
    public static final String DYNAMIC_API_PREFIX = "/dynamic";

    /**
     * 路径分隔符
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * 测试版本的动态API前缀路径
     */
    public static final String TEST_DYNAMIC_API_PREFIX = "/test/dynamic";

    /**
     * 发布版本的动态API前缀路径
     */
    public static final String RELEASE_DYNAMIC_API_PREFIX = "/release/dynamic";

    /**
     * 默认的响应内容的MIME类型
     */
    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";


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

        //method作为一个字符串数组返回
        public static String[] supportedMethods() {
            RequestMethodMapping[] values = values();
            String[] methods = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                methods[i] = values[i].method;
            }
            return methods;
        }
    }

    public enum VersionFlag {
        TEST("test"), RELEASE("release");
        public final String flag;

        VersionFlag(String flag) {
            this.flag = flag;
        }

        public static boolean isSupported(String flag) {
            for (VersionFlag value : values()) {
                if (value.flag.equalsIgnoreCase(flag)) {
                    return true;
                }
            }
            return false;
        }
    }


}
