package com.example.dynamic_registration_interface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

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

}
