package com.example.dynamic_registration_interface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void register(UserLoginReq userLoginReq) throws NoSuchMethodException {

        // 无参get方法
        RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths("/dynamic/lmcTest").methods(RequestMethod.GET).build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo, "adapterController", AdapterController.class.getDeclaredMethod("myTest"));

        // 带一参数的get方法
        RequestMappingInfo requestMappingInfo1 = RequestMappingInfo.paths("/dynamic/lmcTest2").params("fileName").methods(RequestMethod.GET).build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo1, "adapterController", AdapterController.class.getDeclaredMethod("myTest2", String.class));

        // 带多个参数的get方法
        RequestMappingInfo requestMappingInfo2 = RequestMappingInfo.paths("/dynamic/lmcTest3")
                .params("fileName", "type", "isSort")
                .methods(RequestMethod.GET).build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo2, "adapterController", AdapterController.class.getDeclaredMethod("myTest3", String.class, String.class, Boolean.class));

        // 无参post方法
        RequestMappingInfo requestMappingInfo3 = RequestMappingInfo.paths("/dynamic/lmcTest4").methods(RequestMethod.POST).build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo3, "adapterController", AdapterController.class.getDeclaredMethod("myTest"));

        // 带参post方法
        RequestMappingInfo requestMappingInfo4 = RequestMappingInfo.paths("/dynamic/lmcTest5")
                .params("fileName", "type", "isSort")
                .methods(RequestMethod.POST).build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo4, "adapterController", AdapterController.class.getDeclaredMethod("myTest3", String.class, String.class, Boolean.class));


        // body带参的post方法
        RequestMappingInfo requestMappingInfo5 = RequestMappingInfo.paths("/dynamic/lmcTest6")
                .produces("application/json;charset=UTF-8")
                .methods(RequestMethod.POST).build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo5, "adapterController", AdapterController.class.getDeclaredMethod("myTest4", HttpServletRequest.class));


        //get请求多个动态参数
        RequestMappingInfo requestMappingInfo6 = RequestMappingInfo.paths("/dynamic/lmcTest7")
                .produces("application/json;charset=UTF-8")
                .methods(RequestMethod.GET).build();
        requestMappingHandlerMapping.registerMapping(requestMappingInfo6, "adapterController", AdapterController.class.getDeclaredMethod("handleDynamicParams", Map.class));

        // 手动刷新 HandlerMapping 缓存
        requestMappingHandlerMapping.afterPropertiesSet();
    }

    @Override
    public void unregister(String apiName) {
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        RequestMappingInfo requestMappingInfo6 = RequestMappingInfo.paths("/dynamic/"+ apiName)
                .produces("application/json;charset=UTF-8")
                .methods(RequestMethod.GET).build();
        bean.unregisterMapping(requestMappingInfo6);
    }

}