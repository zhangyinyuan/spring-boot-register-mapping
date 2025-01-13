package com.example.dynamic_registration_interface.data.work.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.dynamic_registration_interface.data.work.RequestContext;
import com.example.dynamic_registration_interface.data.work.result.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdapterController {

    /**
     * 处理请求
     */
    public ApiResult<JSONObject> handleDynamicAPi(@RequestParam(required = false) Map<String, Object> query, @RequestBody(required = false) Map<String, Object> body) {
        JSONObject obj = new JSONObject();
        obj.put("query", query);
        obj.put("body", body);
        ApiResult<JSONObject> apiResult = ApiResult.succeed(obj);
        String requestId = RequestContext.getRequestId();
        apiResult.setRequestId(requestId);
        return apiResult;
    }

}