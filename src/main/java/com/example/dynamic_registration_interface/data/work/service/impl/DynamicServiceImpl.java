package com.example.dynamic_registration_interface.data.work.service.impl;

import com.example.dynamic_registration_interface.data.work.RegisterReqVo;
import com.example.dynamic_registration_interface.data.work.controller.AdapterController;
import com.example.dynamic_registration_interface.data.work.service.DynamicService;
import com.example.dynamic_registration_interface.data.work.util.ApiManagerUtil;
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

import static com.example.dynamic_registration_interface.data.work.GlobalConstants.*;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public void register(RegisterReqVo registerReqVo) throws NoSuchMethodException {
        String path = registerReqVo.getPath();
        String mappingName = registerReqVo.getMappingName();
        String method = registerReqVo.getMethod();
        String produce = registerReqVo.getProduce();
        String mappingUrl = DYNAMIC_API_PREFIX + path + PATH_SEPARATOR + mappingName;
        checkParam(path, mappingName, method, produce, mappingUrl);
        List<String> mappingList = ApiManagerUtil.mappingList(handlerMapping);
        boolean notExists = mappingList.stream().filter(p -> mappingUrl.equals(p)).count() == 0;
        Assert.isTrue(notExists, "接口[" + path + PATH_SEPARATOR + mappingName + "]已注册,请勿重复注册");
        RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths(mappingUrl)
                .produces(produce)
                .methods(RequestMethodMapping.requestMethod(method.toUpperCase())).build();
        handlerMapping.registerMapping(requestMappingInfo, "adapterController", AdapterController.class.getDeclaredMethod("handleDynamicAPi", Map.class, Map.class));
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
    public void unregister(RegisterReqVo registerReqVo) {
        String path = registerReqVo.getPath();
        String mappingName = registerReqVo.getMappingName();
        String method = registerReqVo.getMethod();
        String produce = registerReqVo.getProduce();
        String mappingUrl = DYNAMIC_API_PREFIX + path + PATH_SEPARATOR + mappingName;
        checkParam(path, mappingName, method, produce, mappingUrl);
        RequestMappingHandlerMapping bean = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        List<String> mappingList = ApiManagerUtil.mappingList(handlerMapping);
        boolean notExists = mappingList.stream().filter(p -> mappingUrl.equals(p)).count() == 0;
        if (!notExists) {
            return;
        }
        RequestMappingInfo requestMappingInfo6 = RequestMappingInfo.paths(mappingUrl)
                .produces(produce)
                .methods(RequestMethodMapping.requestMethod(method.toUpperCase())).build();
        bean.unregisterMapping(requestMappingInfo6);
    }

}