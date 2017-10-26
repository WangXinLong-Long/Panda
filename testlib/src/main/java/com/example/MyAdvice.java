package com.example;

import java.lang.reflect.Method;

/**
 * @author wxl
 * @date on 2017/10/26.
 */

public class MyAdvice implements Advice {
    long start = 0;
    @Override
    public void before(Method method) {
        start = System.currentTimeMillis();
    }

    @Override
    public void after(Method method) {
        long end = System.currentTimeMillis();
        System.out.println(method.getName()+ " running time is "+ (start-end));
    }
}
