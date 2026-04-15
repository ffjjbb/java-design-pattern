package com.fanjiabao.design.pattern.structural.decorator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/15 17:46
 * @description: 快餐类(抽象构件角色)
 */
public abstract class FastFood {

    private float price;

    private String desc;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public FastFood(float price, String desc) {
        this.price = price;
        this.desc = desc;
    }

    public FastFood() {
    }

    public abstract float cost();

}
