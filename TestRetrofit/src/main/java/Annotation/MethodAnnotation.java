package Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target 表示该注解的作用范围
 * 当注解未指定Target值时，则此注解可以用于任何元素之上，多个值使用{}包含并用逗号隔开
 * @Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
 */
@Target(ElementType.METHOD)
/**
 * @Retention 注解的运行周期
 */
@Retention(RetentionPolicy.RUNTIME)
//表示注解可被继承。例如B继承A，A用了MethodAnnotation注解，则B也有了MethodAnnotation注解
@Inherited

//@Repeatable()  详细看博客解释
public @interface MethodAnnotation {

    //成员变量 注解的属性
    String name() default "";

    int count() default 0;
}
