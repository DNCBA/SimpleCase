package com.mhc.spring.framework.annotation.springMVC;

import java.lang.annotation.*;


@Target(value = {ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME )
@Documented
public @interface MHCRequestParm {
    String value();
}
