package com.example.dynamic_registration_interface;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
import java.util.UUID;

@Slf4j
public class RequestMappingRegisterFilter extends OncePerRequestFilter {

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public RequestMappingRegisterFilter(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 生成 requestId
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        // 将 requestId 存入 ThreadLocal
        RequestContext.setRequestId(requestId);
        // 在响应头中附加 requestId
        response.setHeader("Request-Id", requestId);
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
        log.error("请求动态接口未注册或者已注销，requestUri:{},requestId:{}", requestUri, requestId);
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
    }

}