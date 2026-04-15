package com.fanjiabao.design.pattern.structural.decorator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 17:48
 * @description: 炒面(具体的构件角色)
 */
public class FriedNoodles extends FastFood {

    public FriedNoodles() {
        super(12, "炒面");
    }

    public float cost() {
        return getPrice();
    }
}
