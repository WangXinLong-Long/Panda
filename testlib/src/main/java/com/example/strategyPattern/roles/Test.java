package com.example.strategyPattern.roles;

import com.example.statePattern.IState;
import com.example.statePattern.OpenState;
import com.example.strategyPattern.attack.AttackJY;
import com.example.strategyPattern.defend.DefineTBS;
import com.example.strategyPattern.display.Display;

/**
 * @author wxl
 * @date on 2017/12/8.
 * @describe:
 */

public class Test {
    public static void main(String[] args) {
        Role role = new RoleA("A");

        role.setAttackBehavior(new AttackJY())
                .setDefendBehavior(new DefineTBS())
                .setDisplayBehavior(new Display());
        System.out.println(role.name + ":");
        role.attack();

        OpenState openState = new OpenState();
        role.registerObservabal(openState);
        openState.clickAction();
    }
}
