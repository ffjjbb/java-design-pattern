package com.fanjiabao.design.pattern.structural.decorator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 17:46
 * @description: 装饰者类(抽象装饰者角色)
 */
public abstract class Garnish extends FastFood {

    // 声明快餐类的变量
    private FastFood fastFood;

    public FastFood getFastFood() {
        return fastFood;
    }

    public void setFastFood(FastFood fastFood) {
        this.fastFood = fastFood;
    }

    public Garnish(FastFood fastFood, float price, String desc) {
        super(price, desc);
        this.fastFood = fastFood;
    }

}
