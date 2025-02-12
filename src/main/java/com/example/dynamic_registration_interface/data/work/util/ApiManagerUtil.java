package com.example.dynamic_registration_interface.data.work.util;

import com.example.dynamic_registration_interface.data.work.res.ConditionResVo;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

import static com.example.dynamic_registration_interface.data.work.conf.DataWorkConstants.RELEASE_DYNAMIC_API_PREFIX;
import static com.example.dynamic_registration_interface.data.work.conf.DataWorkConstants.TEST_DYNAMIC_API_PREFIX;

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


    /**
     * 查找已经映射到spring的接口(所有的已经注册到Spring的接口,包括原有的接口)
     */
    public static List<ConditionResVo> allMappingList(RequestMappingHandlerMapping handlerMapping) {
        List<ConditionResVo> conditions = new LinkedList<>();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = handlerMapping.getHandlerMethods().entrySet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo info = entry.getKey();
            PatternsRequestCondition requestCondition = info.getPatternsCondition();
            if (requestCondition != null) {
                ConditionResVo conditionResVo = new ConditionResVo();
                Set<String> patterns = requestCondition.getPatterns();
                RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
                Set<RequestMethod> requestMethods = methodsCondition.getMethods();
                conditionResVo.setPatterns(patterns);
                conditionResVo.setMethods(requestMethods);
                conditions.add(conditionResVo);
            }
        }
        return conditions;
    }

    public static boolean isExists(RequestMappingHandlerMapping handlerMapping, String requestMethod, String pattern) {
        List<ConditionResVo> conditionResVos = allMappingList(handlerMapping);
        for (ConditionResVo conditionResVo : conditionResVos) {
            Set<String> patterns = conditionResVo.getPatterns();
            Set<RequestMethod> methods = conditionResVo.getMethods();
            if (patterns.contains(pattern) && methods.contains(RequestMethod.valueOf(requestMethod.toUpperCase()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 手动注册的接口
     *
     * @param handlerMapping
     */
    public static List<ConditionResVo> manualMappingList(RequestMappingHandlerMapping handlerMapping) {
        List<String> registeredMethods = new LinkedList<>();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = handlerMapping.getHandlerMethods().entrySet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo info = entry.getKey();
            if (info.getPatternsCondition() != null) {
                Set<String> patterns = info.getPatternsCondition().getPatterns();
                for (String pattern : patterns) {
                    if (pattern.startsWith(TEST_DYNAMIC_API_PREFIX) || pattern.startsWith(RELEASE_DYNAMIC_API_PREFIX)) {
                        registeredMethods.add(pattern);
                    }
                }
            }
        }
        List<ConditionResVo> results = new ArrayList<>();
        List<ConditionResVo> conditions = allMappingList(handlerMapping);
        for (ConditionResVo condition : conditions) {
            Set<RequestMethod> methods = condition.getMethods();
            Set<String> patterns = condition.getPatterns();
            for (String pattern : patterns) {
                if (pattern.startsWith(TEST_DYNAMIC_API_PREFIX) || pattern.startsWith(RELEASE_DYNAMIC_API_PREFIX)) {
                    results.add(condition);
                }
            }
        }
        return results;
    }


}
