package com.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wxl
 * @date on 2017/10/26.
 */

public class ProxyFactoryBean {
    public Advice advice;
    public Object targe;

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public Object getTarge() {
        return targe;
    }

    public void setTarge(Object targe) {
        this.targe = targe;
    }

  public Object getProxy(){
        return Proxy.newProxyInstance(targe.getClass().getClassLoader(), targe.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                advice.before(method);
                Object returnVal = method.invoke(targe,objects);
                advice.after(method);
                return returnVal;
            }
        });
  }
}
/*
*  public Object getProxy(){
        return Proxy.newProxyInstance(targe.getClass().getClassLoader(), targe.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                advice.before(method);
                Object object =method.invoke(targe,objects);
                advice.after(method);
                return object;
            }
        });
   }
*/