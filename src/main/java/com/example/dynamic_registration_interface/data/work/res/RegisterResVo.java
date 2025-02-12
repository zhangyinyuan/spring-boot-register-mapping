package com.example.dynamic_registration_interface.data.work.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterResVo implements Serializable {

    private static final long serialVersionUID = 1761861930676481457L;

    /**
     * 请求路径
     */
    @ApiModelProperty("请求路径,以/开头,比如/shop")
    private String path;

    /**
     * 请求的具体的方法
     */
    @ApiModelProperty("请求的具体的方法,比如getShop,开头和末尾不需要/")
    private String mappingName;

    /**
     * 请求方式
     */
    @ApiModelProperty("请求方式,比如GET,POST")
    private String method;

    /**
     * 服务端生成的响应内容的MIME类型
     * 默认是 application/json;charset=UTF-8
     */
    @ApiModelProperty("服务端生成的响应内容的MIME类型,默认是application/json;charset=UTF-8")
    private String produce = "application/json;charset=UTF-8";

    @ApiModelProperty(value = "版本标识,test表示测试版本, release表示已发布版本")
    private String versionFlag = "test";

    @ApiModelProperty(value = "注册方式:1：静态，2：动态")
    private Integer registerMethod ;

    @ApiModelProperty("提交测试的数据行id:testId或者releaseId.目前按照原型设计,只支持测试版本用于测试,所以传testId即可")
    private Integer id;
}
