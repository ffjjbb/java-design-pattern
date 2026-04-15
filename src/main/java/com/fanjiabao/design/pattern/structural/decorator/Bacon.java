package com.fanjiabao.design.pattern.structural.decorator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 17:48
 * @description: 培根类(具体的装饰者角色)
 */
public class Bacon extends Garnish {

    public Bacon(FastFood fastFood) {
        super(fastFood, 2, "培根");
    }

    public float cost() {
        return getPrice() + getFastFood().cost();
    }

    @Override
    public String getDesc() {
        return super.getDesc() + getFastFood().getDesc();
    }

}

