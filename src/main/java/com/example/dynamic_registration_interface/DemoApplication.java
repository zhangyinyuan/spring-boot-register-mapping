package com.example.dynamic_registration_interface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean<Filter> webVisitFilterConfigRegistration(@Autowired @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping requestMappingHandlerMapping ) {
//        //匹配拦截 URL
//        String urlPatterns = "/dynamic/*";
//        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
//        registration.setDispatcherTypes(DispatcherType.REQUEST);
//        registration.setFilter(new RequestMappingRegisterFilter(requestMappingHandlerMapping));
//        registration.addUrlPatterns(urlPatterns.split(","));
//        //设置名称
//        registration.setName("requestMappingRegisterFilter");
//        //设置过滤器链执行顺序
//        registration.setOrder(1);
//        //启动标识
//        registration.setEnabled(true);
//        //添加初始化参数
//        registration.addInitParameter("enabel", "true");
//        return registration;
//    }

}
