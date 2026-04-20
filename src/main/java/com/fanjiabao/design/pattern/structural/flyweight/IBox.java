package com.fanjiabao.design.pattern.structural.flyweight;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 10:43
 * @description: I图形类(具体享元角色)
 */
public class IBox extends AbstractBox {

    @Override
    public String getShape() {
        return "I";
    }

}
