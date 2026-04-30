package com.fanjiabao.design.pattern.behavioral.visitor;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/28 11:42
 * @description: 具体访问者角色类(其他人)
 */
public class Someone implements Person {

    @Override
    public void feed(Cat cat) {
        System.out.println("别人喂猫");
    }

    @Override
    public void feed(Dog dog) {
        System.out.println("别人喂狗");
    }

}
