package com.example.aop;

import com.example.config.datasource.DbContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class DataSourceAop {
  @Around(value="@annotation(DbTypeAnnotation)")
  public Object processDBTypeAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    DbTypeAnnotation dbTypeAnnotation = methodSignature.getMethod().getAnnotation(DbTypeAnnotation.class);

    DbContextHolder.setDbType(dbTypeAnnotation.value());
    Object proceedReturnValue = proceedingJoinPoint.proceed();
    DbContextHolder.clearDbType();

    return proceedReturnValue;
  }
}
