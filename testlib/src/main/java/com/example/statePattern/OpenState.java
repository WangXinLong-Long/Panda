package com.example.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class OpenState extends Observable implements IState{
    Map<Integer,Boolean> list =new HashMap<>();

    Bean bean;

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
        Boolean replace = list.replace(bean.key, bean.value);
        setChanged();
        notifyObservers();
    }

    public OpenState() {

        this.list.put(1,true);
        this.list.put(2,true);
        this.list.put(3,true);
        this.list.put(4,true);
    }

    public void clickAction(){
        Bean bean = new Bean();
        bean.key = 3;
        bean.value = false;
        setBean(bean);

	}

	public class Bean{
      public  Integer key;
        public  Boolean value;
    }
}