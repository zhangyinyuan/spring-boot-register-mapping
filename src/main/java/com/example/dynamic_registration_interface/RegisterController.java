package com.example.dynamic_registration_interface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

@RestController
public class RegisterController {

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object register(RegisterReqVo registerReqVo) throws NoSuchMethodException {
        dynamicService.register(registerReqVo);
        return ApiResult.succeed(Result.Status.OK, null, "注册成功");
    }

    @GetMapping("/unregister")
    public Object unregister(RegisterReqVo registerReqVo) throws NoSuchMethodException {
        dynamicService.unregister(registerReqVo);
        return ApiResult.succeed(Result.Status.OK, null, "取消注册成功");
    }

    @GetMapping("/registeredMethods")
    public List<String> registeredMethods() {
        return ApiManagerUtil.mappingList(handlerMapping);
    }

}
