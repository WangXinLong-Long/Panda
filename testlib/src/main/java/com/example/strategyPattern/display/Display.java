package com.example.strategyPattern.display;

/**
 * @author wxl
 * @date on 2017/12/8.
 * @describe:
 */

public class Display implements IDisplayBehavior {
    @Override
    public void display() {
        System.out.println("金蝉脱壳");
    }
}
