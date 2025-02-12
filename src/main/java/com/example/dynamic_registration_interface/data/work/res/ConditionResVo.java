package com.example.dynamic_registration_interface.data.work.res;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.Set;

@Data
public class ConditionResVo implements Serializable {
    private Set<String> patterns;

    private Set<RequestMethod> methods;
}
