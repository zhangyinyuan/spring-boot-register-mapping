package com.example.dynamic_registration_interface;

public interface DynamicService {
    void register(UserLoginReq userLoginReq) throws NoSuchMethodException;

    void unregister(String apiName) throws NoSuchMethodException;
}
