package com.mhc.spring.framework.anntion.spring;


import java.lang.annotation.*;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME )
@Documented
public @interface MHCAutowired {
}
