package com.example.dynamic_registration_interface;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class AdapterController {

//    Object myTest() {
//        return "this is test request";
//    }
//
//    Object myTest2(@RequestParam("fileName") String value) {
//        return "this is my param : " + value;
//    }
//
//    Object myTest3(String fileName, String type, Boolean isSort) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("fileName", fileName);
//        jsonObject.put("type", type);
//        jsonObject.put("isSort", isSort);
//        return jsonObject;
//    }
//
//    Object myTest4(HttpServletRequest request) {
//        byte[] body = new byte[request.getContentLength()];
//        JSONObject jsonObj = null;
//        try (
//                ServletInputStream in = request.getInputStream();
//        ) {
//            in.read(body, 0, request.getContentLength());
//            jsonObj = JSON.parseObject(new String(body, "UTF-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (Objects.isNull(jsonObj)) {
//            return "fail to parse request";
//        }
//        return jsonObj;
//    }

    /**
     * GET 或 POST query传参
     */
    public Map<String, Object> handleDynamicAPi(@RequestParam(required = false) Map<String, Object> query, @RequestBody(required = false) Map<String, Object> body) {
        query.put("succe", true);
        query.put("requestId", UUID.randomUUID().toString().replaceAll("-", ""));
        query.put("data", body);
        return query;
    }

}