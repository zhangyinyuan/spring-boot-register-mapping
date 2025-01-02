package com.example.dynamic_registration_interface;

import com.alibaba.fastjson.JSON;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class RequestMappingRegisterFilter extends OncePerRequestFilter {

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public RequestMappingRegisterFilter(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = requestMappingHandlerMapping.getHandlerMethods().entrySet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo info = entry.getKey();
            if (info.getPatternsCondition() != null) {
                if (info.getPatternsCondition().getPatterns().contains(requestUri)) {
                    // 已注册的接口，执行某些逻辑
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        PrintWriter writer = response.getWriter();
        Result<Object> result = Result.failed("接口未注册或者已注销");
        String jsonStr = JSON.toJSONString(result);
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }

}