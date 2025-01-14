package com.example.dynamic_registration_interface.data.work;

import com.alibaba.fastjson.JSON;
import com.example.dynamic_registration_interface.data.work.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

@Component
public class DatabaseDrivenInterfaceRegistrar {

    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void init() throws Exception {
        String json = readJson("dynamic.json");
        List<RegisterReqVo> list = JSON.parseArray(json, RegisterReqVo.class);
        for (RegisterReqVo registerReqVo : list) {
            dynamicService.register(registerReqVo);
        }
    }

    public String readJson(String fileName) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:/" + fileName);
        try (InputStream inputStream = resource.getInputStream();
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
