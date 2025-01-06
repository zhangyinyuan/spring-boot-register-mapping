package com.example.dynamic_registration_interface;

import lombok.Data;

@Data
public class ApiResult<T> extends Result<T> {
    private String requestId;

    public static <T> ApiResult<T> of(Status status, T result, String message, String errorCode) {
        return new ApiResult<T>(status, result, message, errorCode);
    }

    public static <T> ApiResult<T> succeed(T result) {
        return of(Result.Status.OK, result, (String) null, (String) null);
    }

    public static <T> ApiResult<T> succeed(Status status, T result, String message) {
        return of(status, result, message, null);
    }

    public static <T> ApiResult<T> failed(T result, String message, String errorCode) {
        return of(Result.Status.ERROR, result, message, errorCode);
    }

    public static <T> ApiResult<T> failed(String message, String errorCode) {
        return of(Result.Status.ERROR, (T)null, message, errorCode);
    }

    public static <T> ApiResult<T> failed(String message) {
        return of(Result.Status.ERROR, (T)null, message, (String)null);
    }

    @Override
    public boolean isOk() {
        return super.isOk();
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    @Override
    public T getResult() {
        return super.getResult();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
    }

    @Override
    public void setResult(T result) {
        super.setResult(result);
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public void setErrorCode(String errorCode) {
        super.setErrorCode(errorCode);
    }

    public ApiResult(Status status, T result, String message, String errorCode) {
        super(status, result, message, errorCode);
    }






}
