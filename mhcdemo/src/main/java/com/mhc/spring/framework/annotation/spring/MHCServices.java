package com.mhc.spring.framework.annotation.spring;


import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME )
@Documented
public @interface MHCServices {
    String value();
}
