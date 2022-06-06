package com.mhc.spring.test.enbale;


import com.mhc.spring.test.MCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME )
@Documented
@Import(BeanSelector.class)
@Conditional(MCondition.class)
public @interface EnableBean {
}
