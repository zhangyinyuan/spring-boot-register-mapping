package com.example.dynamic_registration_interface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class RegisterController {

    @Autowired
    private DynamicService dynamicService;

    @Autowired @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping("/register")
    public Object register(UserLoginReq userLoginReq) throws NoSuchMethodException {
        dynamicService.register(userLoginReq);
        return "register success";
    }

    @GetMapping("/unregister")
    public Object unregister(String apiName) throws NoSuchMethodException {
        dynamicService.unregister(apiName);
        return "unregister [" + apiName + "] success";
    }

    @GetMapping("/registeredMethods")
    public List<String> registeredMethods() {
        List<String> registeredMethods = new LinkedList<>();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = handlerMapping.getHandlerMethods().entrySet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo info = entry.getKey();
            if (info.getPatternsCondition() != null) {
                registeredMethods.add(info.getPatternsCondition().toString());
            }
        }
        return registeredMethods;
    }
}
