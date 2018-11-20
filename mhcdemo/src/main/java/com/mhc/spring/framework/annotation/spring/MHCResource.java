package com.mhc.spring.framework.annotation.spring;


import java.lang.annotation.*;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME )
@Documented
public @interface MHCResource {
}
