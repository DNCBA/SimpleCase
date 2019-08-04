package com.mhc.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Aop {

    @Pointcut("execution(* com.mhc.spring.framework..*)")
    public void pointcut(){}

    @Pointcut("@annotation(org.springframework.stereotype.Controller)")
    public void pointcutAnnotation(){}

    @Pointcut("within(com.mhc.spring.framework.*)")
    public void pointcutClass(){}

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void pointcutExtends(){}

}
