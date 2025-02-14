package com.example.dynamic_registration_interface.data.work;

import lombok.Data;
import org.springframework.http.MediaType;

import java.io.Serializable;

@Data
public class RegisterReqVo implements Serializable {

    private static final long serialVersionUID = -327565397185943221L;

    /**
     * 请求路径
     */
    String path;

    /**
     * 请求的具体的方法
     */
    String mappingName;

    /**
     * 请求方式
     */
    String method;

    /**
     * 服务端生成的响应内容的 MIME 类型
     * 默认是 application/json;charset=UTF-8
     */
    String produce = MediaType.APPLICATION_JSON_UTF8_VALUE;

    //        Builder paths(String... paths);
    //
    //        Builder methods(RequestMethod... methods);
    //
    //        Builder params(String... params);
    //
    //        Builder headers(String... headers);
    //
    //        Builder consumes(String... consumes);
    //
    //        Builder produces(String... produces);
    //
    //        Builder mappingName(String name);
}
