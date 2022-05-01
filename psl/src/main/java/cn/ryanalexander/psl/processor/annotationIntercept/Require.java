package cn.ryanalexander.psl.processor.annotationIntercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Require {
    boolean enable() default true;

    RoleEnum value() default RoleEnum.TEACHER;
}