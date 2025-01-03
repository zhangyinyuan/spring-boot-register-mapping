package com.example.dynamic_registration_interface;

public interface DynamicService {
    void register(RegisterReq registerReq) throws NoSuchMethodException;

//    void unregister(String apiName) throws NoSuchMethodException;

    void unregister(RegisterReq registerReq) throws NoSuchMethodException;
}
