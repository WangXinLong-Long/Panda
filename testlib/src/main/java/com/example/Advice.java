package com.example;

import java.lang.reflect.Method;

/**
 * @author wxl
 * @date on 2017/10/26.
 */

public interface Advice {
    void before(Method method);
    void after(Method method);
}
