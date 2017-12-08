package com.example.strategyPattern.defend;

/**
 * @author wxl
 * @date on 2017/12/8.
 * @describe:
 */

public class DefineTBS implements IDefendBehavior {
    @Override
    public void defend() {
        System.out.println("铁布衫");
    }
}
