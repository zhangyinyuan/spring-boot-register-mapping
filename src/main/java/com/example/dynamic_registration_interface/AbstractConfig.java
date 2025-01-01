package com.example.dynamic_registration_interface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractConfig {

    public static final Map<Object, Object> map = new ConcurrentHashMap<>();

}
