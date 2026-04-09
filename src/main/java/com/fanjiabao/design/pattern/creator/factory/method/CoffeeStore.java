package com.fanjiabao.design.pattern.creator.factory.method;
import com.fanjiabao.design.pattern.creator.factory.Coffee;

public class CoffeeStore {

    private CoffeeMethodFactory factory;

    public void setFactory(CoffeeMethodFactory factory) {
        this.factory = factory;
    }

    public Coffee orderCoffee() {
        Coffee coffee = factory.createCoffee();
        coffee.addMilk();
        coffee.addSugar();
        return coffee;
    }

}
