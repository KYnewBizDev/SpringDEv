package com.example.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface DbTypeAnnotation {
  String value() default "";
}
