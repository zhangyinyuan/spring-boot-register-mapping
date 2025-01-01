package com.example.dynamic_registration_interface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class AdapterController {

    Object myTest() {
        return "this is test request";
    }

    Object myTest2(@RequestParam("fileName") String value) {
        return "this is my param : " + value;
    }

    Object myTest3(String fileName, String type, Boolean isSort) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileName", fileName);
        jsonObject.put("type", type);
        jsonObject.put("isSort", isSort);
        return "values : " + jsonObject.toJSONString();
    }

//    Object myTest33333(Object... args) {
//        return args;
//    }

    Object myTest4(HttpServletRequest request) {
        byte[] body = new byte[request.getContentLength()];
        JSONObject jsonObj = null;
        try (
                ServletInputStream in = request.getInputStream();
        ) {
            in.read(body, 0, request.getContentLength());
            jsonObj = JSON.parseObject(new String(body, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(jsonObj)) {
            return "fail to parse request";
        }
        return jsonObj;
    }


    public Map<String, Object> handleDynamicParams(@RequestParam Map<String, Object> params) {
        return params;
    }
}