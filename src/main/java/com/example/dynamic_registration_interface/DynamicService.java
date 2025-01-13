package com.example.dynamic_registration_interface;

public interface DynamicService {
    void register(RegisterReqVo registerReqVo) throws NoSuchMethodException;

    void unregister(RegisterReqVo registerReqVo) throws NoSuchMethodException;
}
