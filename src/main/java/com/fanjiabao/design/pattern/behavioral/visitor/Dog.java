package com.fanjiabao.design.pattern.behavioral.visitor;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/28 11:40
 * @description: 具体元素角色类(宠物狗)
 */
public class Dog implements Animal {

    @Override
    public void accept(Person person) {
        person.feed(this);
        System.out.println("狗说: 猫吃得不错");
    }
}

