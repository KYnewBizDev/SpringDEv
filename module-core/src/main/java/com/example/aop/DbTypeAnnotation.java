package com.example.aop;

import java.lang.annotation.*;

// 사용할 DB명 annotation
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface DbTypeAnnotation {
  String value() default "";
}
