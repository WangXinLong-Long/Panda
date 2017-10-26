package com.example;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author wxl
 * @date on 2017/10/26.
 */

public class AopFrameWorkTest {
    public static void main(String[] args) {

        InputStream resourceAsStream = AopFrameWorkTest.class.getResourceAsStream("G:\\csdn\\Panda\\testlib\\src\\main\\java\\com\\example"+ File.separator+"config.properties");
        BeanFactory beanFactory = new BeanFactory(resourceAsStream);
        Object xxx = beanFactory.getBean("xxx");
        System.out.println(xxx.toString());
    }

}
