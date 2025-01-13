package com.example.dynamic_registration_interface;

import com.example.dynamic_registration_interface.data.work.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Object> handleGeneralException(Exception ex) {
        System.out.println("Exception caught: " + ex.getMessage());
        return Result.failed(ex.getMessage());
    }

}
