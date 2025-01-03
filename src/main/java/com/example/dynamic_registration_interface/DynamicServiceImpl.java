package com.example.dynamic_registration_interface;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

import static com.example.dynamic_registration_interface.GlobalConstants.*;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

//    @Override
//    public void register(RegisterReq registerReq) throws NoSuchMethodException {
//        // 无参get方法
//        RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths("/dynamic/lmcTest").methods(RequestMethod.GET).build();
//        handlerMapping.registerMapping(requestMappingInfo, "adapterController", AdapterController.class.getDeclaredMethod("myTest"));
//
//        // 带一参数的get方法
//        RequestMappingInfo requestMappingInfo1 = RequestMappingInfo.paths("/dynamic/lmcTest2").params("fileName").methods(RequestMethod.GET).build();
//        handlerMapping.registerMapping(requestMappingInfo1, "adapterController", AdapterController.class.getDeclaredMethod("myTest2", String.class));
//
//        // 带多个参数的get方法
//        RequestMappingInfo requestMappingInfo2 = RequestMappingInfo.paths("/dynamic/lmcTest3")
//                .params("fileName", "type", "isSort")
//                .methods(RequestMethod.GET).build();
//        handlerMapping.registerMapping(requestMappingInfo2, "adapterController", AdapterController.class.getDeclaredMethod("myTest3", String.class, String.class, Boolean.class));
//
//        // 无参post方法
//        RequestMappingInfo requestMappingInfo3 = RequestMappingInfo.paths("/dynamic/lmcTest4").methods(RequestMethod.POST).build();
//        handlerMapping.registerMapping(requestMappingInfo3, "adapterController", AdapterController.class.getDeclaredMethod("myTest"));
//
//        // 带参post方法
//        RequestMappingInfo requestMappingInfo4 = RequestMappingInfo.paths("/dynamic/lmcTest5")
//                .params("fileName", "type", "isSort")
//                .methods(RequestMethod.POST).build();
//        handlerMapping.registerMapping(requestMappingInfo4, "adapterController", AdapterController.class.getDeclaredMethod("myTest3", String.class, String.class, Boolean.class));
//
//        // body带参的post方法
//        RequestMappingInfo requestMappingInfo5 = RequestMappingInfo.paths("/dynamic/lmcTest6")
//                .produces("application/json;charset=UTF-8")
//                .methods(RequestMethod.POST).build();
//        handlerMapping.registerMapping(requestMappingInfo5, "adapterController", AdapterController.class.getDeclaredMethod("myTest4", HttpServletRequest.class));
//
//        //get请求多个动态参数
//        RequestMappingInfo requestMappingInfo6 = RequestMappingInfo.paths("/dynamic/lmcTest7")
//                .produces("application/json;charset=UTF-8")
//                .methods(RequestMethod.GET).build();
//        handlerMapping.registerMapping(requestMappingInfo6, "adapterController", AdapterController.class.getDeclaredMethod("handleDynamicParams", Map.class));
//
//        // afterPropertiesSet会清空所有非Actuator的静态接口.慎用
//        // handlerMapping.afterPropertiesSet();
//    }

    @Override
    public void register(RegisterReq registerReq) throws NoSuchMethodException {
        String path = registerReq.getPath();
        String mappingName = registerReq.getMappingName();
        String method = registerReq.getMethod();
        String produce = registerReq.getProduce();
        Assert.isTrue(StringUtils.isNotBlank(path), "请求路径[path]不能为空");
        Assert.isTrue(path.startsWith(PATH_SEPARATOR), "请求路径[path]必须以/开头");
        Assert.isTrue(!path.endsWith(PATH_SEPARATOR), "请求路径[path]末尾不需要/");
        Assert.isTrue(StringUtils.isNotBlank(mappingName), "请求方法名[mappingName]不能为空");
        Assert.isTrue(mappingName.matches("[a-zA-Z]+"), "方法名只支持字母");
        Assert.isTrue(StringUtils.isNotBlank(produce), "请求方法名[produce]不能为空");
        Assert.isTrue(MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(produce), "produce目前只支持produce:" + MediaType.APPLICATION_JSON_UTF8_VALUE);
        //已注册的接口,已经被占用. 禁止重复注册.获取先下线即取消注册. 然后再注册
        //get请求多个动态参数
        RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths(DYNAMIC_API_PREFIX + path + PATH_SEPARATOR + mappingName)
                .produces(produce)
                .methods(RequestMethodMapping.requestMethod(method.toUpperCase())).build();
        handlerMapping.registerMapping(requestMappingInfo, "adapterController", AdapterController.class.getDeclaredMethod("handleDynamicParams", Map.class));
    }

//    @Override
//    public void unregister(String apiName) {
//        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
//        RequestMappingInfo requestMappingInfo6 = RequestMappingInfo.paths("/dynamic/"+ apiName)
//                .produces("application/json;charset=UTF-8")
//                .methods(RequestMethod.GET).build();
//        bean.unregisterMapping(requestMappingInfo6);
//    }

    @Override
    public void unregister(RegisterReq registerReq) {
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        RequestMappingInfo requestMappingInfo6 = RequestMappingInfo.paths(DYNAMIC_API_PREFIX + registerReq.getPath() + PATH_SEPARATOR + registerReq.getMappingName())
                .produces("application/json;charset=UTF-8")
                .methods(RequestMethod.GET).build();
        bean.unregisterMapping(requestMappingInfo6);
    }

}