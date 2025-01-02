package com.example.dynamic_registration_interface;


public class NotFoundDynamicApiException extends RuntimeException {
    public NotFoundDynamicApiException(String message) {
        super(message);
    }
}
