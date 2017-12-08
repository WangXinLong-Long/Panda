package com.example.strategyPattern.roles;

import com.example.statePattern.OpenState;
import com.example.strategyPattern.attack.IAttackBehavior;
import com.example.strategyPattern.defend.IDefendBehavior;
import com.example.strategyPattern.display.IDisplayBehavior;

import java.util.Observable;
import java.util.Observer;

/**
 * @author wxl
 * @date on 2017/12/8.
 * @describe:
 */

public class Role implements Observer {
    protected String name;

    protected IDefendBehavior defendBehavior;
    protected IDisplayBehavior displayBehavior;
    protected IAttackBehavior attackBehavior;

    public Role setDefendBehavior(IDefendBehavior defendBehavior) {
        this.defendBehavior = defendBehavior;
        return this;
    }

    public Role setDisplayBehavior(IDisplayBehavior displayBehavior) {
        this.displayBehavior = displayBehavior;
        return this;
    }

    public Role setAttackBehavior(IAttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
        return this;
    }

    protected void display() {
        displayBehavior.display();
    }

    protected void defend() {
        defendBehavior.defend();
    }

    protected void attack() {
        attackBehavior.attack();
    }

    public void registerObservabal(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof OpenState) {
            OpenState observable1 = (OpenState) observable;
            OpenState.Bean bean = observable1.getBean();
            if (bean.value) {
                System.out.println("change +1");
            } else {
                System.out.println("change -1");
            }

        }
    }
}
