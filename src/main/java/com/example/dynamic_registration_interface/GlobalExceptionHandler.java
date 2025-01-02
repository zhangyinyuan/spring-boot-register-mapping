package com.example.dynamic_registration_interface;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundDynamicApiException.class)
    public Result<Object> handleCustomException(NotFoundDynamicApiException ex) {
        // 加入日志输出，检查是否进入该方法
        System.out.println("Exception caught: " + ex.getMessage());
        return Result.failed(ex.getMessage());
    }

    // 捕获通用异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> handleGeneralException(Exception ex) {
        System.out.println("Exception caught: " + ex.getMessage());
        return Result.failed(ex.getMessage());
    }

}
