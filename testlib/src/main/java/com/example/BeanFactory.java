package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author wxl
 * @date on 2017/10/26.
 */

public class BeanFactory {
   Properties properties = new Properties();
   public BeanFactory(InputStream is){
       try {
           properties.load(is);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
  public Object getBean(String key){
       String value = properties.getProperty(key);
      Object bean = null;
      try {
          Class aClass = Class.forName(value);
          bean =aClass.newInstance();
      } catch (Exception e) {
          e.printStackTrace();
      }
      if (bean instanceof ProxyFactoryBean){
          String adviceStr = properties.getProperty(key+".advice");
          String targetStr = properties.getProperty(key+".target");
          try {
              Advice adviceIns = ((Advice) Class.forName(adviceStr).newInstance());
              Object targetIns = (Class.forName(adviceStr).newInstance());
              ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
              proxyFactoryBean.setAdvice(adviceIns);
              proxyFactoryBean.setTarge(targetIns);
              return proxyFactoryBean.getProxy();
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      return bean;
  }
}
/**
 Properties properties = new Properties();

 public BeanFactory(InputStream ips) {
 try {
 properties.load(ips);
 } catch (IOException e) {
 e.printStackTrace();
 }
 }

 public Object getBean(String key) {
 String property = properties.getProperty(key);
 Object instance = null;
 try {
 Class<?> aClass = Class.forName(property);
 instance = aClass.newInstance();
 } catch (Exception e) {
 e.printStackTrace();
 }
 if (instance instanceof ProxyBean){
 String adviceProperty1 = properties.getProperty(key + ".advice");
 String  targetProperty1=properties.getProperty(key+".target");
 ProxyFactoryBean proxyFactoryBean =null;
 try {
 Advice advice = (Advice) Class.forName(adviceProperty1).newInstance();
 Object target = Class.forName(targetProperty1).newInstance();
 proxyFactoryBean= new ProxyFactoryBean();
 proxyFactoryBean.setAdvice(advice);
 proxyFactoryBean.setTarge(target);

 } catch (Exception e) {
 e.printStackTrace();
 }

 return proxyFactoryBean.getProxy();
 }
 return instance;
 }
 */