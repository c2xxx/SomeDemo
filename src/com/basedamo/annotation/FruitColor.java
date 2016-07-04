package com.basedamo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ChenHui on 2016/7/4.
 */

/**
 * 水果颜色注解
 *
 * @author peida
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     *
     * @author peida
     */
    public enum Color {
        BULE, RED, GREEN
    }

    ;

    /**
     * 颜色属性
     *
     * @return
     */
    Color fruitColor() default Color.GREEN;

}