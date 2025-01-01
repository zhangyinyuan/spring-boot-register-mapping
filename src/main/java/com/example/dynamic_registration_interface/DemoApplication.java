package com.example.dynamic_registration_interface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class DemoApplication {


    @Autowired
    private DynamicService dynamicService;

    public static void main(String[] args) throws NoSuchMethodException {
        ApplicationContext run = SpringApplication.run(DemoApplication.class, args);


        RequestMappingHandlerMapping bean = run.getBean(RequestMappingHandlerMapping.class);
//        AdapterController bean1 = run.getBean(AdapterController.class);
//        RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths("/test").methods(RequestMethod.GET).build();
//        bean.registerMapping(requestMappingInfo, bean1, AdapterController.class.getDeclaredMethod("myTest"));

        // 无参get方法
        RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths("/lmcTest").methods(RequestMethod.GET).build();
        bean.registerMapping(requestMappingInfo, "adapterController", AdapterController.class.getDeclaredMethod("myTest"));

    }

}
