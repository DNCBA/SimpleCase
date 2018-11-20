package com.mhc.spring.framework.annotation.springMVC;


import java.lang.annotation.*;

@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME )
@Documented
public @interface MHCRequestMapping {
    String value();
}
