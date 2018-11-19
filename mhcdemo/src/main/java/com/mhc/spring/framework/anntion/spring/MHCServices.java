package com.mhc.spring.framework.anntion.spring;


import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME )
@Documented
public @interface MHCServices {
}
