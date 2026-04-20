package com.fanjiabao.design.pattern.structural.flyweight;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 10:42
 * @description: 抽象享元角色
 */
public abstract class AbstractBox {

    public abstract String getShape();

    public void display(String color) {
        System.out.println("方块形状: " + getShape() + ", 颜色: " + color);
    }
}
