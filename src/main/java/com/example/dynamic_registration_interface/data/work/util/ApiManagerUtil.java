package com.example.dynamic_registration_interface.data.work.util;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApiManagerUtil {

    /**
     * 查找已经映射到spring的接口(所有的已经注册到Spring的接口,包括原有的接口)
     */
    public static List<String> mappingList(RequestMappingHandlerMapping handlerMapping) {
        List<String> registeredMethods = new LinkedList<>();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = handlerMapping.getHandlerMethods().entrySet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo info = entry.getKey();
            if (info.getPatternsCondition() != null) {
                Set<String> patterns = info.getPatternsCondition().getPatterns();
                for (String pattern : patterns) {
                    registeredMethods.add(pattern);
                }
            }
        }
        return registeredMethods;
    }

}
