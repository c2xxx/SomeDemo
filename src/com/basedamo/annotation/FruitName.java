package com.basedamo.annotation;

/**
 * Created by ChenHui on 2016/7/4.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 水果名称注解
 *
 * @author peida
 */


//Java中提供了四种元注解，专门负责注解其他的注解，分别如下

//@Retention元注解，表示需要在什么级别保存该注释信息（生命周期）。可选的RetentionPoicy参数包括：
//RetentionPolicy.SOURCE: 停留在java源文件，编译器被丢掉
//RetentionPolicy.CLASS：停留在class文件中，但会被VM丢弃（默认）
//RetentionPolicy.RUNTIME：内存中的字节码，VM将在运行时也保留注解，因此可以通过反射机制读取注解的信息

//@Target元注解，默认值为任何元素，表示该注解用于什么地方。可用的ElementType参数包括
//ElementType.CONSTRUCTOR: 构造器声明
//ElementType.FIELD: 成员变量、对象、属性（包括enum实例）
//ElementType.LOCAL_VARIABLE: 局部变量声明
//ElementType.METHOD: 方法声明
//ElementType.PACKAGE: 包声明
//ElementType.PARAMETER: 参数声明
//ElementType.TYPE: 类、接口（包括注解类型)或enum声明

//@Documented将注解包含在JavaDoc中

//@Inheried允许子类继承父类中的注解

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}