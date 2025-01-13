package com.example.dynamic_registration_interface.data.work.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class AdapterController {

    /**
     * 处理请求
     */
    public Map<String, Object> handleDynamicAPi(@RequestParam(required = false) Map<String, Object> query, @RequestBody(required = false) Map<String, Object> body) {
        query.put("succe", true);
        query.put("requestId", UUID.randomUUID().toString().replaceAll("-", ""));
        query.put("data", body);
        return query;
    }

}