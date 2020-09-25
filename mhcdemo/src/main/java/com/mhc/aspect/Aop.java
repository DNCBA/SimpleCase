package com.mhc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Aop {

  @Pointcut("execution(* com.mhc.spring.framework..*)")
  public void pointcut() {
  }

  @Pointcut("@annotation(org.springframework.stereotype.Controller)")
  public void pointcutAnnotation() {
  }

  @Pointcut("within(com.mhc.spring.framework.*)")
  public void pointcutClass() {
  }

  @Pointcut("@within(org.springframework.stereotype.Controller)")
  public void pointcutExtends() {
  }


  @Before(value = "pointcut()")
  public void before(JoinPoint joinPoint){
    //todo
  }

  @Around(value = "pointcutAnnotation()")
  public void around(JoinPoint joinPoint){
    //todo
  }

  @After(value = "pointcutClass()")
  public void after(JoinPoint joinPoint){
    //todo
  }

  @AfterReturning(value = "pointcutExtends()",returning = "res")
  public void afterReturning(ProceedingJoinPoint joinPoint,Object res){
    //todo
  }

  @AfterThrowing(value = "pointcut()",throwing = "thr")
  public void afterThrowing(JoinPoint joinPoint,Throwable thr){
    //todo
  }


}
