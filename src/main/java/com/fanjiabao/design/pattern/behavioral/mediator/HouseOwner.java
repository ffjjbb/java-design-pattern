package com.fanjiabao.design.pattern.behavioral.mediator;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 15:53
 * @description: 具体的同事角色类
 */
public class HouseOwner extends Person {

    public HouseOwner(String name, Mediator mediator) {
        super(name, mediator);
    }

    // 和中介沟通
    public void contact(String message) {
        mediator.contact(message,this);
    }

    public void getMessage(String message) {
        System.out.println("房主 " + name + " 获取到的信息是: " + message);
    }
}