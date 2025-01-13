package com.example.dynamic_registration_interface.data.work.service;

import com.example.dynamic_registration_interface.data.work.RegisterReqVo;

public interface DynamicService {
    void register(RegisterReqVo registerReqVo) throws NoSuchMethodException;

    void unregister(RegisterReqVo registerReqVo) throws NoSuchMethodException;
}
