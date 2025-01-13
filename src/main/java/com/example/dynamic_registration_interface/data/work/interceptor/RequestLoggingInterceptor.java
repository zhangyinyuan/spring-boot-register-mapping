package com.example.dynamic_registration_interface.data.work.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.dynamic_registration_interface.data.work.RequestContext;
import com.example.dynamic_registration_interface.data.work.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    @Lazy
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        request.setAttribute("requestId", requestId);
        log.info("Request started:[{}],{},{}", requestId, request.getMethod(), request.getRequestURI());
        request.setAttribute("startTime", System.currentTimeMillis());
        response.setHeader("requestId", requestId);
        return preHandleDynamicAPI(request, response);
    }

    private boolean preHandleDynamicAPI(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        RequestContext.setRequestId(requestId);
        response.setHeader("requestId", requestId);
        String requestUri = request.getRequestURI();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = requestMappingHandlerMapping.getHandlerMethods().entrySet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo info = entry.getKey();
            if (info.getPatternsCondition() != null) {
                if (info.getPatternsCondition().getPatterns().contains(requestUri)) {
                    // 已注册的接口，继续执行业务逻辑
                    return true;
                }
            }
        }
        log.error("接口未注册或者已注销,requestUri:{},requestId:{}", requestUri, requestId);
        PrintWriter writer = response.getWriter();
        // 设置响应编码和内容类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        ApiResult<Object> result = ApiResult.failed("接口未注册或者已注销");
        result.setRequestId(requestId);
        String jsonStr = JSON.toJSONString(result);
        writer.write(jsonStr);
        writer.flush();
        writer.close();
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        String requestId = (String) request.getAttribute("requestId");
        response.setHeader("startTime", String.valueOf(startTime));
        response.setHeader("endTime", String.valueOf(endTime));
        response.setHeader("duration", String.valueOf(duration));
        log.info("Request completed: [{}],{},{}, Duration: {} ms", requestId, request.getMethod(), request.getRequestURI(), duration);
        RequestContext.clear();
    }

}
