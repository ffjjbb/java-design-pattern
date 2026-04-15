package com.fanjiabao.design.pattern.structural.decorator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 17:48
 * @description: 炒饭(具体的构件角色)
 */
public class FriedRice extends FastFood {

    public FriedRice() {
        super(10,"炒饭");
    }

    public float cost() {
        return getPrice();
    }
}
