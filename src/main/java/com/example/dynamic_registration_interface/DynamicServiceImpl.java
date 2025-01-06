package com.example.dynamic_registration_interface;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
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
        String mappingUrl = DYNAMIC_API_PREFIX + path + PATH_SEPARATOR + mappingName;
        checkParam(path, mappingName, method, produce, mappingUrl);
        List<String> mappingList = ApiManagerUtil.mappingList(handlerMapping);
        boolean notExists = mappingList.stream().filter(p -> mappingUrl.equals(p)).count() == 0;
        Assert.isTrue(notExists, "接口[" + path + PATH_SEPARATOR + mappingName + "]已注册,请更换url或者先下线");
        RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths(mappingUrl)
                .produces(produce)
                .methods(RequestMethodMapping.requestMethod(method.toUpperCase())).build();
        handlerMapping.registerMapping(requestMappingInfo, "adapterController", AdapterController.class.getDeclaredMethod("handleDynamicParams", Map.class));
    }

    private void checkParam(String path, String mappingName, String method, String produce, String mappingUrl) {
        Assert.isTrue(StringUtils.isNotBlank(path), "请求路径[path]不能为空");
        Assert.isTrue(path.startsWith(PATH_SEPARATOR), "请求路径[path]必须以/开头");
        Assert.isTrue(!path.endsWith(PATH_SEPARATOR), "请求路径[path]末尾不需要/");
        Assert.isTrue(StringUtils.isNotBlank(mappingName), "请求方法名[mappingName]不能为空");
        Assert.isTrue(mappingName.matches("[a-zA-Z]+"), "方法名只支持字母");
        Assert.isTrue(StringUtils.isNotBlank(method), "请求方式[method]不能为空");
        Assert.isTrue(RequestMethodMapping.isSupported(method), "不支持的请求方式method:[" + method + "]");
        Assert.isTrue(StringUtils.isNotBlank(produce), "请求方法名[produce]不能为空");
        Assert.isTrue(MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(produce), "produce目前只支持produce:" + MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    @Override
    public void unregister(RegisterReq registerReq) {
        String path = registerReq.getPath();
        String mappingName = registerReq.getMappingName();
        String method = registerReq.getMethod();
        String produce = registerReq.getProduce();
        String mappingUrl = DYNAMIC_API_PREFIX + path + PATH_SEPARATOR + mappingName;
        checkParam(path, mappingName, method, produce, mappingUrl);
        RequestMappingHandlerMapping bean = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        List<String> mappingList = ApiManagerUtil.mappingList(handlerMapping);
        boolean notExists = mappingList.stream().filter(p -> mappingUrl.equals(p)).count() == 0;
        Assert.isTrue(!notExists, "接口[" + path + PATH_SEPARATOR + mappingName + "]未注册");
        RequestMappingInfo requestMappingInfo6 = RequestMappingInfo.paths(mappingUrl)
                .produces(produce)
                .methods(RequestMethodMapping.requestMethod(method.toUpperCase())).build();
        bean.unregisterMapping(requestMappingInfo6);
    }

}