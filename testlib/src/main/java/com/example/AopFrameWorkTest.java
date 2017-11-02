package com.example;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

/**
 * @author wxl
 * @date on 2017/10/26.
 */

public class AopFrameWorkTest {
    public static void main(String[] args) {

//        InputStream resourceAsStream = AopFrameWorkTest.class.getResourceAsStream("config.properties");
//        BeanFactory beanFactory = new BeanFactory(resourceAsStream);
//        Object xxx = beanFactory.getBean("xxx");
//        System.out.println(xxx.toString());

        try {
            Method method = AopFrameWorkTest.class.getMethod("apply", Vector.class);
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            ParameterizedType genericParameterType = (ParameterizedType) genericParameterTypes[0];
            Type[] actualTypeArguments = genericParameterType.getActualTypeArguments();
            System.out.println(actualTypeArguments[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

    public void apply(Vector<Date> vector) {

    }
/*
*  try {
            Method method =  AopFrameWorkTest.class.getMethod("apply",Vector.class);
            Type[] genericTypes = method.getGenericParameterTypes();
            ParameterizedType parameterizedType =   ((ParameterizedType) genericTypes[0]);
            Type[] parameterTypes = parameterizedType.getActualTypeArguments();
            System.out.println(parameterTypes[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/
}
