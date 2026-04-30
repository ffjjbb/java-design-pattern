package com.fanjiabao.design.pattern.behavioral.visitor;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/28 11:41
 * @description: 具体访问者角色类(自己)
 */
public class Owner implements Person {

    @Override
    public void feed(Cat cat) {
        System.out.println("喂猫");
    }

    @Override
    public void feed(Dog dog) {
        System.out.println("喂狗");
    }

}

